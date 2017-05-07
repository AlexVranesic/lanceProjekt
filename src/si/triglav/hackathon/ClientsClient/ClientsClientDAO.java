package si.triglav.hackathon.ClientsClient;

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

import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.RepairService.RepairService;
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
	
	public List<ClientsClient> getClientsClientList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<ClientsClient> repairServiceList = jdbcTemplate.query("select "+CLIENT_CLIENTS_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team="+id_team, params, new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		//List<ClientsClient> repairServiceList = jdbcTemplate.query("select "+CLIENT_CLIENTS_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		return repairServiceList;
	}
		
	public ClientsClient getClientsClientById(Integer ID_clients_client, Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("ID_clients_client", ID_clients_client);
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		params.addValue("id_team", id_team);
		
		ClientsClient clientsClient = jdbcTemplate.queryForObject("select "+CLIENT_CLIENTS_COLUMN_LIST+" from "+TABLE_NAME+" where ID_clients_client = :ID_clients_client AND id_team="+id_team, params , new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		//ClientsClient clientsClient = jdbcTemplate.queryForObject("select "+CLIENT_CLIENTS_COLUMN_LIST+" from "+TABLE_NAME+" where ID_clients_client = :ID_clients_client AND id_team= :id_team", params , new BeanPropertyRowMapper<ClientsClient>(ClientsClient.class));
		return clientsClient;
	}
	
	public ClientsClient createClientsClient(ClientsClient clientsClient, Integer team_key) {
		
		//clientsClient.setId_team(teamDAO.getTeamIdByKey(team_key));
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name,tax_id,id_team,risk_contract_percent) values (:name,:tax_id,"+teamDAO.getTeamIdByKey(team_key)+",:risk_contract_percent)",
				new BeanPropertySqlParameterSource(clientsClient), generatedKeyHolder);
		
		ClientsClient createdClientsClient = getClientsClientById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdClientsClient;

	}

	/*
	
		public RepairService createRepairService(RepairService repairService, Integer team_key) {
		
		repairService.setId_team(teamDAO.getTeamIdByKey(team_key));

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name, address, id_team, id_gear_type) values (:name, :address, :id_team, 2)",
				new BeanPropertySqlParameterSource(repairService), generatedKeyHolder);
		
		RepairService createdRepairService = getRepairServiceById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdRepairService;

	}
	
	*/
	public int updateClientsClient(ClientsClient clientsClient, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		//clientsClient.setId_team(id_team);
/*
		int updatedRowsCount = jdbcTemplate.update(
				 "UPDATE "+TABLE_NAME
				+" SET (name,tax_id,risk_contract_percent) = (:name,:tax_id,risk_contract_percent) "
				+" WHERE ID_clients_client = :ID_clients_client"
				+" AND ID_team = :id_team",
		new BeanPropertySqlParameterSource(clientsClient));
		*/
		int updatedRowsCount = jdbcTemplate.update(
				 "UPDATE "+TABLE_NAME
				+" SET (name,tax_id,risk_contract_percent) = (:name,:tax_id,risk_contract_percent) "
				+" WHERE ID_clients_client = :ID_clients_client",
		new BeanPropertySqlParameterSource(clientsClient));
		
		
		return updatedRowsCount;
	}

	/*
public int updateRepairService(RepairService repairService, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		repairService.setId_team(id_team);

		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (name,address) = (:name,:address) "
						+" WHERE ID_repair_service = :ID_repair_service"
						+" AND ID_team = :id_team",
				new BeanPropertySqlParameterSource(repairService));
		return updatedRowsCount;
	}
	
	*/
	
	public int deleteClientsClient(Integer ID_clients_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_clients_client", ID_clients_client);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_clients_client = :ID_clients_client", params);
		return deletedRows;
	}

}
