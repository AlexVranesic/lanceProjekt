package si.triglav.hackathon.RepairService;

import si.triglav.hackathon.team.Team;

public class RepairService {
	private Integer ID_repair_service;
	private String name;
	private String address;
	private Integer team_key;
	private Integer id_team;
	
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public Integer getTeam_key() {
		return team_key;
	}
	public void setTeam_key(Integer team_key) {
		this.team_key = team_key;
	}
	public Integer getID_repair_service() {
		return ID_repair_service;
	}
	public void setID_repair_service(Integer iD_repair_service) {
		ID_repair_service = iD_repair_service;
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
