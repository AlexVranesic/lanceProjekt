package si.triglav.hackathon.team;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Team {
	
	private Integer id_team;
	private String team_name;
	private Integer team_key;
	
	public Integer getIdTeam() {
		return id_team;
	}
	
	public void setIdTeam(Integer id_team) {
		this.id_team = id_team;
	}

	public String getTeamName() {
		return team_name;
	}
	
	public void setTeamName(String team_name) {
		this.team_name = team_name;
	}
	
	public Integer getTeamKey() {
		return team_key;
	}

	public void setTeamKey(Integer team_key) {
		this.team_key = team_key;
	}
}