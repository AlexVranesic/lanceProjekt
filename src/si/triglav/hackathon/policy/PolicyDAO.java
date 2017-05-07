package si.triglav.hackathon.policy;

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

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class PolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String POLICY_COLUMN_LIST = "ID_policy";
	private static final String TABLE_NAME = "FREELANCE.POLICY";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	/*
	 * this method allows policy to be viewed by all
	 */
	
	public List<Policy> getPolicyList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<Policy> policyList = jdbcTemplate.query("select "+POLICY_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<Policy>(Policy.class));
		
		for(Policy policy: policyList){
		//	repairService.setGear_type(policyDAO.getGearTypeById(repairService.getId_gear_type(), team_key));
		}
		
		return policyList;
	}
	
	public Policy getPolicyById(Integer idPolicy) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", idPolicy);
		Policy policy = jdbcTemplate.queryForObject("select " + POLICY_COLUMN_LIST + " from FREELANCE.POLICY where id = :id", params, new BeanPropertyRowMapper<Policy>(Policy.class));
		
		if (policy == null)
		{
			throw new Exception("Policy with id " + idPolicy + " does not exist in database!");
		}
		else
		{
			return policy;
		}
	}
	
	/*
	 * this method allows policy to be viewed only for allowed team
	 */
	
	public Policy getAllowedPolicyById(Integer idPolicy) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", idPolicy);
		Policy policy = jdbcTemplate.queryForObject("select " + POLICY_COLUMN_LIST + " from FREELANCE.POLICY where id = :id", params, new BeanPropertyRowMapper<Policy>(Policy.class));
		
		//Integer currentTeamId = null;
		/* currentTeamId = KeyChecker.getTeamKey(); naceloma se bodo oni prijavili na bazo
		 * 												    z user + geslom. Na podlagi tega, jim baza dodali nek key, ki 
		 *                                                  se hrani tako da nimajo oni dostop do njega.
		 *                                                  Na tem mestu se zahteva, da program sporoci, uporabnikov ekipni key
		 *                                                  na podlagi katerega se dovoli, alipa ne, dostop do baze
		
		*/

		if (policy == null)
		{
			throw new Exception("Policy with id " + idPolicy + " does not exist in database!");
		}
		else
		{
//			if (policy.getTeamId() == currentTeamId)
//			{
				return policy;
//			}
//			else
//			{
//				throw new Exception("team " + policy.getTeamId() + " is accessing other's team policy " + idPolicy);
//			}
		}
	}

	public Policy createPolicy(Policy policy) throws Exception {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into schema.POLICY (" + POLICY_COLUMN_LIST + ") values (:id_policy, :id_occupation, :id_client, :id_ekipa)",
				new BeanPropertySqlParameterSource(policy), generatedKeyHolder);
 		
		Policy createdPeson = getPolicyById(generatedKeyHolder.getKey().intValue());
		//Policy createdPeson = getAllowedPolicyById(generatedKeyHolder.getKey().intValue());
		return createdPeson;

	}

	public int deletePerson(Integer id) {
		
		// tukaj lahko tudi preverjas, ce pravilna skupina brise zapis in ce ne, potem ne dovoli
		int deletedRows = jdbcTemplate.update("delete from schema.POLICY where id = :id", new MapSqlParameterSource("id", id));
		return deletedRows;
	}
}
