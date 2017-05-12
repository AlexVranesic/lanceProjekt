package si.triglav.hackathon.SickDaysClaim;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.File.File;

public class SickDaysClaim {
	private Integer id_sick_day_claim;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_to;
	private Integer claim_is_valid;
	private Double claim_value;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date claim_date;
	private String return_account_number;
	private List<File> files;
	public Integer getId_sick_day_claim() {
		return id_sick_day_claim;
	}
	public void setId_sick_day_claim(Integer id_sick_day_claim) {
		this.id_sick_day_claim = id_sick_day_claim;
	}
	public Date getDate_from() {
		return date_from;
	}
	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}
	public Date getDate_to() {
		return date_to;
	}
	public void setDate_to(Date date_to) {
		this.date_to = date_to;
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
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
}
