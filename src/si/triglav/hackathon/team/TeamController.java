package si.triglav.hackathon.team;

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

import si.triglav.hackathon.person.Person;

@Controller
public class TeamController {

	@Autowired
	private TeamDAO teamDAO;

	@RequestMapping( path="/teams", method=RequestMethod.GET)
	public @ResponseBody List<Team> getTeamList(){
		return teamDAO.getTeamList();
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/teams/{id_team}", method=RequestMethod.GET)
	public @ResponseBody Team getTeamNameById(@PathVariable(name="id_team") Integer id_team){
		return teamDAO.getTeamNameById(id_team);
	}
	
	@RequestMapping( path="/teams", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTeam(@RequestBody Team team){
	
		
		Team createdTeam = teamDAO.createTeam(team); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTeam.getId_team()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}

	@RequestMapping( path="/teams/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable(name="id_team") Integer id_team, @RequestBody Team team){
		team.setId_team(id_team);
		int updatedRows = teamDAO.updateTeam(team);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/teams/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteTeam(@PathVariable(name="id_team") Integer id_team){
		
		int updatedRows = teamDAO.deleteTeam(id_team);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

}
