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

import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.RepairService.RepairServiceDAO;

@Controller
public class LiabilityClaimController {

	@Autowired
	private LiabilityClaimDAO liabilityClaimDAO;

	@RequestMapping( path="/liabilityclaims/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<LiabilityClaim> getLiabilityClaimList(@PathVariable(name="team_key") Integer team_key){
		return liabilityClaimDAO.getLiabilityClaimList(team_key);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/liabilityclaims/{team_key}/{id}", method=RequestMethod.GET)
	public @ResponseBody LiabilityClaim getLiabilityClaimById(@PathVariable(name="id") Integer id, 
															@PathVariable(name="team_key") Integer team_key){
		return liabilityClaimDAO.getLiabilityClaimById(id, team_key);
	}
	
	@RequestMapping( path="/liabilityclaims/{team_key}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createLiabilityClaim(	@RequestBody LiabilityClaim liabilityClaim, 
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		
		LiabilityClaim createdLiabilityClaim = liabilityClaimDAO.createLiabilityClaim(liabilityClaim, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdLiabilityClaim.getId_liability_claim()).toUri();

		
		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/liabilityclaims/{team_key}/{id_liability_claim}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLiabilityClaim(	@PathVariable(name="id_liability_claim") Integer id_liability_claim,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody LiabilityClaim liabilityClaim){	
		liabilityClaim.setId_liability_claim(id_liability_claim);
		
		int updatedRows = liabilityClaimDAO.updateLiabilityClaim(liabilityClaim, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}

	@RequestMapping( path="/liabilityclaims/{team_key}/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(	@PathVariable(name="id") Integer id,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = liabilityClaimDAO.deleteLiabilityClaim(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}	
}
