package si.triglav.hackathon.ClaimType;

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
public class ClaimTypeController {

	@Autowired
	private ClaimTypeDAO claimTypeDAO;


	
	@RequestMapping( path="/claimtypes", method=RequestMethod.GET)
	public @ResponseBody List<ClaimType> getClaimTypeList(){
		return claimTypeDAO.getClaimTypeList();
	}
	
	@RequestMapping( path="/claimtypes", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createClaimType(@RequestBody ClaimType calm_type) throws Exception{
		
		//optionally validate person
		ClaimType createdClaimType = claimTypeDAO.createClaimType(calm_type); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdClaimType.getId_claim_type()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	} 
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/claimtypes/{id}", method=RequestMethod.GET)
	public @ResponseBody ClaimType getClaimTypeById(@PathVariable(name="id") Integer id) throws Exception {
		return claimTypeDAO.getClaimTypeById(id);
	}
	
	
}
