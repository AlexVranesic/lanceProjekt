package si.triglav.hackathon.ContractsPolicy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.Contract.Contract;

public class ContractsPolicy {
	Integer ID_policies_product;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_to;
	List<Contract> contracts;

	public Integer getID_policies_product() {
		return ID_policies_product;
	}
	public void setID_policies_product(Integer iD_policies_product) {
		ID_policies_product = iD_policies_product;
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
	public List<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
	
}
