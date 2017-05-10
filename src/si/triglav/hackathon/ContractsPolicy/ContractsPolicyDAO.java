package si.triglav.hackathon.ContractsPolicy;

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

import si.triglav.hackathon.Contract.ContractDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractsPolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private ContractDAO contractDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String CONTRACT_COLUMN_LIST = "date_from,date_to";
	private static final String TABLE_NAME = "FREELANCE.POLICY_PRODUCT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public ContractsPolicy getContractsPolicy(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		ContractsPolicy contractsPolicy;
		
		try{
			contractsPolicy = jdbcTemplate.queryForObject("select "+CONTRACT_COLUMN_LIST+" from "+TABLE_NAME+" WHERE ID_product=1 AND id_client=:id_client AND id_team= :id_team", params , new BeanPropertyRowMapper<ContractsPolicy>(ContractsPolicy.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		contractsPolicy.setContracts(contractDAO.getContractList(team_key, id_client));

		return contractsPolicy;
	}
	
	public ContractsPolicy createContractPolicy(Integer id_client, ContractsPolicy contractsPolicy, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(contractsPolicy.getDate_from()!=null)
			actualDateFrom = new Date(contractsPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(contractsPolicy.getDate_to()!=null)
			actualDateTo = new Date(contractsPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 1)",
				params, generatedKeyHolder);
		
		ContractsPolicy createdContractsPolicy = getContractsPolicy(id_client, team_key);
		return createdContractsPolicy;

	}
	
	public int updateContractPolicy(Integer id_client, ContractsPolicy contractsPolicy, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(contractsPolicy.getDate_from()!=null)
			actualDateFrom = new Date(contractsPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(contractsPolicy.getDate_to()!=null)
			actualDateTo = new Date(contractsPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		


		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET  (date_from, date_to) = (:date_from, :date_to) "
						+" WHERE ID_product = 1"
						+" AND ID_team = :ID_team"
						+" AND ID_client = :ID_client",
						params);
		return updatedRowsCount;
	}
	
	public int deleteContractPolicy(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_client", id_client);

		
		int deletedRows = jdbcTemplate.update(	"delete from "+TABLE_NAME
											 +" where ID_product = 1"
											 +" AND ID_team = :id_team"
											 +" AND ID_client = :ID_client", params);
		return deletedRows;
	}
	
}
