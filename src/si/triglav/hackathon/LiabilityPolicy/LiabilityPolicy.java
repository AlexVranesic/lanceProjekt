package si.triglav.hackathon.LiabilityPolicy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.LiabilityClaim.LiabilityClaim;

public class LiabilityPolicy {
	
	private Double premium_price;
	private Double max_claim_value;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_from;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date date_to;
	
	private List<LiabilityClaim> liabilityClaims;


	public List<LiabilityClaim> getLiabilityClaims() {
		return liabilityClaims;
	}

	public void setLiabilityClaims(List<LiabilityClaim> liabilityClaims) {
		this.liabilityClaims = liabilityClaims;
	}

	public Double getPremium_price() {
		return premium_price;
	}

	public void setPremium_price(Double premium_price) {
		this.premium_price = premium_price;
	}

	public Double getMax_claim_value() {
		return max_claim_value;
	}

	public void setMax_claim_value(Double max_claim_value) {
		this.max_claim_value = max_claim_value;
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
