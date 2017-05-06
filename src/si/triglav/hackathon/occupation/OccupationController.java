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

import si.triglav.hackathon.product.Product;
import si.triglav.hackathon.product.ProductDAO;

@Controller
public class OccupationController {

	@Autowired
	private OccupationDAO OccupationDAO;
	
	@RequestMapping( path="/occupations", method=RequestMethod.GET)
	public @ResponseBody List<Occupation> getOccupationList() throws Exception{
		return OccupationDAO.getOccupationList(); 
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/occupations/{id}", method=RequestMethod.GET)
	public @ResponseBody Occupation getOccupationById(@PathVariable(name="id") Integer id) throws Exception {
		return OccupationDAO.getOccupationById(id);
	}
	
	@RequestMapping( path="/occupations", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createOccupation(@RequestBody Occupation occupation) throws Exception{
		
		//optionally validate person
		Occupation createdOccupation = OccupationDAO.createOccupation(occupation); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdOccupation.getId()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/occupations/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteOccupation(@PathVariable(name="id") Integer id){
		
		int updatedRows = OccupationDAO.deleteOccupation(id);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
