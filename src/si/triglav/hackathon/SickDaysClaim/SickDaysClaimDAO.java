package si.triglav.hackathon.SickDaysClaim;

import java.util.Date;
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
public class SickDaysClaimDAO {
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<SickDaysClaim> getSickDaysClaims(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		
		List<SickDaysClaim> sickDaysClaimList = jdbcTemplate.query("select ID_sick_day_claim,"
																			+ "date_from,"
																			+ "date_to,"
																			+ "claim_is_valid,"
																			+ "claim_value,"
																			+ "claim_date,"
																			+ "return_account_number "
																			+ "FROM FREELANCE.SICK_DAY_CLAIM "
																			+ "WHERE ID_team= :id_team "
																			+ "AND ID_sickness_insurance IN (SELECT ID_sickness_insurance "
																								+ "FROM FREELANCE.SICKNESS_INSURANCE "
																								+ "WHERE ID_policy_product IN (SELECT ID_policy_product "
																																+ "FROM FREELANCE.POLICY_PRODUCT "
																																+ "WHERE ID_client=:id_client "
																																+ "AND ID_team=:id_team "
																																+ "AND ID_product=2))" , params, new BeanPropertyRowMapper<SickDaysClaim>(SickDaysClaim.class));
		
		
		for(SickDaysClaim sickDaysClaim:sickDaysClaimList){
			sickDaysClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_sick_day_claim", sickDaysClaim.getId_sick_day_claim(), team_key));
		}
		
		return sickDaysClaimList;
	}

	public SickDaysClaim getSickDaysClaimByID(Integer id_client, Integer ID_sick_day_claim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("ID_sick_day_claim", ID_sick_day_claim);

		SickDaysClaim sickDaysClaim;
		
		try{
			sickDaysClaim = jdbcTemplate.queryForObject("SELECT ID_sick_day_claim,"
																+ "date_from,"
																+ "date_to,"
																+ "claim_is_valid,"
																+ "claim_value,"
																+ "claim_date,"
																+ "return_account_number "
														+ "FROM FREELANCE.SICK_DAY_CLAIM "
														+ "WHERE ID_sickness_insurance= (SELECT ID_sickness_insurance FROM FREELANCE.SICKNESS_INSURANCE WHERE ID_policy_product IN (SELECT ID_policy_product "
																																					+ "FROM FREELANCE.POLICY_PRODUCT "
																																					+ "WHERE ID_client=:id_client "
																																					+ "AND ID_team=:id_team "
																																					+ "AND ID_product=2)) "
														+ "AND ID_team= :id_team "
														+ "AND ID_sick_day_claim = :ID_sick_day_claim", params , new BeanPropertyRowMapper<SickDaysClaim>(SickDaysClaim.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		sickDaysClaim.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_sick_day_claim", sickDaysClaim.getId_sick_day_claim(), team_key));

		return sickDaysClaim;
	}

	public SickDaysClaim createSickDaysClaim(Integer id_client, SickDaysClaim sickDaysClaim, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		Date actualDateFrom;
		Date actualDateTo;
		
		//for some reason it substracts a day so we add it
		if(sickDaysClaim.getDate_from()!=null)
			actualDateFrom = new Date(sickDaysClaim.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(sickDaysClaim.getDate_to()!=null)
			actualDateTo = new Date(sickDaysClaim.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		params.addValue("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("claim_date", sickDaysClaim.getClaim_date());
		params.addValue("claim_value", sickDaysClaim.getClaim_value());
		params.addValue("claim_is_valid", sickDaysClaim.getClaim_is_valid());
		params.addValue("return_account_number", sickDaysClaim.getReturn_account_number());

		jdbcTemplate.update(
				"insert into FREELANCE.SICK_DAY_CLAIM "
				+ "(date_from,"
				+ "date_to, "
				+ "return_account_number,"
				+ "claim_date,"
				+ "claim_value,"
				+ "claim_is_valid,"
				+ "ID_sickness_insurance,"
				+ "ID_team) "
				+ "values "
				+ "(:date_from, "
				+ ":date_to,"
				+ ":return_account_number,"
				+ ":claim_date,"
				+ ":claim_value,"
				+ ":claim_is_valid,"
				+ "(SELECT ID_sickness_insurance "
						+ "FROM FREELANCE.SICKNESS_INSURANCE "
						+ "WHERE ID_policy_product= (SELECT ID_policy_product "
												+ "FROM FREELANCE.POLICY_PRODUCT "
												+ "WHERE ID_client=:id_client "
												+ "AND ID_team=:id_team "
												+ "AND ID_product = 2)),"
				+ ":id_team)",
				params, generatedKeyHolder);
		
		SickDaysClaim createdSickDaysClaim = getSickDaysClaimByID(id_client, generatedKeyHolder.getKey().intValue(), team_key);
		return createdSickDaysClaim;
	}

	public int updateSickDaysClaim(SickDaysClaim sickDaysClaim, Integer id_sick_day_claim, Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		params.addValue("id_sick_day_claim", id_sick_day_claim);

		Date actualDateFrom;
		Date actualDateTo;
		
		//for some reason it substracts a day so we add it
		if(sickDaysClaim.getDate_from()!=null)
			actualDateFrom = new Date(sickDaysClaim.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(sickDaysClaim.getDate_to()!=null)
			actualDateTo = new Date(sickDaysClaim.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		params.addValue("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);

		params.addValue("claim_is_valid", sickDaysClaim.getClaim_is_valid());
		params.addValue("claim_value",  sickDaysClaim.getClaim_value());
		params.addValue("claim_date", sickDaysClaim.getClaim_date());
		params.addValue("return_account_number", sickDaysClaim.getReturn_account_number());

		int updatedRows = jdbcTemplate.update("UPDATE FREELANCE.SICK_DAY_CLAIM "
											 +" SET (date_from,date_to,claim_is_valid,claim_value,claim_date,return_account_number) "
											 + " = (:date_from,:date_to,:claim_is_valid,:claim_value,:claim_date,:return_account_number) "
											 +" WHERE ID_sick_day_claim = :id_sick_day_claim "
											 + "AND ID_team=id_team", params);
		
		return updatedRows;
	}

	public int deleteSickDaysClaim(Integer ID_sick_day_claim, Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_sick_day_claim", ID_sick_day_claim);

		
		int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.SICK_DAY_CLAIM "
											 +" WHERE ID_team = :id_team"
											 +" AND ID_sick_day_claim = :ID_sick_day_claim", params);
		return deletedRows;	}
}
