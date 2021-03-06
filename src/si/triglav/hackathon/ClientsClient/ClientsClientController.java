package si.triglav.hackathon.ClientsClient;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
public class ClientsClientController {

	@Autowired
	private ClientsClientDAO clientsClientDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/clientsclients", method=RequestMethod.GET)
	public @ResponseBody List<ClientsClient> getClientsClientList(@PathVariable(name="id_client") Integer id_client,
																  @PathVariable(name="team_key") Integer team_key){
		return clientsClientDAO.getClientsClientList(id_client, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clientsclients", method=RequestMethod.GET)
	public @ResponseBody List<ClientsClient> getClientsClientList(@PathVariable(name="team_key") Integer team_key){
		return clientsClientDAO.getAllClients(team_key);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/clientsclients/{id}", method=RequestMethod.GET)
	public @ResponseBody ClientsClient getClientsClientById(@PathVariable(name="id") Integer id, 
															@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key){
		return clientsClientDAO.getClientsClientById(id, team_key);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id_contract}/clientsclient", method=RequestMethod.GET)
	public @ResponseBody ClientsClient getClientsClientByIdViaContract(@PathVariable(name="id_contract") Integer id_contract,
															@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key){
		
		return clientsClientDAO.getClientsClientByIdViaContract(id_contract, id_client, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/clientsclient", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createClientsClient(	@RequestBody ClientsClient clientsClient, 
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		ClientsClient createdClientsClient = clientsClientDAO.createClientsClient(clientsClient, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdClientsClient.getID_clients_client()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/clientsclient/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateClientsClient(	@PathVariable(name="id") Integer id,
													@RequestBody ClientsClient clientsClient, 
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
		clientsClient.setID_clients_client(id);
		int updatedRows = clientsClientDAO.updateClientsClient(clientsClient, team_key, id);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/clientsclient/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteClientsClient(	@PathVariable(name="id") Integer id,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = clientsClientDAO.deleteClientsClient(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
