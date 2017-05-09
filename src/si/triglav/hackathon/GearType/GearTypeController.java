package si.triglav.hackathon.GearType;

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
public class GearTypeController {

	@Autowired
	private GearTypeDAO gearTypeDAO;
	
	//list all gear_types
	@RequestMapping( path="/{team_key}/geartypes", method=RequestMethod.GET)
	public @ResponseBody List<GearType> getGearTypeList(@PathVariable(name="team_key") Integer team_key){
		return gearTypeDAO.getGearTypeList(team_key);
	}

	//Find specific GearType with ID
	@RequestMapping( path="/{team_key}/geartypes/{id_gear_type}", method=RequestMethod.GET)
	public @ResponseBody GearType getTeamNameById(@PathVariable(name="id_gear_type") Integer id_gear_type, @PathVariable(name="team_key") Integer team_key){
		return gearTypeDAO.getGearTypeById(id_gear_type,team_key);
	}
	
	
	//create new GEAR_TYPE (npr. body: "gear_type": "computer")
	@RequestMapping( path="/{team_key}/geartypes", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGearType(@RequestBody GearType gearType, @PathVariable(name="team_key") Integer team_key){

		GearType createdGearType = gearTypeDAO.createGearType(gearType,team_key); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id_gear_type}")
				.buildAndExpand(createdGearType.getId_gear_type()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	
	//updateTeam(Team team)
	@RequestMapping( path="/{team_key}/geartypes/{id_gear_type}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTeam(@PathVariable(name="team_key") Integer team_key, @PathVariable(name="id_gear_type") Integer id_gear_type, @RequestBody GearType gearType){
		gearType.setId_gear_type(id_gear_type);
		
		int updatedRows = gearTypeDAO.updateGearType(gearType, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	@RequestMapping( path="/{team_key}/geartypes/{id_gear_type}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteGearType(@PathVariable(name="id_gear_type") Integer id_gear_type, 
											@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = gearTypeDAO.deleteGearType(id_gear_type, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
