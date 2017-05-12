package si.triglav.hackathon.LiabilityPolicy;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.LiabilityClaim.LiabilityClaimDAO;
import si.triglav.hackathon.team.TeamDAO;


@Repository
public class LiabilityPolicyDAO {
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private LiabilityClaimDAO liabilityClaimDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public LiabilityPolicy getLiabilityPolicy(Integer id_client, Integer team_key){
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		LiabilityPolicy liabilityPolicy;
		
		try{
			liabilityPolicy = jdbcTemplate.queryForObject("SELECT ID_liability,"
																+ "date_from,"
																+ "date_to,"
																+ "premium_price,"
																+ "max_claim_value "
														+ "FROM FREELANCE.LIABILITY L "
														+ "LEFT JOIN FREELANCE.POLICY_PRODUCT P "
																+ "ON P.ID_policy_product = L.ID_policy_product "
														+ "WHERE P.ID_product=4 "
														+ "AND P.ID_client=:id_client "
														+ "AND L.ID_team= :id_team "
														+ "FETCH FIRST 1 ROW ONLY", params , new BeanPropertyRowMapper<LiabilityPolicy>(LiabilityPolicy.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		liabilityPolicy.setLiabilityClaims(liabilityClaimDAO.getLiabilityClaims(id_client, team_key));

		return liabilityPolicy;
		
	}
	
	public LiabilityPolicy createLiabilityPolicy(Integer id_client, LiabilityPolicy liabilityPolicy, Integer team_key){
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(liabilityPolicy.getDate_from()!=null)
			actualDateFrom = new Date(liabilityPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(liabilityPolicy.getDate_to()!=null)
			actualDateTo = new Date(liabilityPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"INSERT INTO FREELANCE.POLICY_PRODUCT (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 4)",
				params, generatedKeyHolder);
		
		Integer newIDOfPolicyProduct=generatedKeyHolder.getKey().intValue();
		
		params = new MapSqlParameterSource("premium_price", liabilityPolicy.getPremium_price());
		params.addValue("max_claim_value", liabilityPolicy.getMax_claim_value());
		params.addValue("ID_policy_product", newIDOfPolicyProduct);
		params.addValue("ID_team", id_team);
		
		jdbcTemplate.update(
				"INSERT INTO FREELANCE.LIABILITY (premium_price, max_claim_value, ID_policy_product, ID_team) values (:premium_price, :max_claim_value, :ID_policy_product, :ID_team)",
				params, generatedKeyHolder);
		
		LiabilityPolicy createdLiabilityPolicy = getLiabilityPolicy(id_client, team_key);
		
		
		return createdLiabilityPolicy;
	}
	
	/*public int deleteContract(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.LIABILITY "
											 +" WHERE ID_team = :id_team"
											 +" AND ID_policy_product IN (SELECT ID_policy_product "
											 							+ "FROM FREELANCE.POLICY_PRODUCT "
											 							+ " WHERE ID_client= :id_client "
											 							+ " AND ID_team=:id_team)", params);
		
		deletedRows = deletedRows+jdbcTemplate.update("DELETE FROM FREELANCE.POLICY_PRODUCT "
													 +" WHERE ID_team = :id_team"
													 +" AND ID_client=:id_client", params);
		
		return deletedRows;
	}*/

	public int updateLiabilityPolicy(LiabilityPolicy liabilityPolicy, Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("premium_price", liabilityPolicy.getPremium_price());
		params.addValue("max_claim_value", liabilityPolicy.getMax_claim_value());
		
		
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(liabilityPolicy.getDate_from()!=null)
			actualDateFrom = new Date(liabilityPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(liabilityPolicy.getDate_to()!=null)
			actualDateTo = new Date(liabilityPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		params.addValue("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);

		int updatedRows = jdbcTemplate.update("UPDATE FREELANCE.LIABILITY "
											 +" SET (premium_price,max_claim_value) = (:premium_price,:max_claim_value) "
											 +" WHERE ID_policy_product IN (SELECT ID_policy_product "
											 							+ "FROM FREELANCE.POLICY_PRODUCT "
											 							+ " WHERE ID_client= :id_client "
											 							+ " AND ID_team=:id_team"
											 							+ " AND ID_product=4)", params);
		
		updatedRows = updatedRows+jdbcTemplate.update("UPDATE FREELANCE.POLICY_PRODUCT "
													 +" SET(date_from, date_to) = (:date_from, :date_to)"
													 + "WHERE ID_team = :id_team"
													 +" AND ID_client=:id_client", params);
		
		return updatedRows;
	}

	public int deleteLiabilityPolicy(Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		
		int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.LIABILITY "
											 +" WHERE  ID_policy_product IN (SELECT ID_policy_product "
											 							+ "FROM FREELANCE.POLICY_PRODUCT "
											 							+ " WHERE ID_client= :id_client "
											 							+ " AND ID_team=:id_team)"
											 + "AND ID_team = :id_team", params);
		
		 deletedRows = deletedRows+jdbcTemplate.update("DELETE FROM FREELANCE.POLICY_PRODUCT "
				 +" WHERE  ID_policy_product IN (SELECT ID_policy_product "
				 							+ "FROM FREELANCE.POLICY_PRODUCT "
				 							+ " WHERE ID_client= :id_client "
				 							+ " AND ID_team=:id_team)", params);
		
		return deletedRows;
	}
	
	
}
