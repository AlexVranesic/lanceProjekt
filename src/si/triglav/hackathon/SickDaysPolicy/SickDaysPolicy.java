package si.triglav.hackathon.SickDaysPolicy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.SickDaysClaim.SickDaysClaim;

public class SickDaysPolicy {
	private Double premium_price;
	private Double daily_compensation;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_to;

	List<SickDaysClaim> sickDayClaims;
	
	
	public List<SickDaysClaim> getSickDayClaims() {
		return sickDayClaims;
	}
	public void setSickDayClaims(List<SickDaysClaim> sickDayClaims) {
		this.sickDayClaims = sickDayClaims;
	}
	public Double getPremium_price() {
		return premium_price;
	}
	public void setPremium_price(Double premium_price) {
		this.premium_price = premium_price;
	}
	public Double getDaily_compensation() {
		return daily_compensation;
	}
	public void setDaily_compensation(Double daily_compensation) {
		this.daily_compensation = daily_compensation;
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

}
