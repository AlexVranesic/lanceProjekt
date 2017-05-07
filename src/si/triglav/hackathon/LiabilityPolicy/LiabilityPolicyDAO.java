/*package si.triglav.hackathon.LiabilityPolicy;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.team.TeamDAO;
import si.triglav.hackathon.File.FileDAO;

import si.triglav.hackathon.File.*;

@Repository
public class LiabilityPolicyDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String LIABILITY_POLICY_COLUMN_LIST = "id_liability_claim, description, id_liability, id_claim, id_team, is_valid, claim_value, claim_date, returnAccountNumber";
	private static final String TABLE_NAME = "FREELANCE.LIABILITY_CLAIM";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<LiabilityClaim> getLiabilityClaimList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<LiabilityClaim> liabilityClaimList = jdbcTemplate.query("select "+LIABILITY_CLAIM_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<LiabilityClaim>(LiabilityClaim.class));
		
		for(LiabilityClaim liabilityClaim: liabilityClaimList){
			liabilityClaim.setFile(fileDAO.getFileById(liabilityClaim.getFile().getId_file(), team_key));
		}
		
		return liabilityClaimList;
	}
	
/*
	public List<RepairService> getRepairServiceList() {
		List<RepairService> repairServiceList = jdbcTemplate.query("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME, new BeanPropertyRowMapper<RepairService>(RepairService.class));
		return repairServiceList;
	}*/
/*	
	public LiabilityClaim getLiabilityClaimById(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_liability_claim", id);
		params.addValue("id_team", id_team);
		
		LiabilityClaim liabilityClaim = jdbcTemplate.queryForObject("select "+LIABILITY_CLAIM_COLUMN_LIST+" from "+TABLE_NAME+" where id_liability_claim = :id_liability_claim AND id_team= :id_team", params , new BeanPropertyRowMapper<LiabilityClaim>(LiabilityClaim.class));
		
		liabilityClaim.setFile(fileDAO.getFileById(liabilityClaim.getFile().getId_file(), team_key));
		
		return liabilityClaim;
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
	/*
	public int deleteLiabilityClaim(Integer id_liability_claim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_repair_service", id_liability_claim);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_liability_claim = :id_liability_claim", params);
		return deletedRows;
	}
}
