package si.triglav.hackathon.MonthlyPayment;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.Client.ClientDAO;
import si.triglav.hackathon.ContractsPolicy.ContractsPolicy;
import si.triglav.hackathon.File.FileDAO;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.occupation.Occupation;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class MonthlyPaymentDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	//@Autowired
	//private ClientDAO clientDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String MONTHLY_PAYMENTY_COLUMN_TEAM_LIST = "id_payment, payment_value, payment_date";
	private static final String TABLE_NAME = "FREELANCE.MONTHLY_PAYMENT";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	//this method allows occupation to be viewed by all
	
	public List<MonthlyPayment> getMonthlyPaymentList(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		params.addValue("id_client", id_client);
		
		
		List<MonthlyPayment> monthlyPaymentList = jdbcTemplate.query("select "+MONTHLY_PAYMENTY_COLUMN_TEAM_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team AND id_client= :id_client" , params, new BeanPropertyRowMapper<MonthlyPayment>(MonthlyPayment.class));
		return monthlyPaymentList;
	}
	 
	public List<MonthlyPayment> getMonthlyPaymentList() throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<MonthlyPayment> monthlyPaymentList = jdbcTemplate.query("select * from schema.MONTHLY_PAYMENTY where team_key = :team_key", params, new BeanPropertyRowMapper<MonthlyPayment>(MonthlyPayment.class));
		
		if (monthlyPaymentList == null)
		{
			throw new Exception("Monthly payment list " + monthlyPaymentList + " does not exist in database!");
		}
		else
		{
			return monthlyPaymentList;
		}
	}
	//getMonthlyPaymentById(id_payment, id_client, team_key);
	
	//getMonthlyPaymentById
	public MonthlyPayment getMonthlyPaymentById(Integer ID_payment, Integer id_client, Integer team_key) {
		
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("ID_payment", ID_payment);
		params.addValue("id_team", id_team);
		params.addValue("id_client", id_client);
		
		//params.addValue("id_team", id_team);
		//System.out.println("===============================");
		//System.out.println(id_team);
		//System.out.println("===============================");
		MonthlyPayment monthlyPayment = jdbcTemplate.queryForObject("select "+MONTHLY_PAYMENTY_COLUMN_TEAM_LIST+" from "+TABLE_NAME+" where ID_payment = :ID_payment AND id_client=:id_client AND id_team= :id_team",params,  new BeanPropertyRowMapper<MonthlyPayment>(MonthlyPayment.class));
	
		return monthlyPayment;
	}
	
	public MonthlyPayment getAllowedMonthlyPaymentById(Integer id_payment) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id_payment);
		MonthlyPayment monthlyPayment = jdbcTemplate.queryForObject("select " + MONTHLY_PAYMENTY_COLUMN_TEAM_LIST + " from schema.MONTHLY_PAYMENTY where id = :id", params, new BeanPropertyRowMapper<MonthlyPayment>(MonthlyPayment.class));
		
		
		if (monthlyPayment == null)
		{
			throw new Exception("Payment with id " + id_payment + " does not exist in database!");
		}
		else
		{
//			if (occupation.getTeamId() == currentTeamId)
//			{
				return monthlyPayment;
//			}
//			else
//			{
//				throw new Exception("team " + occupation.getTeamId() + " is accessing other's team occupation " + idOccupation);
//			}
		}
	}

	/*
public ContractsPolicy createContractPolicy(Integer id_client, ContractsPolicy contractsPolicy, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date actualDateFrom;
		Date actualDateTo;
		//for some reason it substracts a day so we add it
		if(contractsPolicy.getDate_from()!=null)
			actualDateFrom = new Date(contractsPolicy.getDate_from().getTime()+(24*60*60*1000));
		else
			actualDateFrom = null;
		
		if(contractsPolicy.getDate_from()!=null)
			actualDateTo = new Date(contractsPolicy.getDate_to().getTime()+(24*60*60*1000));
		else
			actualDateTo = null;
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("date_from", actualDateFrom);
		params.addValue("date_to", actualDateTo);
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (date_from, date_to, ID_client, ID_team, ID_product) values (:date_from, :date_to, :ID_client, :ID_team, 1)",
				params, generatedKeyHolder);
		
		ContractsPolicy createdContractsPolicy = getContractsPolicyById(id_client, generatedKeyHolder.getKey().intValue(), team_key);
		return createdContractsPolicy;

	}
	*/
	
	public MonthlyPayment createMonthlyPayment(MonthlyPayment monthlyPayment, Integer team_key, Integer id_client) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		Date payment_date;
		Double payment_value=null;
		
		if(monthlyPayment.getPayment_date()!=null)
			payment_date = new Date(monthlyPayment.getPayment_date().getTime()+(24*60*60*1000));
		else
			payment_date = null;
		
		payment_value=monthlyPayment.getPayment_value();
		
		MapSqlParameterSource params = new MapSqlParameterSource("payment_date", payment_date);
		
		params.addValue("ID_client", id_client);
		params.addValue("ID_team", id_team);
		params.addValue("payment_value", payment_value);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (payment_date, ID_client, ID_team, payment_value) values (:payment_date, :ID_client, :ID_team, :payment_value)",
				params, generatedKeyHolder);
		
		MonthlyPayment createdMonthlyPayment = getMonthlyPaymentById(generatedKeyHolder.getKey().intValue() , id_client, team_key);
		return createdMonthlyPayment;
		
	}
	
	public int updateMonthlyPayment(MonthlyPayment monthlyPayment, Integer team_key, Integer  ID_client) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		Date payment_date;
		Double payment_value=null;
		Integer id_payment;
		
		id_payment=monthlyPayment.getId_payment();
		
		if(monthlyPayment.getPayment_date()!=null)
			payment_date = new Date(monthlyPayment.getPayment_date().getTime()+(24*60*60*1000));
		else
			payment_date = null;
		
		payment_value=monthlyPayment.getPayment_value();
		
		
		
		MapSqlParameterSource params = new MapSqlParameterSource("payment_date", payment_date);
		
		params.addValue("ID_client", ID_client);
		params.addValue("ID_team", id_team);
		params.addValue("payment_value", payment_value);
		params.addValue("id_payment", id_payment);
		
		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (payment_date, payment_value) = (:payment_date, :payment_value) "
						+" WHERE id_payment = :id_payment"
						+" AND id_team = :ID_team"
						+" AND ID_client = :ID_client",
						params);
				
		return updatedRowsCount;
	}

	public int deleteMonthlyPayment(Integer id_client, Integer id_payment, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_payment", id_payment);
		params.addValue("ID_client", id_client);

		
		int deletedRows = jdbcTemplate.update(	"delete from "+TABLE_NAME
											 +" where id_payment = :id_payment"
											 +" AND ID_team = :id_team"
											 +" AND ID_client = :ID_client", params);
		return deletedRows;
	}	
}