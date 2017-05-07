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
import si.triglav.hackathon.team.TeamDAO;


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

	private static final String CLIENT_COLUMN_LIST = "id_client,email,name,surname,birth_date,is_fulltime,y_of_experience,annual_income,addressl1,addressl2,post,city,country,password,card_number,ccv";
	private static final String TABLE_NAME = "FREELANCE.CLIENT";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Client> getClientList() {
		List<Client> clientList = jdbcTemplate.query("select " + CLIENT_COLUMN_LIST + " from " + TABLE_NAME, new BeanPropertyRowMapper<Client>(Client.class));
		return clientList;
	}
	
	public Client getClientById(Integer id_client,Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_client", id_client);
						
		Client client = jdbcTemplate.queryForObject("select "+CLIENT_COLUMN_LIST+" from "+ TABLE_NAME + " where id_client = :id_client AND id_team= "+teamDAO.getTeamIdByKey(team_key), params , new BeanPropertyRowMapper<Client>(Client.class));
		return client;
	}
	
	public Client createClient(Client client, Integer team_key) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (client,id_team) VALUES (:client,"+teamDAO.getTeamIdByKey(team_key)+")",
				new BeanPropertySqlParameterSource(client), generatedKeyHolder);
			
		Client created_client = getClientById(generatedKeyHolder.getKey().intValue(), team_key);
		return created_client;

	}
	
	public int updateClient(Client client, Integer team_key) {
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
				+" SET (client) = (:client) "
				+" WHERE ID_client = :id_client"
				+" AND ID_team = "+teamDAO.getTeamIdByKey(team_key),
				new BeanPropertySqlParameterSource(client));
		
		return updatedRowsCount;
		
	}

	public int deleteClient(Integer id_client, Integer team_key) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_client = :id_client AND id_team="+teamDAO.getTeamIdByKey(team_key), new MapSqlParameterSource("id_client", id_client));
		return deletedRows;
	}
}
