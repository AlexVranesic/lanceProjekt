package si.triglav.hackathon.GearClaim;

import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.RepairService.RepairServiceDAO;
import si.triglav.hackathon.team.TeamDAO;
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
					
			gearClaim.setFiles(fileDAO.getFilesOfGearClaimListFromIdGearClaim(team_key , gearClaim.getId_gear_claim()));
					
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
		
		gearClaim.setFiles(fileDAO.getFilesOfGearClaimListFromIdGearClaim(team_key , gearClaim.getId_gear_claim()));
					
		return gearClaim;
	}
	
	public LiabilityClaim createLiabilityClaim(LiabilityClaim liabilityClaim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (description, id_liability, id_claim, id_team, is_valid, claim_value, claim_date, returnAccountNumber) values (:description, :id_liability,  :id_claim, "+id_team+", :is_valid, :claim_value, :claim_date, :returnAccountNumber)",
				new BeanPropertySqlParameterSource(liabilityClaim), generatedKeyHolder);
		
		LiabilityClaim createdLiabilityClaim = getLiabilityClaimById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdLiabilityClaim;

	}

	public int updateLiabilityClaim(LiabilityClaim liabilityClaim, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (description, id_liability, id_claim, is_valid, claim_value, claim_date, returnAccountNumber) = (:description, :id_liability,  :id_claim, :is_valid, :claim_value, :claim_date, :returnAccountNumber) "
						+" WHERE id_liability_claim = :id_liability_claim"
						+" AND ID_team = "+id_team,
				new BeanPropertySqlParameterSource(liabilityClaim));
		return updatedRowsCount;
	}

	/*
	jdbcTemplate.update(
			"insert into "+TABLE_NAME+" (description, id_liability, id_claim, id_team, is_valid, claim_value, claim_date, returnAccountNumber) values (:description, :id_liability,  :id_claim, "+id_team+", :is_valid, :claim_value, :claim_date, :returnAccountNumber)",
			new BeanPropertySqlParameterSource(liabilityClaim), generatedKeyHolder);
	*/
	
	public int deleteLiabilityClaim(Integer id_liability_claim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_repair_service", id_liability_claim);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_liability_claim = :id_liability_claim", params);
		return deletedRows;
	}
}
