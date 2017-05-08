package si.triglav.hackathon.Contract;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.ClientsClient.ClientsClientDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractDAO {
	@Autowired
	private TeamDAO teamDAO;
	

	@Autowired
	private ClientsClientDAO clientsClientDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String CONTRACT_COLUMN_LIST = "ID_contract, contract_value, payment_due_to, is_paid, ID_clients_client";
	private static final String TABLE_NAME = "FREELANCE.CONTRACT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public Contract getContractById(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_contract", id);
		params.addValue("id_team", id_team);
		
		Contract contract = jdbcTemplate.queryForObject("select "+CONTRACT_COLUMN_LIST+" from "+TABLE_NAME+" where ID_contract = :id_contract AND id_team= :id_team", params , new BeanPropertyRowMapper<Contract>(Contract.class));
		
		contract.setClients_client(clientsClientDAO.getClientsClientById(contract.getId_clients_client(), team_key));
		
		return contract;
	}

	public Contract createContract(Integer team_key, Integer id_client, Contract contract) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("contract_value", contract.getContract_value());
		params.addValue("payment_due_to", contract.getPayment_due_to());
		params.addValue("is_paid", contract.getIs_paid());
		params.addValue("id_clients_client", contract.getId_clients_client());
		params.addValue("id_team", id_team);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"INSERT INTO "+TABLE_NAME+" (contract_value, payment_due_to, is_paid, ID_policy_product, ID_clients_client, ID_team) "
						+ "VALUES (:contract_value, "
						+ ":payment_due_to, "
						+ ":is_paid, "
						+ "(SELECT ID_policy_product "
								+ "FROM LANCEOUT.POLICY_PRODUCT"
								+ "WHERE ID_product = 1"
								+ "AND ID_client = :id_clients_client), "
						+ ":id_clients_client, "
						+ ":id_team)",
						params, generatedKeyHolder);
		
		Contract createdContract = getContractById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdContract;
	}
	
	
	public List<Contract> getContractList(Integer team_key,Integer id_client) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);

		List<Contract> contractList = jdbcTemplate.query("SELECT "+CONTRACT_COLUMN_LIST
														+" FROM "+TABLE_NAME
														+" WHERE ID_policy_product = (SELECT P.ID_policy_product "
																					+ "FROM FREELANCE.POLICY_PRODUCT P "
																					+ "WHERE id_product=1 "
																					+ "AND id_team= :id_team "
																					+ "AND id_client = :id_client ) "
														+ "AND id_team= :id_team ", params, new BeanPropertyRowMapper<Contract>(Contract.class));
		
		for(Contract contract: contractList){
			contract.setClients_client(clientsClientDAO.getClientsClientById(contract.getId_clients_client(), team_key));
		}
		
		return contractList;
	}
	
	public int deleteContract(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_contract", id);

		
		int deletedRows = jdbcTemplate.update(	"delete from "+TABLE_NAME
											 +" where ID_product = 1"
											 +" AND ID_team = :id_team"
											 +" AND ID_contract = :ID_contract", params);
		return deletedRows;
	}
	
	
}
