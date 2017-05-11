package si.triglav.hackathon.Gear;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.File.File;
import si.triglav.hackathon.Gear.Gear;
import si.triglav.hackathon.GearType.GearType;

public class Gear {
	
	private Integer id_gear;
	private Double gear_value;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_of_purchase;
	
	//spodaj so razredi od katerih je GearType odvidsen
	List<File> files;
	
	private Double premium_price;

	private GearType gearType;
	private Integer id_gear_type;
	
	public Integer getId_gear_type() {
		return id_gear_type;
	}

	public void setId_gear_type(Integer id_gear_type) {
		this.id_gear_type = id_gear_type;
	}

	

	public GearType getGearType() {
		return gearType;
	}

	public void setGearType(GearType gearType) {
		this.gearType = gearType;
	}

	public Integer getId_gear() {
		return id_gear;
	}

	public void setId_gear(Integer id_gear) {
		this.id_gear = id_gear;
	}

	public Double getGear_value() {
		return gear_value;
	}

	public void setGear_value(Double gear_value) {
		this.gear_value = gear_value;
	}

	public Date getDate_of_purchase() {
		return date_of_purchase;
	}

	public void setDate_of_purchase(Date date_of_purchase) {
		this.date_of_purchase = date_of_purchase;
	}

	public Double getPremium_price() {
		return premium_price;
	}

	public void setPremium_price(Double premium_price) {
		this.premium_price = premium_price;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
}
