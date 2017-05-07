package si.triglav.hackathon.ContractsPolicy;

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

import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractsPolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String CONTRACT_COLUMN_LIST = "ID_policy_product,date_from,date_to";
	private static final String TABLE_NAME = "FREELANCE.POLICY_PRODUCT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ContractsPolicy> getContractsPolicies(Integer id_client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		List<ContractsPolicy> contractsPolicyList = jdbcTemplate.query("select "+CONTRACT_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<ContractsPolicy>(ContractsPolicy.class));
		
		for(ContractsPolicy repairService: contractsPolicyList){
			//repairService.setGear_type(gearTypeDAO.getGearTypeById(repairService.getId_gear_type(), team_key));
		}
		
		return contractsPolicyList;
	}
	
	public ContractsPolicy getContractsPolicyById(Integer id_client, Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("ID_policy_product", id);
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);

		ContractsPolicy contractsPolicy = jdbcTemplate.queryForObject("select "+CONTRACT_COLUMN_LIST+" from "+TABLE_NAME+" where ID_policy_product = :ID_policy_product AND id_client=:id_client AND id_team= :id_team", params , new BeanPropertyRowMapper<ContractsPolicy>(ContractsPolicy.class));
				
		return contractsPolicy;
	}
	
	
	public ContractsPolicy createContractPolicy(Integer id_client, ContractsPolicy contractsPolicy, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", contractsPolicy.getDate_from());
		params.addValue("date_to", contractsPolicy.getDate_to());
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 1)",
				params, generatedKeyHolder);
		
		ContractsPolicy createdContractsPolicy = getContractsPolicyById(id_client, generatedKeyHolder.getKey().intValue(), team_key);
		return createdContractsPolicy;

	}
	
	public int updateContractPolicy(Integer id_client, ContractsPolicy contractsPolicy, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		MapSqlParameterSource params = new MapSqlParameterSource("date_from", contractsPolicy.getDate_from());
		params.addValue("date_to", contractsPolicy.getDate_to());
		params.addValue("ID_policy_product", contractsPolicy.getID_policy_product());
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);

		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET  (date_from, date_to, ID_client) = (:date_from, :date_to, :ID_client) "
						+" WHERE ID_policy_product = :ID_policy_product"
						+" AND ID_team = :ID_team",
						params);
		return updatedRowsCount;
	}
	
}
