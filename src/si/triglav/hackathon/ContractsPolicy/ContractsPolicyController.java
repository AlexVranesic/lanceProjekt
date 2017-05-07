package si.triglav.hackathon.ContractsPolicy;

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

import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.RepairService.RepairService;

@Controller
public class ContractsPolicyController {
	
	@Autowired
	private ContractsPolicyDAO contractsPolicyDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies", method=RequestMethod.GET)
	public @ResponseBody List<ContractsPolicy> getContractPoliciesList(	@PathVariable(name="team_key") Integer team_key, 
																	@PathVariable(name="id_client") Integer id_client){
		return contractsPolicyDAO.getContractsPolicies(id_client, team_key);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id}", method=RequestMethod.GET)
	public @ResponseBody ContractsPolicy getContractPoliciesById(	@PathVariable(name="id_client") Integer id_client,
																@PathVariable(name="id") Integer id, 
																@PathVariable(name="team_key") Integer team_key){
		return contractsPolicyDAO.getContractsPolicyById(id_client, id, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContractPolicy(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@RequestBody ContractsPolicy contractsPolicy){
		
		//optionally validate repairService
		
		ContractsPolicy createdContractsPolicy = contractsPolicyDAO.createContractPolicy(id_client, contractsPolicy, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdContractsPolicy.getID_policy_product()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRepairService(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id") Integer id_policy_product,
													@RequestBody ContractsPolicy contractsPolicy){
		
		contractsPolicy.setID_policy_product(id_policy_product);
		
		int updatedRows = contractsPolicyDAO.updateContractPolicy(id_client, contractsPolicy, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id") Integer id_policy_product){
		
		int updatedRows = contractsPolicyDAO.deleteContractPolicy(id_client, id_policy_product, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
}
