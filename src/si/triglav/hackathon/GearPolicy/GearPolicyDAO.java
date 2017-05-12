package si.triglav.hackathon.GearPolicy;

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
import org.springframework.web.bind.annotation.PathVariable;

import si.triglav.hackathon.Gear.GearDAO;
import si.triglav.hackathon.GearClaim.GearClaimDAO;
import si.triglav.hackathon.LiabilityClaim.LiabilityClaim;
import si.triglav.hackathon.LiabilityClaim.LiabilityClaimDAO;
import si.triglav.hackathon.LiabilityPolicy.LiabilityPolicy;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class GearPolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private GearClaimDAO gearClaimDAO;
	
	@Autowired
	private GearPolicyDAO gearPolicyDAO;
	
	@Autowired
	private GearDAO gearDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public GearPolicy getGearPolicy(Integer id_client, Integer team_key){
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		GearPolicy gearPolicy;
		
		try{
			gearPolicy = jdbcTemplate.queryForObject("SELECT date_from, date_to "
													+ "FROM FREELANCE.POLICY_PRODUCT "
													+ "WHERE ID_product=3 "
													+ "AND ID_client=:id_client "
													+ "AND ID_team= :id_team "
													+ "FETCH FIRST 1 ROW ONLY", params , new BeanPropertyRowMapper<GearPolicy>(GearPolicy.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
			
		gearPolicy.setGear(gearDAO.getGearList(team_key, id_client));
		
		return gearPolicy;
	}
	
	public GearPolicy createGearPolicy(Integer id_client, GearPolicy gearPolicy, Integer team_key){
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date actualDateFrom;
		Date actualDateTo;
		
		//for some reason it substracts a day so we add it
		if(gearPolicy.getDate_from()!=null)
			actualDateFrom = new Date(gearPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(gearPolicy.getDate_to()!=null)
			actualDateTo = new Date(gearPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"INSERT INTO FREELANCE.POLICY_PRODUCT (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 3)",
				params, generatedKeyHolder);
		
		Integer newIDOfPolicyProduct=generatedKeyHolder.getKey().intValue();
		
		GearPolicy createdGearPolicy =  getGearPolicy(id_client, team_key); 
		return createdGearPolicy;
	}
	

	public int updateGearPolicy(GearPolicy gearPolicy, Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		Date actualDateFrom;
		Date actualDateTo;
		
		//for some reason it substracts a day so we add it
		if(gearPolicy.getDate_from()!=null)
			actualDateFrom = new Date(gearPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(gearPolicy.getDate_to()!=null)
			actualDateTo = new Date(gearPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		
		params.addValue("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);

		int updatedRows = jdbcTemplate.update("UPDATE FREELANCE.POLICY_PRODUCT "
													 +" SET(date_from, date_to) = (:date_from, :date_to)"
													 + "WHERE ID_team = :id_team"
													 +" AND ID_client=:id_client"
													 +" AND ID_product=3 ", params);
		
		return updatedRows;
	}

	public int deleteGearPolicy(Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		
		 int deletedRows = jdbcTemplate.update("DELETE FROM FREELANCE.POLICY_PRODUCT "
				 							+" WHERE  ID_policy_product IN (SELECT ID_policy_product "
				 							+ "FROM FREELANCE.POLICY_PRODUCT "
				 							+ " WHERE ID_client= :id_client "
				 							+ " AND ID_product=3 )", params);
		
		return deletedRows;
	}
	
	
}
