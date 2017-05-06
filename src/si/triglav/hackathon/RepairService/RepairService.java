package si.triglav.hackathon.RepairService;

public class RepairService {
	private Integer ID_repair_service;
	private String name;
	private String address;
	
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
