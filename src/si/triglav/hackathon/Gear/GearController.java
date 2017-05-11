package si.triglav.hackathon.Gear;

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

import si.triglav.hackathon.ContractsPolicy.ContractsPolicy;

@Controller
public class GearController {

	@Autowired
	private GearDAO gearDAO;

	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear", method=RequestMethod.GET)
	public @ResponseBody List<Gear> getGearList(@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_client") Integer id_client){
		return gearDAO.getGearList(team_key,id_client);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id}", method=RequestMethod.GET)
	public @ResponseBody Gear getGearById(@PathVariable(name="team_key") Integer team_key, 
										  @PathVariable(name="id_client") Integer id_client,
										  @PathVariable(name="id") Integer id){
		return gearDAO.getGearById(team_key, id_client, id);
	}

	@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGear(  @PathVariable(name="team_key") Integer team_key,
										  @PathVariable(name="id_client") Integer id_client,
										  @RequestBody Gear gear){
											
		Gear createdGear = gearDAO.createGear(gear, team_key, id_client); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdGear.getId_gear()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	///clients/{id_client}/gearpolicy/gear/{id}
	@RequestMapping( path="{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateGear(	@PathVariable(name="id_gear") Integer id_gear,
											@PathVariable(name="id_client") Integer id_client,
											@PathVariable(name="team_key") Integer team_key,
											@RequestBody Gear gear){
		gear.setId_gear(id_gear);
		
		int updatedRows = gearDAO.updateGear(gear, team_key, id_client, id_gear);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	//Gear gear, Integer team_key, Integer id_client, Integer id_gear
	@RequestMapping( path="{team_key}/clients/{id_client}/gearpolicy/gear/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteGear(	@PathVariable(name="id") Integer id,
											@PathVariable(name="id_client") Integer id_client,
											@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = gearDAO.deleteGear(id,id_client,team_key);
		
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
