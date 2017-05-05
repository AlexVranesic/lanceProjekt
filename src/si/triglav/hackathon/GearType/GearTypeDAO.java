package si.triglav.hackathon.GearType;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.person.Person;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class GearTypeDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GEAR_TYPE_COLUMN_LIST = "id_gear_type,gear_type,id_team";
	private static final String TABLE_NAME = "FREELANCE.GEAR_TYPE";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<GearType> getGearTypeList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		
		List<GearType> gearTypeList = jdbcTemplate.query("select " + GEAR_TYPE_COLUMN_LIST + " from " + TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<GearType>(GearType.class));
		return gearTypeList;
	}
	
	public GearType getGearTypeNameById(Integer id_gear_type,Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_type", id_gear_type);
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		params.addValue("id_team", id_team);
		
		GearType gearType = jdbcTemplate.queryForObject("select "+GEAR_TYPE_COLUMN_LIST+" from "+ TABLE_NAME + " where id_gear_type = :id_gear_type AND id_team= :id_team ", params , new BeanPropertyRowMapper<GearType>(GearType.class));
		return gearType;
	}
	
	public GearType createGearType(GearType gearType) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (gear_type,id_team) VALUES (:gear_type,:id_team)",
				new BeanPropertySqlParameterSource(gearType), generatedKeyHolder);
			
		GearType created_gearType = getGearTypeById(generatedKeyHolder.getKey().intValue());
		return created_gearType;

	}
	
	public GearType getGearTypeById(Integer id_gear_type) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_type", id_gear_type);
		GearType gearType = jdbcTemplate.queryForObject("select "+GEAR_TYPE_COLUMN_LIST+" from FREELANCE.GEAR_TYPE where id_gear_type = :id_gear_type", params , new BeanPropertyRowMapper<GearType>(GearType.class));
		return gearType;
	}
	/*
	public Integer getTeamIdByKey(Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("team_key", team_key);
		Team team = jdbcTemplate.queryForObject("select id_team from FREELANCE.TEAM where team_key = :team_key FETCH FIRST 1 ROW ONLY", params , new BeanPropertyRowMapper<Team>(Team.class));
		return team.getId_team();
	}
	*/
	public int updateGearType(GearType gearType) {
		
		int updatedRowsCount = jdbcTemplate.update(
				"update "+TABLE_NAME+" set (gear_type) = (:gear_type) where id_team = :id_team",
				new BeanPropertySqlParameterSource(gearType));
		return updatedRowsCount;
		
	}

	public int deleteGearType(Integer id_team) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_team = :id_team", new MapSqlParameterSource("id_team", id_team));
		return deletedRows;
	}
}
