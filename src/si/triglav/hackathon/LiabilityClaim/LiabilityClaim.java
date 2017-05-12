package si.triglav.hackathon.LiabilityClaim;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.File.File;

public class LiabilityClaim {
	private Integer id_liability_claim;
	private String description;
	private Integer claim_is_valid;
	private Double claim_value;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date claim_date;
	private String return_account_number;
	private List<File> files;
	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public Integer getId_liability_claim() {
		return id_liability_claim;
	}
	public void setId_liability_claim(Integer id_liability_claim) {
		this.id_liability_claim = id_liability_claim;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Date getClaim_date() {
		return claim_date;
	}
	public void setClaim_date(Date claim_date) {
		this.claim_date = claim_date;
	}
	public String getReturn_account_number() {
		return return_account_number;
	}
	public void setReturn_account_number(String return_account_number) {
		this.return_account_number = return_account_number;
	}
	
	
}
