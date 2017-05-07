package si.triglav.hackathon.Client;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Client {
	
	private Integer id_client;
	private String email;
	private String name;
	private String surname;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birth_date;
	
	private Integer is_fulltime;
	private Integer y_of_experience;
	private Integer y_of_experience;
	
	
	private Integer t;
	
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public Integer getTeam_key() {
		return team_key;
	}
	public void setTeam_key(Integer team_key) {
		this.team_key = team_key;
	}
	

}