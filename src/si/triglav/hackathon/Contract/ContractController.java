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
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id_policy_product}/contracts", method=RequestMethod.GET)
	public @ResponseBody List<Contract> getRepairServiceList(	@PathVariable(name="id_client") Integer id_client,
																@PathVariable(name="id_policy_product") Integer id_policy_product, 
																@PathVariable(name="team_key") Integer team_key){
		return contractDAO.getContractList(team_key,id_client,id_policy_product);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractpolicies/{id_policy_product}/contracts", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContract(	@RequestBody Contract contract, 
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="id_policy_product") Integer id_policy_product){
		
		//optionally validate repairService
		
		Contract createdContract = contractDAO.createContract(team_key,id_client,id_policy_product, contract); // this will set the id on the createdContract object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdContract.getId_contract()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}

}
