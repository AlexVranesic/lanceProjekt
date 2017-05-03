package si.triglav.hackathon.policy;


public class Policy {
	private Integer id_policy;
	private Integer id_occupation;
	private Integer id_client;
	
	public Integer getPolicyId() {
		return id_policy;
	}
	public void setPolicyId(Integer id_policy) {
		this.id_policy = id_policy;
	}
	public Integer getOccupationId() {
		return id_occupation;
	}
	public void setOccupationId(Integer id_occupation) {
		this.id_occupation = id_occupation;
	}
	public Integer getClientId() {
		return id_client;
	}
	public void setClientId(Integer id_client) {
		this.id_client = id_client;
	}
	
}
