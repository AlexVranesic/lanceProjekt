package si.triglav.hackathon.GearClaim;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.team.TeamDAO;
import si.triglav.hackathon.File.*;

@Repository
public class GearClaimDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GEAR_CLAIM_COLUMN_LIST = "ID_gear_claim, event_date, event_description, claim_is_valid, "
						+ "claim_value, claim_date, return_account_number, id_team,id_gear,id_repair_service,id_claim_type";
	private static final String TABLE_NAME = "FREELANCE.GEAR_CLAIM";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<GearClaim> getGearClaimList(Integer team_key, Integer id_client, Integer id_gear) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("id_gear", id_gear);
		
		List<GearClaim> gearClaimList = jdbcTemplate.query("select "+GEAR_CLAIM_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team="+id_team, params, new BeanPropertyRowMapper<GearClaim>(GearClaim.class));
		
		for(GearClaim gearClaim: gearClaimList){
					
			gearClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("id_gear_claim", gearClaim.getId_gear_claim(), team_key));
					
		}
		return gearClaimList;
	}
	
	public GearClaim getGearClaimById(Integer team_key, Integer id_client, Integer id_gear, Integer id_gear_claim) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_gear", id_gear);
		params.addValue("id_gear_claim", id_gear_claim);
		
		GearClaim gearClaim = jdbcTemplate.queryForObject("SELECT "+GEAR_CLAIM_COLUMN_LIST
														+" FROM "+TABLE_NAME
														+" WHERE id_gear_claim = :id_gear_claim"
														+" AND id_gear= :id_gear"
														+" AND id_team= :id_team", params , new BeanPropertyRowMapper<GearClaim>(GearClaim.class));
		
		//gearClaim.setFiles(fileDAO.getFilesOfGearClaimListFromIdGearClaim(team_key , gearClaim.getId_gear_claim()));
		
		//gearClaim.(fileDAO.getFileById(gearClaim.getId_gear_claim(), team_key));
		gearClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("id_gear_claim", gearClaim.getId_gear_claim(), team_key));

		
		return gearClaim;
	}
	
	
	public GearClaim createGearClaim(GearClaim gearClaim, Integer team_key, Integer id_client, Integer id_gear) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date event_date;
		if(gearClaim.getEvent_date()!=null){
			event_date = new Date(gearClaim.getEvent_date().getTime()+(24*60*60*1000));
		}
		else{
			event_date = null;
		}
		
		Date claim_date;

		if(gearClaim.getEvent_date()!=null){
			claim_date = new Date(gearClaim.getClaim_date().getTime()+(24*60*60*1000));
		}
		else{
			claim_date = null;
		}
		
		MapSqlParameterSource params = new MapSqlParameterSource("event_date", event_date);
		params.addValue("event_description", gearClaim.getEvent_description());
		params.addValue("claim_is_valid", gearClaim.getClaim_is_valid());
		params.addValue("claim_value", gearClaim.getClaim_value());
		params.addValue("claim_date", claim_date);
		params.addValue("return_account_number", gearClaim.getReturn_account_number());
		params.addValue("id_team", id_team);
		params.addValue("id_gear", id_gear);
		params.addValue("id_repair_service", gearClaim.getId_repair_service());
		params.addValue("id_claim_type", gearClaim.getId_claim_type());

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (event_date, event_description, claim_is_valid, "
						+ "claim_value, claim_date, return_account_number, id_team,id_gear,id_repair_service,id_claim_type) "
						+ "values (:event_date, :event_description, :claim_is_valid,"
						+ " :claim_value, :claim_date, :return_account_number, :id_team,:id_gear,:id_repair_service,:id_claim_type )",
				params, generatedKeyHolder);
		
		
		GearClaim createdGearClaim = getGearClaimById(team_key, id_client, id_gear, generatedKeyHolder.getKey().intValue());
		return createdGearClaim;

	}
	
	public int updateGearClaim(GearClaim gearClaim, Integer team_key, Integer  ID_client, Integer id_gear) {
		
		Integer id_team = teamDAO.getTeamIdByKey(team_key);
		
		Date event_date;
		Date claim_date;
		
		if(gearClaim.getEvent_date()!=null)
			event_date = new Date(gearClaim.getEvent_date().getTime()+(24*60*60*1000));
		else
			event_date = null;
		
		if(gearClaim.getClaim_date()!=null)
			claim_date = new Date(gearClaim.getClaim_date().getTime()+(24*60*60*1000));
		else
			claim_date = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource("event_date", event_date);
		params.addValue("event_description", gearClaim.getEvent_description());
		params.addValue("claim_is_valid", gearClaim.getClaim_is_valid());
		params.addValue("claim_value", gearClaim.getClaim_value());
		params.addValue("claim_date", claim_date);
		params.addValue("return_account_number", gearClaim.getReturn_account_number());
		params.addValue("id_team", id_team);
		params.addValue("id_gear", id_gear);
		params.addValue("id_repair_service", gearClaim.getId_repair_service());
		params.addValue("id_claim_type", gearClaim.getId_claim_type());
		params.addValue("id_gear_claim", gearClaim.getId_gear_claim());

		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (event_date, event_description, claim_is_valid, "
						+ "claim_value, claim_date, return_account_number, id_team,id_gear,id_repair_service,id_claim_type) "
						+ "= (:event_date, :event_description, :claim_is_valid,"
						+ " :claim_value, :claim_date, :return_account_number, :id_team,:id_gear,:id_repair_service,:id_claim_type ) "
						+" WHERE ID_gear_claim = :id_gear_claim"
						+" AND ID_team = :id_team",
						params);
				
		return updatedRowsCount;
				
	}
	public int deleteGearClaim(Integer id_gear_claim, Integer id_gear, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_claim", id_gear_claim);
		params.addValue("id_gear", id_gear);
		params.addValue("id_team", id_team);
		
		int deletedRows = jdbcTemplate.update(	"delete FROM "+TABLE_NAME
				 +" where id_gear_claim = :id_gear_claim"
				 +" AND id_team = :id_team"
				 +" AND id_gear = :id_gear", params);
		
		return deletedRows;
	}
}
