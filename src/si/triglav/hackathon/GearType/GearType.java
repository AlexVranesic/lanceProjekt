package si.triglav.hackathon.GearType;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GearType {
	
	private Integer id_gear_type;
	private Integer id_team;
	private String gear_type;
	
	public Integer getId_gear_type() {
		return id_gear_type;
	}
	public void setId_gear_type(Integer id_gear_type) {
		this.id_gear_type = id_gear_type;
	}
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public String getGear_type() {
		return gear_type;
	}
	public void setGear_type(String gear_type) {
		this.gear_type = gear_type;
	}
	
}