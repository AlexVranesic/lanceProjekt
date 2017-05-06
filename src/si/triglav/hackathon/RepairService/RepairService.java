package si.triglav.hackathon.RepairService;

import si.triglav.hackathon.GearType.GearType;

public class RepairService {
	private Integer id_repair_service;
	private String name;
	private String address;
	private GearType gear_type;
	private Integer id_gear_type;
	
	public GearType getGear_type() {
		return gear_type;
	}
	public void setGear_type(GearType gear_type) {
		this.gear_type = gear_type;
	}
	public Integer getId_repair_service() {
		return id_repair_service;
	}
	public void setId_repair_service(Integer id_repair_service) {
		this.id_repair_service = id_repair_service;
	}
	public Integer getId_gear_type() {
		return id_gear_type;
	}
	public void setId_gear_type(Integer id_gear_type) {
		this.id_gear_type = id_gear_type;
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
