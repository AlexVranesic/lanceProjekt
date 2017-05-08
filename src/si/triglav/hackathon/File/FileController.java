package si.triglav.hackathon.File;

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

import si.triglav.hackathon.GearType.GearType;

@Controller
public class FileController {

	@Autowired
	private FileDAO fileDAO;
	
	//list all gear_types
	@RequestMapping( path="/files/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<File> getFileList(@PathVariable(name="team_key") Integer team_key){
		return fileDAO.getFileList(team_key);
	}

	//Find specific GearType with ID
	@RequestMapping( path="/files/{team_key}/{id_file}", method=RequestMethod.GET)
	public @ResponseBody File getFileById(@PathVariable(name="id_file") Integer id_file, @PathVariable(name="team_key") Integer team_key){
		return fileDAO.getFileById(id_file,team_key);
	}
	
	//create new GEAR_TYPE (npr. body: "gear_type": "computer")
	@RequestMapping( path="/files/{team_key}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createFile(@RequestBody File file, @PathVariable(name="team_key") Integer team_key){

		File createdFile = fileDAO.createFile(file,team_key); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id_file}")
				.buildAndExpand(createdFile.getId_file()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	//updateTeam(Team team)
	@RequestMapping( path="/files/{team_key}/{id_file}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFile(@PathVariable(name="team_key") Integer team_key, @PathVariable(name="id_file") Integer id_file, @RequestBody File file){
		file.setId_file(id_file);
		
		int updatedRows = fileDAO.updateFile(file, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/files/{team_key}/{id_file}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteFile(@PathVariable(name="id_file") Integer id_file, 
											@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = fileDAO.deleteFile(id_file, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
