package si.triglav.hackathon.SickDaysPolicy;

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

import si.triglav.hackathon.SickDaysClaim.SickDaysClaimDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class SickDaysPolicyDAO {
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private SickDaysClaimDAO sickDaysClaimDAO;

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public SickDaysPolicy getSickDaysPolicy(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		SickDaysPolicy sickDaysPolicy;
		
		try{
			sickDaysPolicy = jdbcTemplate.queryForObject("SELECT ID_sickness_insurance,"
																+ "date_from,"
																+ "date_to,"
																+ "premium_price,"
																+ "daily_compensation "
														+ "FROM FREELANCE.SICKNESS_INSURANCE S "
														+ "LEFT JOIN FREELANCE.POLICY_PRODUCT P "
																+ "ON P.ID_policy_product = S.ID_policy_product "
														+ "WHERE P.ID_product=2 "
														+ "AND P.ID_client=:id_client "
														+ "AND S.ID_team= :id_team "
														+ "FETCH FIRST 1 ROW ONLY", params , new BeanPropertyRowMapper<SickDaysPolicy>(SickDaysPolicy.class));
			
			sickDaysPolicy.setSickDayClaims(sickDaysClaimDAO.getSickDaysClaims(id_client, team_key));

		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		
		return sickDaysPolicy;
	}

	public SickDaysPolicy createSickDaysPolicy(Integer id_client, SickDaysPolicy sickDaysPolicy, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(sickDaysPolicy.getDate_from()!=null)
			actualDateFrom = new Date(sickDaysPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(sickDaysPolicy.getDate_to()!=null)
			actualDateTo = new Date(sickDaysPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"INSERT INTO FREELANCE.POLICY_PRODUCT (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 2)",
				params, generatedKeyHolder);
		
		Integer newIDOfPolicyProduct=generatedKeyHolder.getKey().intValue();
		
		params = new MapSqlParameterSource("premium_price", sickDaysPolicy.getPremium_price());
		params.addValue("daily_compensation", sickDaysPolicy.getDaily_compensation());
		params.addValue("ID_policy_product", newIDOfPolicyProduct);
		params.addValue("ID_team", id_team);
		
		jdbcTemplate.update(
				"INSERT INTO FREELANCE.SICKNESS_INSURANCE (premium_price, daily_compensation, ID_policy_product, ID_team) values (:premium_price, :daily_compensation, :ID_policy_product, :ID_team)",
				params, generatedKeyHolder);
		
		SickDaysPolicy createdSickDaysPolicy = getSickDaysPolicy(id_client, team_key);
		
		return createdSickDaysPolicy;
		
	}

	public int updateSickDaysPolicy(SickDaysPolicy sickDaysPolicy, Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		//for some reason it substracts a day so we add it
		Date actualDateFrom;
		Date actualDateTo;
		if(sickDaysPolicy.getDate_from()!=null)
			actualDateFrom = new Date(sickDaysPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(sickDaysPolicy.getDate_to()!=null)
			actualDateTo = new Date(sickDaysPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("premium_price", sickDaysPolicy.getPremium_price());
		params.addValue("daily_compensation", sickDaysPolicy.getDaily_compensation());
		params.addValue("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);

		int updatedRows = jdbcTemplate.update("UPDATE FREELANCE.SICKNESS_INSURANCE "
											 +" SET (premium_price,daily_compensation) = (:premium_price,:daily_compensation) "
											 +" WHERE ID_policy_product IN (SELECT ID_policy_product "
											 							+ "FROM FREELANCE.POLICY_PRODUCT "
											 							+ " WHERE ID_client= :id_client "
											 							+ " AND ID_team=:id_team"
											 							+ " AND ID_product = 2)", params);
		
		updatedRows = updatedRows+jdbcTemplate.update("UPDATE FREELANCE.POLICY_PRODUCT "
													 +" SET(date_from, date_to) = (:date_from, :date_to)"
													 + "WHERE ID_team = :id_team"
													 +" AND ID_client=:id_client"
													 +" AND ID_product = 2 ", params);
		
		return updatedRows;
	}

	public int deleteSickDaysPolicy(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		
		int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.SICKNESS_INSURANCE "
											 +" WHERE  ID_policy_product IN (SELECT ID_policy_product "
											 							+ "FROM FREELANCE.POLICY_PRODUCT "
											 							+ " WHERE ID_client= :id_client "
											 							+ " AND ID_team=:id_team"
											 							+ " AND ID_product = 2)"
											 + "AND ID_team = :id_team", params);
		
		 deletedRows = deletedRows+jdbcTemplate.update("DELETE FROM FREELANCE.POLICY_PRODUCT "
				 +" WHERE  ID_policy_product IN (SELECT ID_policy_product "
				 							+ "FROM FREELANCE.POLICY_PRODUCT "
				 							+ " WHERE ID_client= :id_client "
				 							+ " AND ID_team=:id_team"
				 							+ " AND ID_product = 2)", params);
		
		return deletedRows;
	}
	
}
