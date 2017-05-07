package si.triglav.hackathon.Client;

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

import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.RepairService.RepairServiceDAO;

@Controller
public class ClientController {

	@Autowired
	private ClientDAO clientDAO;
	
	@RequestMapping( path="/clients/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<Client> getClientList(@PathVariable(name="team_key") Integer team_key){
		return clientDAO.getClientList();
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/clients/{team_key}/{id_client}", method=RequestMethod.GET)
	public @ResponseBody Client getClientById(@PathVariable(name="id_client") Integer id_client, 
															@PathVariable(name="team_key") Integer team_key){
		return clientDAO.getClientById(id_client, team_key);
	}
	
	@RequestMapping( path="/clients/{team_key}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createClient(	@RequestBody Client client, 
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		
		Client createdClient = clientDAO.createClient(client, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdClient.getId_client()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/clients/{team_key}/{id_client}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateClient(	@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody Client client){
		client.setId_client(id_client);
		
		int updatedRows = clientDAO.updateClient(client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	

	
	@RequestMapping( path="/clients/{team_key}/{id_client}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteClient(	@PathVariable(name="id_client") Integer id,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = clientDAO.deleteClient(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
		
	
}
