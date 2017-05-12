package si.triglav.hackathon.ClientsClient;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ClientsClientDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
/*
	ID_clients_client;
	private String name;
	private Integer tax_id;
	private Integer id_team;
	private Double risk_contract_percent;
	*/
	
	private static final String CLIENT_CLIENTS_COLUMN_LIST = "ID_clients_client,name,tax_id,risk_contract_percent";
	private static final String TABLE_NAME = "FREELANCE.CLIENTS_CLIENT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ClientsClient> getClientsClientList(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		
		List<ClientsClient> clientsClientList = jdbcTemplate.query("select DISTINCT CC."+CLIENT_CLIENTS_COLUMN_LIST
																	+" from "+TABLE_NAME +" CC "
																	+ "LEFT JOIN FREELANCE.CONTRACT C ON C.ID_clients_client=CC.ID_clients_client "
																	+" WHERE ID_policy_product IN (SELECT ID_policy_product "
																							+" FROM FREELANCE.POLICY_PRODUCT "
																							+" WHERE ID_client= :id_client "
																							+" AND ID_product=1 ) "
																	+ "AND CC.ID_team = :id_team ", params, new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		return clientsClientList;
	}
		
	public ClientsClient getClientsClientById(Integer ID_clients_client, Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("ID_clients_client", ID_clients_client);
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		params.addValue("id_team", id_team);
		
		ClientsClient clientsClient;
		try{
			 clientsClient = jdbcTemplate.queryForObject("select "+CLIENT_CLIENTS_COLUMN_LIST+" from "+TABLE_NAME+" where ID_clients_client = :ID_clients_client AND id_team="+id_team, params , new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		return clientsClient;
	}
	
	public ClientsClient getClientsClientByIdViaContract(Integer id_contract, Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_contract", id_contract);

		ClientsClient clientsClient;
		
		try{
		 clientsClient= jdbcTemplate.queryForObject("select CC."+CLIENT_CLIENTS_COLUMN_LIST
													+" from "+TABLE_NAME +" CC "
													+ "LEFT JOIN FREELANCE.CONTRACT C ON C.ID_clients_client=CC.ID_clients_client "
													+" WHERE C.ID_contract = :id_contract "
													+ "AND CC.ID_team = :id_team ", params, new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
		
		return clientsClient;
	}
	

	public List<ClientsClient> getAllClients(Integer team_key) {

		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		
		List<ClientsClient> clientsClientList = jdbcTemplate.query("select "+CLIENT_CLIENTS_COLUMN_LIST
																	+" from "+TABLE_NAME
																	+" WHERE ID_team = :id_team ", params, new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		return clientsClientList;
	}
	
	
	public ClientsClient createClientsClient(ClientsClient clientsClient, Integer team_key) {
				
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name,tax_id,id_team,risk_contract_percent) values (:name,:tax_id,"+teamDAO.getTeamIdByKey(team_key)+",:risk_contract_percent)",
				new BeanPropertySqlParameterSource(clientsClient), generatedKeyHolder);
		
		ClientsClient createdClientsClient = getClientsClientById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdClientsClient;

	}

	
	
	
	public int updateClientsClient(ClientsClient clientsClient, Integer team_key, Integer id_clients_client) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_clients_client", id_clients_client);
		params.addValue("name", clientsClient.getName());
		params.addValue("tax_id", clientsClient.getTax_id());
		params.addValue("risk_contract_percent", clientsClient.getRisk_contract_percent());

		int updatedRowsCount = jdbcTemplate.update(
				 "UPDATE "+TABLE_NAME
				+" SET (name,tax_id,risk_contract_percent) = (:name,:tax_id,risk_contract_percent) "
				+" WHERE ID_clients_client = :ID_clients_client",
		new BeanPropertySqlParameterSource(clientsClient));
		
		
		return updatedRowsCount;
	}

	
	public int deleteClientsClient(Integer ID_clients_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_clients_client", ID_clients_client);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_clients_client = :ID_clients_client", params);
		return deletedRows;
	}


	

}
