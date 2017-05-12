package si.triglav.hackathon.GearType;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class GearTypeDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GEAR_TYPE_COLUMN_LIST = "id_gear_type, gear_type";
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
	
	//old_tukaj se vcasih zgodi da dobimo list
	public GearType getGearTypeById(Integer id_gear_type,Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		System.out.println(id_gear_type);
		System.out.println(id_team);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_type", id_gear_type);
						
		GearType gearType;
		
		try{
			gearType = jdbcTemplate.queryForObject("select "+GEAR_TYPE_COLUMN_LIST+" from "+ TABLE_NAME + " where id_gear_type = :id_gear_type AND id_team= "+teamDAO.getTeamIdByKey(team_key), params , new BeanPropertyRowMapper<GearType>(GearType.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}	
		return gearType;	
	}
	
	
	/*
	public List<GearType> getGearTypeById(Integer id_gear_type,Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		System.out.println(id_gear_type);
		System.out.println(id_team);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_type", id_gear_type);
						
		List<GearType> gearType = jdbcTemplate.query("select "+GEAR_TYPE_COLUMN_LIST+" from "+ TABLE_NAME + " where id_gear_type = :id_gear_type AND id_team= "+teamDAO.getTeamIdByKey(team_key), params , new BeanPropertyRowMapper<GearType>(GearType.class));
		
	
		return gearType;
		
	}
	
	*/

	public GearType createGearType(GearType gearType, Integer team_key) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (gear_type,id_team) VALUES (:gear_type,"+teamDAO.getTeamIdByKey(team_key)+")",
				new BeanPropertySqlParameterSource(gearType), generatedKeyHolder);
			
		GearType created_gearType = getGearTypeById(generatedKeyHolder.getKey().intValue(), team_key);
		return created_gearType;

	}
	
	public int updateGearType(GearType gearType, Integer team_key) {
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
				+" SET (gear_type) = (:gear_type) "
				+" WHERE ID_gear_type = :id_gear_type"
				+" AND ID_team = "+teamDAO.getTeamIdByKey(team_key),
				new BeanPropertySqlParameterSource(gearType));
		
		return updatedRowsCount;
		
	}

	public int deleteGearType(Integer id_gear_type, Integer team_key) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_gear_type = :id_gear_type AND id_team="+teamDAO.getTeamIdByKey(team_key), new MapSqlParameterSource("id_gear_type", id_gear_type));
		return deletedRows;
	}
}
