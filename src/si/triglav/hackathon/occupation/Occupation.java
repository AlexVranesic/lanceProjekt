package si.triglav.hackathon.occupation;

public class Occupation {
	private Integer id_occupation;
	private Integer id_team;
	private String occupation;
	
	public Integer getId() {
		return id_occupation;
	}
	public Integer getIdTeam() {
		return id_team;
	}
	public String getOccupation() {
		return occupation;
	}
	
	public void setId(Integer id_occupation) {
		this.id_occupation = id_occupation;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
}
