package si.triglav.hackathon.GearClaim;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.ClaimType.ClaimType;
import si.triglav.hackathon.File.File;
import si.triglav.hackathon.GearClaim.GearClaim;
import si.triglav.hackathon.RepairService.RepairService;

public class GearClaim {

	private Integer id_gear_claim;
	private String event_description;
	private Integer claim_is_valid;
	private Double claim_value;
	private String return_account_number;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date claim_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date event_date;
	
	//spodaj so razredi od katerih je GearType odvidsen
	List<File> files;
	
	private Integer id_claim_type;
	private ClaimType claimType;
	
	private Integer id_repair_service;
	private RepairService repair_service;
	


	public Integer getId_claim_type() {
		return id_claim_type;
	}

	public void setId_claim_type(Integer id_claim_type) {
		this.id_claim_type = id_claim_type;
	}

	public Integer getId_repair_service() {
		return id_repair_service;
	}

	public void setId_repair_service(Integer id_repair_service) {
		this.id_repair_service = id_repair_service;
	}

	public RepairService getRepair_service() {
		return repair_service;
	}

	public void setRepair_service(RepairService repair_service) {
		this.repair_service = repair_service;
	}


	public Integer getId_gear_claim() {
		return id_gear_claim;
	}

	public void setId_gear_claim(Integer id_gear_claim) {
		this.id_gear_claim = id_gear_claim;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public Integer getClaim_is_valid() {
		return claim_is_valid;
	}

	public void setClaim_is_valid(Integer claim_is_valid) {
		this.claim_is_valid = claim_is_valid;
	}

	

	public Double getClaim_value() {
		return claim_value;
	}

	public void setClaim_value(Double claim_value) {
		this.claim_value = claim_value;
	}

	public String getReturn_account_number() {
		return return_account_number;
	}

	public void setReturn_account_number(String return_account_number) {
		this.return_account_number = return_account_number;
	}

	public Date getClaim_date() {
		return claim_date;
	}

	public void setClaim_date(Date claim_date) {
		this.claim_date = claim_date;
	}

	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public ClaimType getClaimType() {
		return claimType;
	}

	public void setClaimType(ClaimType claimType) {
		this.claimType = claimType;
	}

}
