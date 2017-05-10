package si.triglav.hackathon.GearClaim;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.ClaimType.ClaimType;
import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.File.File;
import si.triglav.hackathon.Gear.Gear;
import si.triglav.hackathon.GearClaim.GearClaim;
import si.triglav.hackathon.RepairService.RepairService;

public class GearClaim {

	private Integer id_gear_claim;
	private String event_description;
	private Integer claim_is_valid;
	private Double return_account_number;
	private Double claim_value;
	private String returnAccountNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date claim_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date event_date;
	
	//spodaj so razredi od katerih je GearType odvidsen
	List<File> files;
	
	private ClaimType claimType;
	
	private RepairService reapir_service;
	//

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

	public Double getReturn_account_number() {
		return return_account_number;
	}

	public void setReturn_account_number(Double return_account_number) {
		this.return_account_number = return_account_number;
	}

	public Double getClaim_value() {
		return claim_value;
	}

	public void setClaim_value(Double claim_value) {
		this.claim_value = claim_value;
	}

	public String getReturnAccountNumber() {
		return returnAccountNumber;
	}

	public void setReturnAccountNumber(String returnAccountNumber) {
		this.returnAccountNumber = returnAccountNumber;
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

	public RepairService getReapir_service() {
		return reapir_service;
	}

	public void setReapir_service(RepairService reapir_service) {
		this.reapir_service = reapir_service;
	}

}

/*
import si.triglav.hackathon.GearType.GearType;

public class RepairService {
	private Integer id_repair_service;
	private String name;
	private String address;
		private GearType gear_type;
	private Integer id_gear_type;
	
	ni id_team!
	
	public GearType getGear_type() {
		return gear_type;
	}
	public void setGear_type(GearType gear_type) {
		this.gear_type = gear_type;
	}
	public Integer getId_repair_service() {
		return id_repair_service;
	}
	public void setId_repair_service(Integer id_repair_service) {
		this.id_repair_service = id_repair_service;
	}
	public Integer getId_gear_type() {
		return id_gear_type;
	}
	public void setId_gear_type(Integer id_gear_type) {
		this.id_gear_type = id_gear_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
}
*/