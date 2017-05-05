package si.triglav.hackathon.ClaimType;

public class ClaimType {
	private Integer id_claim_type;
	private String claim_type;
	private Integer id_team;
	
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public Integer getId_claim_type() {
		return id_claim_type;
	}
	public void setId_claim_type(Integer id_claim_type) {
		this.id_claim_type = id_claim_type;
	}
	public String getClaim_type() {
		return claim_type;
	}
	public void setClaim_type(String claim_type) {
		this.claim_type = claim_type;
	}
	
}
