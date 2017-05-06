package si.triglav.hackathon.ClientsClient;

import si.triglav.hackathon.ClientsClient.ClientsClient;

public class ClientsClient {
	private Integer ID_clients_client;
	private String name;
	private Integer tax_id;
	//private Integer id_team;
	private Double risk_contract_percent;
	
	public Integer getID_clients_client() {
		return ID_clients_client;
	}
	public void setID_clients_client(Integer iD_clients_client) {
		ID_clients_client = iD_clients_client;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTax_id() {
		return tax_id;
	}
	public void setTax_id(Integer tax_id) {
		this.tax_id = tax_id;
	}
	
	/*
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	*/
	public Double getRisk_contract_percent() {
		return risk_contract_percent;
	}
	public void setRisk_contract_percent(Double risk_contract_percent) {
		this.risk_contract_percent = risk_contract_percent;
	}
}
