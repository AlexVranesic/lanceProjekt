package si.triglav.hackathon.LiabilityClaim;

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
public class LiabilityClaimController {
	
	@Autowired
	private LiabilityClaimDAO liabilityClaimDAO;
	
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims", method=RequestMethod.GET)
	public @ResponseBody List<LiabilityClaim> getLiabilityClaims(@PathVariable(name="id_client") Integer id_client,
																	@PathVariable(name="team_key") Integer team_key){
		return liabilityClaimDAO.getLiabilityClaims(id_client, team_key);
	}
	
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_claim}", method=RequestMethod.GET)
	public @ResponseBody LiabilityClaim getLiabilityClaimtById(@PathVariable(name="id_claim") Integer id_claim,
															  @PathVariable(name="id_client") Integer id_client,
															  @PathVariable(name="team_key") Integer team_key){
		return liabilityClaimDAO.getLiabilityClaimByID(id_client, id_claim,  team_key);
		
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createLiabilityClaim(	@RequestBody LiabilityClaim liabilityClaim, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client){
		
		//optionally validate repairService
		LiabilityClaim createdLiabilityClaim = liabilityClaimDAO.createLiabilityClaim(id_client, liabilityClaim, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdLiabilityClaim.getId_liability_claim()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_claim}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLiabilityPolicy(	  @PathVariable(name="id_claim") Integer id_claim,
													  @PathVariable(name="id_client") Integer id_client,
													  @PathVariable(name="team_key") Integer team_key,
													  @RequestBody LiabilityClaim liabilityClaim){
				
		int updatedRows = liabilityClaimDAO.updateLiabilityClaim(liabilityClaim, id_claim, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_claim}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService( @PathVariable(name="id_claim") Integer id_claim,
												  @PathVariable(name="id_client") Integer id_client,
												  @PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = liabilityClaimDAO.deleteLiabilityClaim(id_claim, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
