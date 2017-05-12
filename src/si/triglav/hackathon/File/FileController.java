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

@Controller
public class FileController {

	@Autowired
	private FileDAO fileDAO;
	
	//list all gear_types
	@RequestMapping( path="/{team_key}/files", method=RequestMethod.GET)
	public @ResponseBody List<File> getFileList(@PathVariable(name="team_key") Integer team_key){
		return fileDAO.getFileList(team_key);
	}

	//Find specific GearType with ID
	@RequestMapping( path="/{team_key}/files/{id_file}", method=RequestMethod.GET)
	public @ResponseBody File getFileById(@PathVariable(name="id_file") Integer id_file, @PathVariable(name="team_key") Integer team_key){
		return fileDAO.getFileById(id_file,team_key);
	}
	
	
	
	
	
	
	//---------------------------------- FILES & CONTRACTS---------------------------------------------
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id_contract}/files", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createFile(@RequestBody File file, 
										@PathVariable(name="team_key") Integer team_key,
										@PathVariable(name="id_client") Integer id_client,
										@PathVariable(name="id_contract") Integer id_contract){

		File createdFile = fileDAO.createFile(file,team_key, id_client, "ID_contract", id_contract); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id_file}")
				.buildAndExpand(createdFile.getId_file()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	

	//Find specific GearType with ID
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id_contract}/files/{id_file}", method=RequestMethod.GET)
	public @ResponseBody File getContractFileById(	@PathVariable(name="id_file") Integer id_file,
													@PathVariable(name="id_contract") Integer id_contract,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
		return fileDAO.getFileById(id_file,team_key);
	}
	
	//Find specific GearType with ID
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id_contract}/files/", method=RequestMethod.GET)
	public @ResponseBody List<File> getContractFiles(	@PathVariable(name="id_contract") Integer id_contract,
														@PathVariable(name="id_client") Integer id_client,
														@PathVariable(name="team_key") Integer team_key){
		
		return fileDAO.getFilesByIdOfForeignKey("ID_contract",id_contract,team_key);
	}
	
	@RequestMapping( path="/{team_key}/clients/{id_client}/contractspolicy/contracts/{id_contract}/files/{id_file}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteContractFile(@PathVariable(name="id_file") Integer id_file,
										@PathVariable(name="id_contract") Integer id_contract,
										@PathVariable(name="id_client") Integer id_client,
										@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = fileDAO.deleteFile(id_file, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
	//---------------------------------- FILES & GEAR ---------------------------------------------
	
		@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/files", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> createGearFile(@RequestBody File file, 
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="id_gear") Integer id_gear){

			File createdFile = fileDAO.createFile(file,team_key, id_client, "ID_gear", id_gear); // this will set the id on the person object
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id_file}")
					.buildAndExpand(createdFile.getId_file()).toUri();

			//by rest conventions we need to repond with the URI for newly created resource 
			return ResponseEntity.created(location).build();
				
		}
		

		//Find specific GearType with ID
		@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/files/{id_file}", method=RequestMethod.GET)
		public @ResponseBody File getGearFileById(	@PathVariable(name="id_file") Integer id_file,
													@PathVariable(name="id_gear") Integer id_gear,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="team_key") Integer team_key){
			return fileDAO.getFileById(id_file,team_key);
		}
		
		//Find specific GearType with ID
		@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/files", method=RequestMethod.GET)
		public @ResponseBody List<File> getGearFiles(	@PathVariable(name="id_gear") Integer id_gear,
														@PathVariable(name="id_client") Integer id_client,
														@PathVariable(name="team_key") Integer team_key){
			
			return fileDAO.getFilesByIdOfForeignKey("ID_gear",id_gear,team_key);
		}
		
		@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/files/{id_file}", method=RequestMethod.DELETE)
		public ResponseEntity<?> deleteGearFile(@PathVariable(name="id_file") Integer id_file,
											@PathVariable(name="id_contract") Integer id_contract,
											@PathVariable(name="id_client") Integer id_client,
											@PathVariable(name="team_key") Integer team_key){
			
			int updatedRows = fileDAO.deleteFile(id_file, team_key);
			
			if(updatedRows == 0 ){
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.noContent().build();
		}
		
		//---------------------------------- FILES & GEARCLAIM ---------------------------------------------
		
			@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id_gear_claim}/files", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<?> createGearClaimFile(@RequestBody File file, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id_gear") Integer id_gear,
													@PathVariable(name="id_gear_claim") Integer id_gear_claim){

				File createdFile = fileDAO.createFile(file,team_key, id_client, "ID_gear_claim", id_gear_claim); // this will set the id on the person object
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id_file}")
						.buildAndExpand(createdFile.getId_file()).toUri();

				//by rest conventions we need to repond with the URI for newly created resource 
				return ResponseEntity.created(location).build();
					
			}
			

			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id_gear_claim}/files/{id_file}", method=RequestMethod.GET)
			public @ResponseBody File getGearClaimFileById(	@PathVariable(name="id_file") Integer id_file,
														@PathVariable(name="id_gear") Integer id_gear,
														@PathVariable(name="id_client") Integer id_client,
														@PathVariable(name="team_key") Integer team_key,
														@PathVariable(name="id_gear_claim") Integer id_gear_claim){
				return fileDAO.getFileById(id_file,team_key);
			}
			
			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id_gear_claim}/files", method=RequestMethod.GET)
			public @ResponseBody List<File> getGearClaimFiles(	@PathVariable(name="id_gear") Integer id_gear,
															@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key,
															@PathVariable(name="id_gear_claim") Integer id_gear_claim){
				
				return fileDAO.getFilesByIdOfForeignKey("ID_gear_claim",id_gear_claim,team_key);
			}
			
			@RequestMapping( path="/{team_key}/clients/{id_client}/gearpolicy/gear/{id_gear}/gearclaims/{id_gear_claim}/files/{id_file}", method=RequestMethod.DELETE)
			public ResponseEntity<?> deleteGearClaimFile(@PathVariable(name="id_file") Integer id_file,
												@PathVariable(name="id_gear") Integer id_gear,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_gear_claim") Integer id_gear_claim){
				
				int updatedRows = fileDAO.deleteFile(id_file, team_key);
				
				if(updatedRows == 0 ){
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.noContent().build();
			}	
	
			//---------------------------------- FILES & LIABILITYCLAIM ---------------------------------------------
			
			@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_liability_claim}/files", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<?> createLiabilityClaimFile(@RequestBody File file, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id_liability_claim") Integer id_liability_claim){

				File createdFile = fileDAO.createFile(file,team_key, id_client, "ID_liability_claim", id_liability_claim); // this will set the id on the person object
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id_file}")
						.buildAndExpand(createdFile.getId_file()).toUri();

				//by rest conventions we need to repond with the URI for newly created resource 
				return ResponseEntity.created(location).build();
					
			}
			

			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_liability_claim}/files/{id_file}", method=RequestMethod.GET)
			public @ResponseBody File getLiabilityClaimFileById(	@PathVariable(name="id_file") Integer id_file,
														@PathVariable(name="id_client") Integer id_client,
														@PathVariable(name="team_key") Integer team_key,
														@PathVariable(name="id_liability_claim") Integer id_liability_claim){
				return fileDAO.getFileById(id_file,team_key);
			}
			
			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_liability_claim}/files", method=RequestMethod.GET)
			public @ResponseBody List<File> getLiabilityClaimFiles(@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key,
															@PathVariable(name="id_liability_claim") Integer id_liability_claim){
				
				return fileDAO.getFilesByIdOfForeignKey("ID_liability_claim",id_liability_claim,team_key);
			}
			
			@RequestMapping( path="/{team_key}/clients/{id_client}/liabilitypolicy/liabilityclaims/{id_liability_claim}/files/{id_file}", method=RequestMethod.DELETE)
			public ResponseEntity<?> deleteLiabilityClaimFile(@PathVariable(name="id_file") Integer id_file,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_liability_claim") Integer id_liability_claim){
				
				int updatedRows = fileDAO.deleteFile(id_file, team_key);
				
				if(updatedRows == 0 ){
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.noContent().build();
			}	
			
//---------------------------------- FILES & SICKDAYCLAIM ---------------------------------------------
			
			@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_sickday_claim}/files", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<?> createSickDayClaimFile(@RequestBody File file, 
													@PathVariable(name="team_key") Integer team_key,
													@PathVariable(name="id_client") Integer id_client,
													@PathVariable(name="id_sickday_claim") Integer id_sickday_claim){

				File createdFile = fileDAO.createFile(file,team_key, id_client, "ID_sick_day_claim", id_sickday_claim); // this will set the id on the person object
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id_file}")
						.buildAndExpand(createdFile.getId_file()).toUri();

				//by rest conventions we need to repond with the URI for newly created resource 
				return ResponseEntity.created(location).build();
					
			}
			

			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_sick_day_claim}/files/{id_file}", method=RequestMethod.GET)
			public @ResponseBody File getSickDayClaimFileById(	@PathVariable(name="id_file") Integer id_file,
														@PathVariable(name="id_client") Integer id_client,
														@PathVariable(name="team_key") Integer team_key,
														@PathVariable(name="id_sick_day_claim") Integer id_sick_day_claim){
				return fileDAO.getFileById(id_file,team_key);
			}
			
			//Find specific GearType with ID
			@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_sick_day_claim}/files", method=RequestMethod.GET)
			public @ResponseBody List<File> getSickDayClaimFiles(@PathVariable(name="id_client") Integer id_client,
															@PathVariable(name="team_key") Integer team_key,
															@PathVariable(name="id_sick_day_claim") Integer id_sick_day_claim){
				
				return fileDAO.getFilesByIdOfForeignKey("ID_sick_day_claim",id_sick_day_claim,team_key);
			}
			
			@RequestMapping( path="/{team_key}/clients/{id_client}/sickdaypolicy/sickdayclaims/{id_sick_day_claim}/files/{id_file}", method=RequestMethod.DELETE)
			public ResponseEntity<?> deleteSickDayClaimFile(@PathVariable(name="id_file") Integer id_file,
												@PathVariable(name="id_client") Integer id_client,
												@PathVariable(name="team_key") Integer team_key,
												@PathVariable(name="id_sick_day_claim") Integer id_sick_day_claim){
				
				int updatedRows = fileDAO.deleteFile(id_file, team_key);
				
				if(updatedRows == 0 ){
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.noContent().build();
			}	
	
	
	/*
	//updateTeam(Team team)
	@RequestMapping( path="/{team_key}/files/{id_file}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFile(@PathVariable(name="team_key") Integer team_key, @PathVariable(name="id_file") Integer id_file, @RequestBody File file){
		file.setId_file(id_file);
		
		int updatedRows = fileDAO.updateFile(file, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/{team_key}/files/{id_file}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteFile(@PathVariable(name="id_file") Integer id_file, 
											@PathVariable(name="team_key") Integer team_key){
		
		int updatedRows = fileDAO.deleteFile(id_file, team_key);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}*/
}
