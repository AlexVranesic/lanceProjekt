package si.triglav.hackathon.ContractsPolicy;

import java.net.URI;

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
public class ContractsPolicyController {
	
	@Autowired
	private ContractsPolicyDAO contractsPolicyDAO;
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicy", method=RequestMethod.GET)
	public @ResponseBody ContractsPolicy getContractPoliciesById(	@PathVariable(name="id_client") Integer id_client,
																	@PathVariable(name="team_key") Integer team_key){
		return contractsPolicyDAO.getContractsPolicy(id_client, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicy", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContractPolicy(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@RequestBody ContractsPolicy contractsPolicy){
		  
		
		boolean contractsPolicyExists = contractsPolicyDAO.contractsPolicyExists(id_client, team_key); // this will set the id on the repairService object

		//ResponseEntity.badRequest();
		
		//optionally validate repairService
		ContractsPolicy createdContractsPolicy = contractsPolicyDAO.createContractPolicy(id_client, contractsPolicy, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
		
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicy", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRepairService(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@RequestBody ContractsPolicy contractsPolicy){
				
		int updatedRows = contractsPolicyDAO.updateContractPolicy(id_client, contractsPolicy, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client){
		
		int updatedRows = contractsPolicyDAO.deleteContractPolicy(id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
}
