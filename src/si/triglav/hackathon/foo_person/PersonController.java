package si.triglav.hackathon.person;

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
public class PersonController {

	@Autowired
	private PersonDAO personDAO;

	@RequestMapping( path="/persons", method=RequestMethod.GET)
	public @ResponseBody List<Person> getPersonList(){
		return personDAO.getPersonList();
	}
	
	//If using PathVariable, not all conversions are supported
	@RequestMapping( path="/persons/{id}", method=RequestMethod.GET)
	public @ResponseBody Person getPersonById(@PathVariable(name="id") Integer id){
		return personDAO.getPersonById(id);
	}
	
	@RequestMapping( path="/persons", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPerson(@RequestBody Person person){
		
		if(person.getCreated_by() == null){
			//possible extension: use api key header and map from key to user  
			person.setCreated_by("anonymous");	
		}
		
		//optionally validate person
		Person createdPerson = personDAO.createPerson(person); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdPerson.getId()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/persons/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePerson(@PathVariable(name="id") Integer id, @RequestBody Person person){
		person.setId(id);
		int updatedRows = personDAO.updatePerson(person);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/persons/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePerson(@PathVariable(name="id") Integer id){
		
		int updatedRows = personDAO.deletePerson(id);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
		
	
	
	
	
}
