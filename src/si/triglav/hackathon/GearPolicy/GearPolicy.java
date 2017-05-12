package si.triglav.hackathon.GearPolicy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.Gear.Gear;

public class GearPolicy {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_to;
	
	private List<Gear> gear;

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