package si.triglav.hackathon.GearClaim;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.RepairService.RepairServiceDAO;
import si.triglav.hackathon.team.TeamDAO;
import si.triglav.hackathon.ClaimType.ClaimType;
import si.triglav.hackathon.ClaimType.ClaimTypeDAO;
import si.triglav.hackathon.Client.Client;
import si.triglav.hackathon.File.*;

@Repository
public class GearClaimDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GEAR_CLAIM_COLUMN_LIST = "id_gear_claim, event_date, event_description, claim_is_valid, claim_value, claim_date, returnAccountNumber";
	private static final String TABLE_NAME = "FREELANCE.GEAR_CLAIM";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;

	@Autowired
	private ClaimTypeDAO claim_typeDAO;
	
	@Autowired
	private RepairServiceDAO reapir_servicesDAO;
	
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
		params.addValue("id_client", id_client);
		params.addValue("id_gear", id_gear);
		params.addValue("id_gear_claim", id_gear_claim);
		
		GearClaim gearClaim = jdbcTemplate.queryForObject("select "+GEAR_CLAIM_COLUMN_LIST+" from "+TABLE_NAME+" where id_gear_claim = :id_gear_claim AND id_gear= :id_gear AND id_client= :id_client AND id_team= :id_team", params , new BeanPropertyRowMapper<GearClaim>(GearClaim.class));
		
		//gearClaim.setFiles(fileDAO.getFilesOfGearClaimListFromIdGearClaim(team_key , gearClaim.getId_gear_claim()));
		
		//gearClaim.(fileDAO.getFileById(gearClaim.getId_gear_claim(), team_key));
		gearClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("id_gear_claim", gearClaim.getId_gear_claim(), team_key));

		
		return gearClaim;
	}
	/*
	public GearClaim createGearClaim(GearClaim gearClaim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date event_date;
		
		if(gearClaim.getEvent_date()!=null){
			event_date = new Date(gearClaim.getEvent_date().getTime()+(24*60*60*1000));
		}
		else{
			event_date = null;
		}
		
		String event_description=gearClaim.getEvent_description();
		Integer claim_is_valid=gearClaim.getClaim_is_valid();
		Double claim_value=gearClaim.getClaim_value();
		Date claim_date;
		
		if(gearClaim.getEvent_date()!=null){
			claim_date = new Date(gearClaim.getClaim_date().getTime()+(24*60*60*1000));
		}
		else{
			claim_date = null;
		}
		
		String returnAccountNumber=gearClaim.getReturnAccountNumber();
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (event_date, event_description, claim_is_valid, id_team ,claim_value, claim_date, returnAccountNumber) values (:event_date, :event_description, :claim_is_valid, "+id_team+", :claim_value, :claim_date, :returnAccountNumber)",
				new BeanPropertySqlParameterSource(gearClaim), generatedKeyHolder);
		
		//id gear še moram dobiti..
		//(Integer team_key, Integer id_client, Integer id_gear, Integer id_gear_claim) {
		
		GearClaim createdGearClaim = getGearClaimById(team_key, generatedKeyHolder.getKey().intValue());
		return createdGearClaim;

	}
	*/
	public int updateGearClaim(GearClaim gearClaim, Integer team_key, Integer  ID_client, Integer id_gear) {
		
		Integer id_team = teamDAO.getTeamIdByKey(team_key);
		
		Date event_date;
		Date claim_date;
		
		
		String event_description = gearClaim.getEvent_description();
		Integer claim_is_valid = gearClaim.getClaim_is_valid();
		Double claim_value = gearClaim.getClaim_value();
		Integer id_gear_claim = gearClaim.getId_gear_claim();
		String returnAccountNumber = gearClaim.getReturnAccountNumber();
		
		if(gearClaim.getEvent_date()!=null)
			event_date = new Date(gearClaim.getEvent_date().getTime()+(24*60*60*1000));
		else
			event_date = null;
		
		if(gearClaim.getClaim_date()!=null)
			claim_date = new Date(gearClaim.getClaim_date().getTime()+(24*60*60*1000));
		else
			claim_date = null;
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("event_description", event_description);
		
		params.addValue("event_date", event_date);
		params.addValue("claim_is_valid", claim_is_valid);
		params.addValue("claim_value", claim_value);
		params.addValue("claim_date", claim_date);
		params.addValue("id_gear_claim", id_gear_claim);
		params.addValue("returnAccountNumber", returnAccountNumber);
		params.addValue("id_team", id_team);

		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (event_description, event_date, claim_is_valid, claim_value, claim_date, id_gear_claim, returnAccountNumber) = (:event_description, :event_date,  :claim_is_valid, :claim_value, :claim_date, :id_gear_claim, :returnAccountNumber) "
						+" WHERE id_gear_claim = :id_gear_claim"
						+" AND id_team = :ID_team"
						+" AND ID_client = :ID_client",
						params);
				
		return updatedRowsCount;
				
	}
	public int deleteGearClaim(Integer id_gear_claim, Integer id_gear, Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear_claim", id_gear_claim);
		params.addValue("id_gear", id_gear);
		params.addValue("id_client", id_client);
		params.addValue("id_team", id_team);
		
		int deletedRows = jdbcTemplate.update(	"delete from "+TABLE_NAME
				 +" where id_gear_claim = :id_gear_claim"
				 +" AND id_team = :id_team"
				 +" AND id_gear = :id_gear"
				 +" AND ID_client = :ID_client", params);
		
		return deletedRows;
	}
}
