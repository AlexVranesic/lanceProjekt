package si.triglav.hackathon.GearPolicy;

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

import si.triglav.hackathon.GearPolicy.GearPolicy;
import si.triglav.hackathon.GearPolicy.GearPolicyDAO;

@Controller
public class GearPolicyController {
	
	@Autowired
	private GearPolicyDAO gearPolicyDAO;
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy", method=RequestMethod.GET)
	public @ResponseBody GearPolicy getGearPoliciesById(	@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key){
		return gearPolicyDAO.getGearPolicy(id_client, team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGearPolicy(	@PathVariable(name="team_key") Integer team_key, 
													@PathVariable(name="id_client") Integer id_client,
													@RequestBody GearPolicy gearPolicy){
		
		
		//optionally validate repairService
		GearPolicy createdGearPolicy = gearPolicyDAO.createGearPolicy(id_client, gearPolicy, team_key);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
		
	}

	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateGearPolicy(	@PathVariable(name="team_key") Integer team_key, 
												@PathVariable(name="id_client") Integer id_client,
												@RequestBody GearPolicy gearPolicy){
				
	int updatedRows = gearPolicyDAO.updateGearPolicy(gearPolicy, id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	

	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteGearPolicy( @PathVariable(name="id_client") Integer id_client,
												 @PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = gearPolicyDAO.deleteGearPolicy(id_client, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
}	
