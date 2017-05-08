package si.triglav.hackathon.MonthlyPayment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MonthlyPayment {
	
	private Integer id_payment;
	private Double payment_value;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date payment_date;
	
	public Integer getId_payment() {
		return id_payment;
	}

	public void setId_payment(Integer id_payment) {
		this.id_payment = id_payment;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public Double getPayment_value() {
		return payment_value;
	}

	public void setPayment_value(Double payment_value) {
		this.payment_value = payment_value;
	}

}
