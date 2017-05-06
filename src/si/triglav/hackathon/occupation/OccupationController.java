package si.triglav.hackathon.occupation;

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
import si.triglav.hackathon.product.Product;
import si.triglav.hackathon.product.ProductDAO;

@Controller
public class OccupationController {

	@Autowired
	private OccupationDAO occupationDAO;
	
	@RequestMapping( path="/occupations/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<Occupation> getRepairServiceList(@PathVariable(name="team_key") Integer team_key){
		return occupationDAO.getOccupationList(team_key);
	}
		
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/occupations/{team_key}/{id}", method=RequestMethod.GET)
	public @ResponseBody Occupation getOccupationById(@PathVariable(name="id") Integer id, 
															@PathVariable(name="team_key") Integer team_key){
		return occupationDAO.getOccupationById(id, team_key);
		
	}
	
	@RequestMapping( path="/occupations/{team_key}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createOccupation(	@RequestBody Occupation occupation, 
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		Occupation createdOccupation = occupationDAO.createOccupation(occupation, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdOccupation.getId_occupation()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/occupations/{team_key}/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateOccupation(	@PathVariable(name="id") Integer id,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody Occupation occupation){
		//occupation.setID_repair_service(id);
		
		occupation.setId_occupation(id);
		
		int updatedRows = occupationDAO.updateOccupation(occupation, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	@RequestMapping( path="/occupations/{team_key}/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(	@PathVariable(name="id") Integer id,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = occupationDAO.deleteOccupation(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}	
}
