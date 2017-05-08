package si.triglav.hackathon.Contract;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.ClientsClient.ClientsClient;

public class Contract {
	Integer id_contract;
	Double contract_value;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date payment_due_to;
	Integer is_paid;
	Integer id_clients_client;
	ClientsClient clients_client;
	
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
