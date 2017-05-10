package si.triglav.hackathon.RepairService;

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

import si.triglav.hackathon.GearType.GearTypeDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class RepairServiceDAO {
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private GearTypeDAO gearTypeDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String REPAIR_SERVICE_COLUMN_LIST = "ID_repair_service,name,address, ID_gear_type, ID_team";
	private static final String TABLE_NAME = "FREELANCE.REPAIR_SERVICE";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<RepairService> getRepairServiceList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<RepairService> repairServiceList = jdbcTemplate.query("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<RepairService>(RepairService.class));
		
		for(RepairService repairService: repairServiceList){
			repairService.setGear_type(gearTypeDAO.getGearTypeById(repairService.getId_gear_type(), team_key));
		}
		
		return repairServiceList;
	}
	
	
	
	
	
/*
	public List<RepairService> getRepairServiceList() {
		List<RepairService> repairServiceList = jdbcTemplate.query("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME, new BeanPropertyRowMapper<RepairService>(RepairService.class));
		return repairServiceList;
	}*/
	
	public RepairService getRepairServiceById(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("ID_repair_service", id);
		params.addValue("id_team", id_team);
		
		RepairService repairService = jdbcTemplate.queryForObject("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME+" where ID_repair_service = :ID_repair_service AND id_team= :id_team", params , new BeanPropertyRowMapper<RepairService>(RepairService.class));
		
		repairService.setGear_type(gearTypeDAO.getGearTypeById(repairService.getId_gear_type(), team_key));
		
		return repairService;
	}
	
	public RepairService createRepairService(RepairService repairService, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name, address, ID_team, ID_gear_type) values (:name, :address, "+id_team+", :id_gear_type)",
				new BeanPropertySqlParameterSource(repairService), generatedKeyHolder);
		
		RepairService createdRepairService = getRepairServiceById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdRepairService;

	}

	public int updateRepairService(RepairService repairService, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (name,address,ID_gear_type) = (:name,:address, :id_gear_type) "
						+" WHERE ID_repair_service = :id_repair_service"
						+" AND ID_team = "+id_team,
				new BeanPropertySqlParameterSource(repairService));
		return updatedRowsCount;
	}

	public int deleteRepairService(Integer ID_repair_service, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_repair_service", ID_repair_service);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_repair_service = :ID_repair_service", params);
		return deletedRows;
	}

}
