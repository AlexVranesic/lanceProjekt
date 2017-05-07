package si.triglav.hackathon.RepairService;

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
public class RepairServiceController {

	@Autowired
	private RepairServiceDAO repairServiceDAO;

	@RequestMapping( path="/repairservices/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<RepairService> getRepairServiceList(@PathVariable(name="team_key") Integer team_key){
		return repairServiceDAO.getRepairServiceList(team_key);
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/repairservices/{id}/{team_key}", method=RequestMethod.GET)
	public @ResponseBody RepairService getRepairServiceById(@PathVariable(name="id") Integer id, 
															@PathVariable(name="team_key") Integer team_key){
		return repairServiceDAO.getRepairServiceById(id, team_key);
	}
	
	@RequestMapping( path="/repairservices/{team_key}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRepairService(	@RequestBody RepairService repairService, 
													@PathVariable(name="team_key") Integer team_key){
		
		//optionally validate repairService
		
		RepairService createdRepairService = repairServiceDAO.createRepairService(repairService, team_key); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdRepairService.getId_repair_service()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/repairservices/{id_repair_service}/{team_key}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRepairService(	@PathVariable(name="id_repair_service") Integer id_repair_service,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody RepairService repairService){
		repairService.setId_repair_service(id_repair_service);
		
		int updatedRows = repairServiceDAO.updateRepairService(repairService, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	

	
	@RequestMapping( path="/repairservices/{id}/{team_key}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(	@PathVariable(name="id") Integer id,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = repairServiceDAO.deleteRepairService(id, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
		
	
	
	
	
}
