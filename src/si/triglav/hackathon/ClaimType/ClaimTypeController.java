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

import si.triglav.hackathon.ClientsClient.ClientsClient;

@Controller
public class ClaimTypeController {

	@Autowired
	private ClaimTypeDAO claimTypeDAO;
	
	@RequestMapping( path="/{team_key}/claimtypes", method=RequestMethod.GET)
	public @ResponseBody List<ClaimType> getClaimTypeList(@PathVariable(name="team_key") Integer team_key){
		return claimTypeDAO.getClaimTypeList(team_key);
	}
	
	//If using PathVariable, not all conversions are supported
		@RequestMapping( path="/{team_key}/claimtypes/{id}", method=RequestMethod.GET)
		public @ResponseBody ClaimType getClaimTypeById(@PathVariable(name="id")Integer id, 
														@PathVariable(name="team_key") Integer team_key) throws Exception{
		
			return claimTypeDAO.getClaimTypeById(id, team_key);
	}
		
	@RequestMapping( path="/{team_key}/claimtypes/", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createClaimType(	@RequestBody ClaimType calm_type,
												@PathVariable(name="team_key") Integer team_key) throws Exception{
		
		//optionally validate person
		ClaimType createdClaimType = claimTypeDAO.createClaimType(calm_type, team_key); // this will set the id on the person object

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdClaimType.getId_claim_type()).toUri();
	
		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();	
	} 
	
	@RequestMapping( path="/{team_key}/claimtypes/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateClientsClient(	@PathVariable(name="id") Integer id,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody ClaimType claim_type){
		claim_type.setId_claim_type(id);
		int updatedRows = claimTypeDAO.updateClaimType(claim_type, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	
}
