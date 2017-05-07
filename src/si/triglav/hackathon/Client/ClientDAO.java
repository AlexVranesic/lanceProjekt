package si.triglav.hackathon.Client;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.GearType.GearTypeDAO;
import si.triglav.hackathon.RepairService.RepairService;
import si.triglav.hackathon.occupation.OccupationDAO;
import si.triglav.hackathon.team.TeamDAO;
import si.triglav.hackathon.occupation.Occupation;

@Repository
public class ClientDAO {

	/*
	 * 
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
	 * 
	 */
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String CLIENT_COLUMN_LIST = "id_client,email,name,surname,birth_date,is_fulltime,y_of_experience,annual_income,addressl1,addressl2,post,city,country,password,card_number,ccv,id_occupation,id_team";
	private static final String TABLE_NAME = "FREELANCE.CLIENT";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private OccupationDAO occupationDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Client> getClientList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		
		List<Client> clientList = jdbcTemplate.query("select " + CLIENT_COLUMN_LIST + " from " + TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<Client>(Client.class));
		
		for(Client client: clientList){
			client.setOccupation(occupationDAO.getOccupationById(client.getId_occupation(), team_key));
		}
		
		return clientList;
	}
	
	public Client getClientById(Integer id_client,Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_client", id_client);
		params.addValue("id_team", id_team);
		
		Client client = jdbcTemplate.queryForObject("select "+CLIENT_COLUMN_LIST+" from "+ TABLE_NAME + " where id_client = :id_client AND id_team= :id_team", params , new BeanPropertyRowMapper<Client>(Client.class));
		
		client.setOccupation(occupationDAO.getOccupationById(client.getId_occupation(), team_key));
		
		return client;
	}
	
	public Client createClient(Client client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (email,name,surname,birth_date,is_fulltime,y_of_experience,annual_income,addressl1,addressl2,post,city,country,password,card_number,ccv, id_occupation, ID_team) VALUES (:email, :name, :surname, :birth_date, :is_fulltime, :y_of_experience, :annual_income, :addressl1, :addressl2, :post, :city, :country, :password, :card_number, :ccv, :id_occupation, "+id_team+")",
				new BeanPropertySqlParameterSource(client), generatedKeyHolder);
			
		Client created_client = getClientById(generatedKeyHolder.getKey().intValue(), team_key);
		return created_client;
	}
	
	public int updateClient(Client client, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
				+" SET (email,name,surname,birth_date,is_fulltime,y_of_experience,annual_income,addressl1,addressl2,post,city,country,password,card_number,ccv, id_occupation) = (:email, :name, :surname, :birth_date, :is_fulltime, :y_of_experience, :annual_income, :addressl1, :addressl2, :post, :city, :country, :password, :card_number, :ccv, :id_occupation) "
				+" WHERE ID_client = :id_client"
				+" AND ID_team = "+id_team,
				new BeanPropertySqlParameterSource(client));
		
		return updatedRowsCount;
		
	}
	
	public int deleteClient(Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_client = :id_client", params);
		return deletedRows;
	}
}
