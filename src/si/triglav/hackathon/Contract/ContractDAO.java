package si.triglav.hackathon.Contract;

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

import si.triglav.hackathon.ClientsClient.ClientsClientDAO;
import si.triglav.hackathon.File.FileDAO;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractDAO {
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;

	@Autowired
	private ClientsClientDAO clientsClientDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String CONTRACT_COLUMN_LIST = "ID_contract, contract_value, payment_due_to, is_paid, ID_clients_client,claim_is_valid,claim_value,claim_date,return_account_number";
	private static final String TABLE_NAME = "FREELANCE.CONTRACT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public Contract getContractById(Integer id, Integer team_key, Integer id_client) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_contract", id);
		params.addValue("id_team", id_team);
		params.addValue("ID_client", id_client);
		
		Contract contract;
		try{
			 contract = jdbcTemplate.queryForObject("select "+CONTRACT_COLUMN_LIST
															+" from "+TABLE_NAME
															+" WHERE ID_contract = :id_contract"
															+ " AND ID_policy_product=(SELECT ID_policy_product "
																					+ "FROM FREELANCE.POLICY_PRODUCT "
																					+ "WHERE ID_client= :ID_client "
																					+ "AND ID_product=1 )"
															+ " AND id_team= :id_team", params , new BeanPropertyRowMapper<Contract>(Contract.class));
			
			contract.setClients_client(clientsClientDAO.getClientsClientById(contract.getId_clients_client(), team_key));
			contract.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_contract", contract.getId_contract(), team_key));
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		
		return contract;
	}

	public Contract createContract(Integer team_key, Integer id_client, Contract contract) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("contract_value", contract.getContract_value());
		params.addValue("payment_due_to", contract.getPayment_due_to());
		params.addValue("is_paid", contract.getIs_paid());
		params.addValue("ID_clients_client", contract.getId_clients_client());
		params.addValue("ID_team", id_team);
		params.addValue("ID_client", id_client);


		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME
						+" (contract_value,"
						+ "payment_due_to,"
						+ "is_paid,"
						+ "ID_policy_product,"
						+ "ID_clients_client,"
						+ "ID_team)"
						+ "VALUES(:contract_value,"
						+ ":payment_due_to,"
						+ ":is_paid,"
						+ "(SELECT ID_policy_product "
								+ "FROM FREELANCE.POLICY_PRODUCT "
								+ "WHERE ID_client= :ID_client "
								+ "AND ID_product=1 ),"
						+ ":ID_clients_client,"
						+ ":ID_team)",
				params, generatedKeyHolder);
		
		Contract createdContract = getContractById(generatedKeyHolder.getKey().intValue(), team_key, id_client);
		return createdContract;
	}
	
	public Integer updateContract(Integer team_key, Integer id_client, Integer id_contract, Contract contract) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("contract_value", contract.getContract_value());
		params.addValue("payment_due_to", contract.getPayment_due_to());
		params.addValue("is_paid", contract.getIs_paid());
		params.addValue("ID_clients_client", contract.getId_clients_client());
		params.addValue("ID_client", id_client);
		params.addValue("ID_contract", id_contract);
		params.addValue("ID_team", id_team);
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
						+" SET (contract_value, payment_due_to, is_paid, ID_clients_client) = "
						+ "(:contract_value,"
						+ ":payment_due_to,"
						+ ":is_paid,"
						+ ":ID_clients_client) "
						+ "WHERE ID_policy_product = (SELECT ID_policy_product "
													+ "FROM FREELANCE.POLICY_PRODUCT "
													+ "WHERE ID_client= :ID_client "
													+ "AND ID_product = 1) "
						+ "AND ID_team= :ID_team "
						+ "AND ID_contract= :ID_contract ",
						params);
		
		return updatedRowsCount;
	}
	
	public int updateContractClaim(Integer team_key, Integer id_client, Integer id_contract, Contract contract) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("claim_is_valid", contract.getClaim_is_valid());
		params.addValue("claim_value", contract.getClaim_value());
		
		Date claim_date;
		if(contract.getClaim_date()!=null)
			claim_date = new Date(contract.getClaim_date().getTime()+(24*60*60*1000));
		else
			claim_date = null;
		
		params.addValue("claim_date", claim_date);
		params.addValue("return_account_number", contract.getReturn_account_number());
		params.addValue("ID_client", id_client);
		params.addValue("ID_contract", id_contract);
		params.addValue("ID_team", id_team);
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
						+" SET (claim_is_valid, claim_value, claim_date, return_account_number) = "
						+ "(:claim_is_valid,"
						+ ":claim_value,"
						+ ":claim_date,"
						+ ":return_account_number) "
						+ "WHERE ID_policy_product = (SELECT ID_policy_product "
									+ "FROM FREELANCE.POLICY_PRODUCT "
									+ " WHERE ID_client= :ID_client"
									+ " AND ID_product = 1) "
						+ "AND ID_team= :ID_team "
						+ "AND ID_contract= :ID_contract ",
						params);
		
		return updatedRowsCount;
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
														+ " AND id_team= :id_team ", params, new BeanPropertyRowMapper<Contract>(Contract.class));
		
		for(Contract contract: contractList){
			contract.setClients_client(clientsClientDAO.getClientsClientById(contract.getId_clients_client(), team_key));
			contract.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_contract", contract.getId_contract(), team_key));
		}
		
		return contractList;
	}
	
	public int deleteContract(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_contract", id);

		
		int deletedRows = jdbcTemplate.update("DELETE FROM "+TABLE_NAME
											 +" WHERE ID_team = :id_team"
											 +" AND ID_contract = :ID_contract", params);
		return deletedRows;
	}
	
	
}
