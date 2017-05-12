package si.triglav.hackathon.GearPolicy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.Gear.Gear;

public class GearPolicy {
	//Integer ID_policies_product;

	private Double gear_value;
	private Double premium_price;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_of_purchase;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_to;
	
	//private List<Gear> gear;
	
	private List<Gear> gear;

	public Double getGear_value() {
		return gear_value;
	}

	public void setGear_value(Double gear_value) {
		this.gear_value = gear_value;
	}

	public Double getPremium_price() {
		return premium_price;
	}

	public void setPremium_price(Double premium_price) {
		this.premium_price = premium_price;
	}

	public Date getDate_of_purchase() {
		return date_of_purchase;
	}

	public void setDate_of_purchase(Date date_of_purchase) {
		this.date_of_purchase = date_of_purchase;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public List<Gear> getGear() {
		return gear;
	}

	public void setGear(List<Gear> gear) {
		this.gear = gear;
	}
}