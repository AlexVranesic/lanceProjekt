package si.triglav.hackathon.Client;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.occupation.Occupation;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;

public class Client {
	
	private Integer id_client;
	private String email;
	private String name;
	private String surname;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birth_date;
	
	private Integer is_fulltime;
	private Integer y_of_experience;
	private Integer annual_income;
	private String addressl1;
	private String addressl2;
	private Integer post;
	private String city;
	private String country;
	private String password;
	private Integer card_number;
	private String ccv;
	
	private Occupation occupation;
	private MonthlyPayment monthlyPayment;
	
	private Integer id_occupation;
	private Integer id_payment;
	
	List<MonthlyPayment> monthlyPayments;

	public Integer getId_client() {
		return id_client;
	}

	public void setId_client(Integer id_client) {
		this.id_client = id_client;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public Integer getIs_fulltime() {
		return is_fulltime;
	}

	public void setIs_fulltime(Integer is_fulltime) {
		this.is_fulltime = is_fulltime;
	}

	public Integer getY_of_experience() {
		return y_of_experience;
	}

	public void setY_of_experience(Integer y_of_experience) {
		this.y_of_experience = y_of_experience;
	}

	public Integer getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(Integer annual_income) {
		this.annual_income = annual_income;
	}

	public String getAddressl1() {
		return addressl1;
	}

	public void setAddressl1(String addressl1) {
		this.addressl1 = addressl1;
	}

	public String getAddressl2() {
		return addressl2;
	}

	public void setAddressl2(String addressl2) {
		this.addressl2 = addressl2;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCard_number() {
		return card_number;
	}

	public void setCard_number(Integer card_number) {
		this.card_number = card_number;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public MonthlyPayment getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(MonthlyPayment monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Integer getId_occupation() {
		return id_occupation;
	}

	public void setId_occupation(Integer id_occupation) {
		this.id_occupation = id_occupation;
	}

	public Integer getId_payment() {
		return id_payment;
	}

	public void setId_payment(Integer id_payment) {
		this.id_payment = id_payment;
	}

	public List<MonthlyPayment> getMonthlyPayments() {
		return monthlyPayments;
	}

	public void setMonthlyPayments(List<MonthlyPayment> monthlyPayments) {
		this.monthlyPayments = monthlyPayments;
	}
	
	
}