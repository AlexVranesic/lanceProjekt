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

	@RequestMapping( path="/repairservices", method=RequestMethod.GET)
	public @ResponseBody List<RepairService> getRepairServiceList(){
		return repairServiceDAO.getRepairServiceList();
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/repairservices/{id}", method=RequestMethod.GET)
	public @ResponseBody RepairService getRepairServiceById(@PathVariable(name="id") Integer id){
		return repairServiceDAO.getRepairServiceById(id);
	}
	
	@RequestMapping( path="/repairservices", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRepairService(@RequestBody RepairService repairService){
		
		/*if(repairService.getCreated_by() == null){
			//possible extension: use api key header and map from key to user  
			repairService.setCreated_by("anonymous");	
		}*/
		
		//optionally validate repairService
		RepairService createdRepairService = repairServiceDAO.createRepairService(repairService); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdRepairService.getID_repair_service()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/repairservices/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRepairService(@PathVariable(name="id") Integer id, @RequestBody RepairService repairService){
		repairService.setID_repair_service(id);
		int updatedRows = repairServiceDAO.updateRepairService(repairService);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/repairservices/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepairService(@PathVariable(name="id") Integer id){
		
		int updatedRows = repairServiceDAO.deleteRepairService(id);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
		
	
	
	
	
}
