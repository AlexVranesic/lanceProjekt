package si.triglav.hackathon.SickDaysClaim;

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

import si.triglav.hackathon.SickDaysClaim.SickDaysClaim;

@Controller
public class SickDaysClaimController {
	
	@Autowired
	private SickDaysClaimDAO sickDaysClaimDAO;
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims", method=RequestMethod.GET)
	public @ResponseBody List<SickDaysClaim> getSickDaysClaims(@PathVariable(name="id_client") Integer id_client,
																	@PathVariable(name="team_key") Integer team_key){
		return sickDaysClaimDAO.getSickDaysClaims(id_client, team_key);
	}
	
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_claim}", method=RequestMethod.GET)
	public @ResponseBody SickDaysClaim getSickDaysClaimtById(@PathVariable(name="id_claim") Integer id_claim,
															  @PathVariable(name="id_client") Integer id_client,
															  @PathVariable(name="team_key") Integer team_key){
		return sickDaysClaimDAO.getSickDaysClaimByID(id_client, id_claim,  team_key);
		
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSickDaysClaim(	@RequestBody SickDaysClaim sickDaysClaim, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client){
		
		//optionally validate repairService
		SickDaysClaim createdSickDaysClaim = sickDaysClaimDAO.createSickDaysClaim(id_client, sickDaysClaim, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdSickDaysClaim.getId_sick_day_claim()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_claim}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSickDaysClaimPolicy(	  @PathVariable(name="id_claim") Integer id_claim,
													  @PathVariable(name="id_client") Integer id_client,
													  @PathVariable(name="team_key") Integer team_key,
													  @RequestBody SickDaysClaim sickDaysClaim){
				
		int updatedRows = sickDaysClaimDAO.updateSickDaysClaim(sickDaysClaim, id_claim, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	

	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_claim}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService( @PathVariable(name="id_claim") Integer id_claim,
												  @PathVariable(name="id_client") Integer id_client,
												  @PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = sickDaysClaimDAO.deleteSickDaysClaim(id_claim, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
