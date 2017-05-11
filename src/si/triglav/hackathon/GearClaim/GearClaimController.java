package si.triglav.hackathon.GearClaim;

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

import si.triglav.hackathon.ClaimType.ClaimType;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.RepairService.RepairServiceDAO;

@Controller
public class GearClaimController {

	@Autowired
	private GearClaimDAO gearClaimDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims", method=RequestMethod.GET)
	public @ResponseBody List<GearClaim> getGearClaimList(@PathVariable(name="team_key") Integer team_key,
														  @PathVariable(name="id_client") Integer id_client,
														  @PathVariable(name="id_gear") Integer id_gear){
														 // @RequestBody GearClaim getGearClaimListDAO){									  
		return getGearClaimList(team_key, id_client, id_gear);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id}", method=RequestMethod.GET)
	public @ResponseBody GearClaim getGearClaimById(@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id_gear") Integer id_gear,
													@PathVariable(name="id") Integer id){
		return getGearClaimById(team_key,id_client,id_gear,id);
	}
	/*
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createLiabilityClaim(	@RequestBody GearClaim gearClaim, 
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		
		GearClaim createdGearClaim = gearClaimDAO.createGearClaim(gearClaim, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdGearClaim.getId_gear_claim()).toUri();

		
		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	*/
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateGearClaim(	@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="id_gear") Integer id_gear,
												@PathVariable(name="id") Integer id,
												@RequestBody GearClaim gearClaim){	
		
		gearClaim.setId_gear_claim(id);
		
		int updatedRows = gearClaimDAO.updateGearClaim(gearClaim,team_key,id_client,id_gear);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
/*
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteGearClaim(	@PathVariable(name="id") Integer id,
												@PathVariable(name="id_gear") Integer id_gear,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="team_key") Integer team_key){
	
		int updatedRows = gearClaimDAO.deleteLiabilityClaim(id, id_gear, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}	
	
	*/
}
