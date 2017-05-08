package si.triglav.hackathon.MonthlyPayment;

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

//import si.triglav.hackathon.ContractsPolicy.ContractsPolicy;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;


@Controller
public class MonthlyPaymentController {

	@Autowired
	private MonthlyPaymentDAO monthlyPaymentDAO;
	
	@RequestMapping( path="/{team_key}/monthlypayments", method=RequestMethod.GET)
	public @ResponseBody List<MonthlyPayment> getRepairServiceList(@PathVariable(name="team_key") Integer team_key){
		return monthlyPaymentDAO.getMonthlyPaymentList(team_key);
	}
		
	@RequestMapping( path="/{team_key}/clients/{id_client}/monthlypayments/{id_payment}", method=RequestMethod.GET)
	public @ResponseBody MonthlyPayment getMonthlyPaymentById(@PathVariable(name="id_payment") Integer id_payment,
															  @PathVariable(name="id_client") Integer id_client,
															  @PathVariable(name="team_key") Integer team_key){
		return monthlyPaymentDAO.getMonthlyPaymentById(id_payment, id_client, team_key);
		
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/monthlypayments", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createMonthlyPayment(	@RequestBody MonthlyPayment monthlyPayment, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client){
		
		//optionally validate repairService
		MonthlyPayment createdMonthlyPayment = monthlyPaymentDAO.createMonthlyPayment(monthlyPayment, team_key, id_client); // this will set the id on the repairService object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdMonthlyPayment.getId_payment()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/monthlypayments/{id_payment}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateMonthlyPayment(	@PathVariable(name="id_payment") Integer id_payment,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key,
													@RequestBody MonthlyPayment monthlyPayment){
	
		monthlyPayment.setId_payment(id_payment);
			
		int updatedRows = monthlyPaymentDAO.updateMonthlyPayment(monthlyPayment, team_key, id_client);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	//(Integer id_client, Integer id_payment, Integer team_key) {
	@RequestMapping( path="/{team_key}/clients/{id_client}/monthlypayments/{id_payment}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteMonthlyPayment(	@PathVariable(name="id_payment") Integer id_payment,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = monthlyPaymentDAO.deleteMonthlyPayment(id_client, id_payment, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
