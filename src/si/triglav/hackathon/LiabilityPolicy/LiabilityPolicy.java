package si.triglav.hackathon.LiabilityPolicy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.File.File;
import si.triglav.hackathon.GearType.GearType;

public class LiabilityPolicy {
	Integer ID_policies_product;

	private String description;
	private Integer id_liability;
	private Integer id_claim;
	private Integer is_valid;
	private Double claim_value;
	private String returnAccountNumber;
	private File file;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date claim_date;

	public Integer getID_policies_product() {
		return ID_policies_product;
	}

	public void setID_policies_product(Integer iD_policies_product) {
		ID_policies_product = iD_policies_product;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId_liability() {
		return id_liability;
	}

	public void setId_liability(Integer id_liability) {
		this.id_liability = id_liability;
	}

	public Integer getId_claim() {
		return id_claim;
	}

	public void setId_claim(Integer id_claim) {
		this.id_claim = id_claim;
	}

	public Integer getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Date getClaim_date() {
		return claim_date;
	}

	public void setClaim_date(Date claim_date) {
		this.claim_date = claim_date;
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