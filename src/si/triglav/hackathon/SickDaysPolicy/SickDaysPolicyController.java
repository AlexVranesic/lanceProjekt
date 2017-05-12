package si.triglav.hackathon.SickDaysPolicy;

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
public class SickDaysPolicyController {
	@Autowired
	private SickDaysPolicyDAO sickDaysPolicyDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy", method=RequestMethod.GET)
	public @ResponseBody SickDaysPolicy getContractPoliciesById(	@PathVariable(name="id_client") Integer id_client,
																	@PathVariable(name="team_key") Integer team_key){
		return sickDaysPolicyDAO.getSickDaysPolicy(id_client, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSickDays(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@RequestBody SickDaysPolicy sickDaysPolicy){
		  
		//optionally validate repairService
		sickDaysPolicyDAO.createSickDaysPolicy(id_client, sickDaysPolicy, team_key); 
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
		
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSickDays(	@PathVariable(name="team_key") Integer team_key, 
												@PathVariable(name="id_client") Integer id_client,
												@RequestBody SickDaysPolicy sickDaysPolicy){
				
		int updatedRows = sickDaysPolicyDAO.updateSickDaysPolicy(sickDaysPolicy, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}



	@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService( @PathVariable(name="id_client") Integer id_client,
												  @PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = sickDaysPolicyDAO.deleteSickDaysPolicy(id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
