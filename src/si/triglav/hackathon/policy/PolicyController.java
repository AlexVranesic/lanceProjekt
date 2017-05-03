package si.triglav.hackathon.policy;

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
public class PolicyController {

	@Autowired
	private PolicyDAO policyDAO;

	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/polices/{id}", method=RequestMethod.GET)
	public @ResponseBody Policy getPolicyById(@PathVariable(name="id") Integer id) throws Exception {
		return policyDAO.getPolicyById(id);
	}
	
	@RequestMapping( path="/polices", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPolicy(@RequestBody Policy policy) throws Exception{
		
		//optionally validate person
		Policy createdPolicy = policyDAO.createPolicy(policy); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdPolicy.getPolicyId()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/polices/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePerson(@PathVariable(name="id") Integer id){
		
		int updatedRows = policyDAO.deletePerson(id);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
