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
	@RequestMapping( path="/geartypes/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<GearType> getGearTypeList(@PathVariable(name="team_key") Integer team_key){
		return gearTypeDAO.getGearTypeList(team_key);
	}
	
	//Find specific GearType with ID
	@RequestMapping( path="/geartypes/{id_gear_type}/{team_key}", method=RequestMethod.GET)
	public @ResponseBody GearType getTeamNameById(@PathVariable(name="id_gear_type") Integer id_gear_type, @PathVariable(name="team_key") Integer team_key){
		return gearTypeDAO.getGearTypeNameById(id_gear_type,team_key);
	}
/*	
	//Find specific GearType with ID
		@RequestMapping( path="/teams/key/{team_key}", method=RequestMethod.GET)
		public @ResponseBody Integer getTeamIdByKey(@PathVariable(name="team_key") Integer team_key){
			return teamDAO.getTeamIdByKey(team_key);
	}
*/		
	//create new TEAM (npr. body: "team_name": "test_team", "team_key": 12345678)
	@RequestMapping( path="/geartypes", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGearType(@RequestBody GearType gearType){

		GearType createdGearType = gearTypeDAO.createGearType(gearType); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdGearType.getId_team()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}

	//updateTeam(Team team)
	/*@RequestMapping( path="/teams/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTeam(@PathVariable(name="id_team") Integer id_team, @RequestBody Team team){
		team.setId_team(id_team);
		int updatedRows = teamDAO.updateTeam(team);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}*/
	
	@RequestMapping( path="/geartypes/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteGearType(@PathVariable(name="id_gear_type") Integer id_gear_type){
		
		int updatedRows = gearTypeDAO.deleteGearType(id_gear_type);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
