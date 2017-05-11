package si.triglav.hackathon.Contract;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.ClientsClient.ClientsClient;
import si.triglav.hackathon.File.File;

public class Contract {
	Integer id_contract;
	Double contract_value;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date payment_due_to;
	Integer is_paid;	
	Integer claim_is_valid;
	Double claim_value;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date claim_date;
	String return_account_number;
	Integer id_clients_client;
	ClientsClient clients_client;
	
	List<File> files;
	
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
	public Integer getId_contract() {
		return id_contract;
	}
	public void setId_contract(Integer id_contract) {
		this.id_contract = id_contract;
	}
	public ClientsClient getClients_client() {
		return clients_client;
	}
	public void setClients_client(ClientsClient clients_client) {
		this.clients_client = clients_client;
	}
	public Integer getId_clients_client() {
		return id_clients_client;
	}
	public void setId_clients_client(Integer id_clients_client) {
		this.id_clients_client = id_clients_client;
	}
	public Double getContract_value() {
		return contract_value;
	}
	public void setContract_value(Double contract_value) {
		this.contract_value = contract_value;
	}
	public Date getPayment_due_to() {
		return payment_due_to;
	}
	public void setPayment_due_to(Date payment_due_to) {
		this.payment_due_to = payment_due_to;
	}
	public Integer getIs_paid() {
		return is_paid;
	}
	public void setIs_paid(Integer is_paid) {
		this.is_paid = is_paid;
	}
	
}
