package si.triglav.hackathon.Contract;

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
public class ContractController {
	

	@Autowired
	private ContractDAO contractDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts", method=RequestMethod.GET)
	public @ResponseBody List<Contract> getRepairServiceList(	@PathVariable(name="id_client") Integer id_client,
																@PathVariable(name="team_key") Integer team_key){
		
		return contractDAO.getContractList(team_key,id_client);
	}
	
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id}", method=RequestMethod.GET)
	public @ResponseBody Contract getClientsClientById(@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="id") Integer id, 
															@PathVariable(name="team_key") Integer team_key){
		return contractDAO.getContractById(id, team_key, id_client);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContract(	@RequestBody Contract contract, 
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_client") Integer id_client){
		
		//optionally validate repairService
		
		Contract createdContract = contractDAO.createContract(team_key,id_client, contract); // this will set the id on the createdContract object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdContract.getId_contract()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	//updateContract(Team team)
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContract(@PathVariable(name="id_client") Integer id_client,
											@PathVariable(name="id") Integer id, 
											@PathVariable(name="team_key") Integer team_key,
											@RequestBody Contract contract){
		
		int updatedRows = contractDAO.updateContract(team_key, id_client, id, contract);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id}/contractclaim", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContractClaim(@PathVariable(name="id_client") Integer id_client,
											@PathVariable(name="id") Integer id_contract, 
											@PathVariable(name="team_key") Integer team_key,
											@RequestBody Contract contract){
		
		int updatedRows = contractDAO.updateContractClaim(team_key, id_client, id_contract, contract);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteClientsClient(	@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id") Integer id, 
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = contractDAO.deleteContract(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

}
