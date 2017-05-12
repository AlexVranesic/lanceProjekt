package si.triglav.hackathon.LiabilityClaim;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.File.FileDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class LiabilityClaimDAO {
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<LiabilityClaim> getLiabilityClaims(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		
		
		List<LiabilityClaim> liabilityClaimList = jdbcTemplate.query("select ID_liability_claim,"
																			+ "description,"
																			+ "claim_is_valid,"
																			+ "claim_value,"
																			+ "claim_date,"
																			+ "return_account_number "
																			+ "FROM FREELANCE.LIABILITY_CLAIM "
																			+ "WHERE ID_team= :id_team "
																			+ "AND ID_liability IN (SELECT ID_liability "
																								+ "FROM FREELANCE.LIABILITY "
																								+ "WHERE ID_policy_product= (SELECT ID_policy_product "
																																+ "FROM FREELANCE.POLICY_PRODUCT "
																																+ "WHERE ID_client=:id_client "
																																+ "AND ID_team=:id_team "
																																+ "AND ID_product=4))" , params, new BeanPropertyRowMapper<LiabilityClaim>(LiabilityClaim.class));
		
		
		for(LiabilityClaim liabilityClaim:liabilityClaimList){
			liabilityClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_liability_claim", liabilityClaim.getId_liability_claim(), team_key));
		}
		
		return liabilityClaimList;
	}
	
	public LiabilityClaim getLiabilityClaimByID(Integer id_client, Integer ID_liability_claim, Integer team_key){
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("ID_liability_claim", ID_liability_claim);

		LiabilityClaim liabilityClaim;
		
		try{
			liabilityClaim = jdbcTemplate.queryForObject("SELECT ID_liability_claim,"
																+ "description,"
																+ "claim_is_valid,"
																+ "claim_value,"
																+ "claim_date,"
																+ "return_account_number "
														+ "FROM FREELANCE.LIABILITY_CLAIM "
														+ "WHERE ID_liability= (SELECT ID_liability FROM FREELANCE.LIABILITY WHERE ID_policy_product= (SELECT ID_policy_product "
																																					+ "FROM FREELANCE.POLICY_PRODUCT "
																																					+ "WHERE ID_client=:id_client "
																																					+ "AND ID_team=:id_team)) "
														+ "AND ID_team= :id_team "
														+ "AND ID_liability_claim = :ID_liability_claim", params , new BeanPropertyRowMapper<LiabilityClaim>(LiabilityClaim.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		liabilityClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_liability_claim", liabilityClaim.getId_liability_claim(), team_key));

		return liabilityClaim;
		
	}
	
	public LiabilityClaim createLiabilityClaim(Integer id_client, LiabilityClaim liabilityClaim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("description", liabilityClaim.getDescription());
		params.addValue("return_account_number", liabilityClaim.getReturn_account_number());
		params.addValue("claim_date", liabilityClaim.getClaim_date());
		params.addValue("claim_value", liabilityClaim.getClaim_value());
		params.addValue("claim_is_valid", liabilityClaim.getClaim_is_valid());
		
		jdbcTemplate.update(
				"insert into FREELANCE.LIABILITY_CLAIM (description, "
				+ "return_account_number,"
				+ "claim_date,"
				+ "claim_value,"
				+ "claim_is_valid,"
				+ "ID_liability,"
				+ "ID_team) "
				+ "values (:description, "
				+ ":return_account_number,"
				+ ":claim_date,"
				+ ":claim_value,"
				+ ":claim_is_valid,"
				+ "(SELECT ID_liability "
						+ "FROM FREELANCE.LIABILITY "
						+ "WHERE ID_policy_product= (SELECT ID_policy_product "
												+ "FROM FREELANCE.POLICY_PRODUCT "
												+ "WHERE ID_client=:id_client "
												+ "AND ID_team=:id_team)),"
				+ ":id_team)",
				params, generatedKeyHolder);
		
		LiabilityClaim createdLiabilityClaim = getLiabilityClaimByID(id_client, generatedKeyHolder.getKey().intValue(), team_key);
		return createdLiabilityClaim;

	}

	public int updateLiabilityClaim(LiabilityClaim liabilityClaim, Integer id_liability_claim, Integer id_client, Integer team_key) {

		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("id_liability_claim", id_liability_claim);
		
		params.addValue("description", liabilityClaim.getDescription());
		params.addValue("claim_is_valid", liabilityClaim.getClaim_is_valid());
		params.addValue("claim_value", liabilityClaim.getClaim_value());
		params.addValue("claim_date", liabilityClaim.getClaim_date());
		params.addValue("return_account_number", liabilityClaim.getReturn_account_number());

		int updatedRows = jdbcTemplate.update("UPDATE FREELANCE.LIABILITY_CLAIM "
											 +" SET (description,claim_is_valid,claim_value,claim_date,return_account_number) "
											 + " = (:description,:claim_is_valid,:claim_value,:claim_date,:return_account_number) "
											 +" WHERE ID_liability_claim = :id_liability_claim "
											 + "AND ID_team=id_team", params);
		
		return updatedRows;
	}

	public int deleteLiabilityClaim(Integer id_claim, Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_liability_claim", id_claim);

		
		int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.LIABILITY_CLAIM "
											 +" WHERE ID_team = :id_team"
											 +" AND ID_liability_claim = :ID_liability_claim", params);
		return deletedRows;
	}
	
}
