package si.triglav.hackathon.Gear;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import si.triglav.hackathon.Client.Client;
import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.File.FileDAO;
import si.triglav.hackathon.GearType.GearType;
import si.triglav.hackathon.GearType.GearTypeDAO;
import si.triglav.hackathon.MonthlyPayment.MonthlyPayment;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class GearDAO {
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private FileDAO fileDAO;
	
	@Autowired
	private GearTypeDAO gearTypeDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GEAR_COLUMN_LIST = "ID_gear, gear_value, date_of_purchase, premium_price, ID_gear_type";
	private static final String TABLE_NAME = "FREELANCE.GEAR";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Gear> getGearList(Integer team_key, Integer id_client) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_client", id_client);
		
		List<Gear> gearList = jdbcTemplate.query("select "+GEAR_COLUMN_LIST
												+" from "+TABLE_NAME
												+" WHERE id_team= :id_team"
												+" AND ID_policy_product = (select ID_policy_product from FREELANCE.policy_product where id_product = 3 and id_client = :id_client) ", params, new BeanPropertyRowMapper<Gear>(Gear.class));
		
		for(Gear gear: gearList){
			gear.setGearType(gearTypeDAO.getGearTypeById(gear.getId_gear(), team_key));
			gear.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_gear", gear.getId_gear(), team_key));
		}
		return gearList;
		
		//contract.setClients_client(clientsClientDAO.getClientsClientById(contract.getId_clients_client(), team_key));
		//gear.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_gear", gear.getId_clients_client(), team_key));
		
	
	}
	
	public Gear getGearById(Integer team_key, Integer id_client,  Integer id_gear) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear", id_gear);
		params.addValue("id_client", id_client);
		params.addValue("id_team", id_team);
		//Gear gear = jdbcTemplate.queryForObject("select "+GEAR_COLUMN_LIST+" from "+TABLE_NAME+" where ID_repair_service = :ID_repair_service AND id_team= :id_team", params , new BeanPropertyRowMapper<Gear>(Gear.class));
		Gear gear;
		
		try{
			gear = jdbcTemplate.queryForObject("select "+GEAR_COLUMN_LIST
											 +" from "+TABLE_NAME
											 +" WHERE ID_team= :id_team"
											 +" AND ID_policy_product = (select ID_policy_product from FREELANCE.policy_product where id_product = 3 and id_client = :id_client) "
											 + "AND ID_gear=:id_gear", params, new BeanPropertyRowMapper<Gear>(Gear.class));

		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		gear.setGearType(gearTypeDAO.getGearTypeById(gear.getId_gear(), team_key));
		gear.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_gear", gear.getId_gear(), team_key));
		
		return gear;
	}

	public Gear createGear(Gear gear, Integer team_key,  Integer id_client){
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear", gear.getId_gear());
		params.addValue("team_key", team_key);
		params.addValue("id_client", id_client);
		params.addValue("id_gear_type", gear.getId_gear_type());
		params.addValue("id_client", id_client);
		params.addValue("gear_value", gear.getGear_value());
		params.addValue("date_of_purchase", gear.getDate_of_purchase());
		params.addValue("premium_price", gear.getPremium_price());
			
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update("insert into "+TABLE_NAME+" (gear_value, date_of_purchase, premium_price, id_team, id_gear_type, ID_policy_product) "
							+"values (:gear_value, :date_of_purchase, :premium_price,"+id_team+" ,:id_gear_type, "
							+"(select ID_policy_product "
							+"from FREELANCE.policy_product "
							+"where id_product = 3 "
							+"AND id_client = :id_client))", params, generatedKeyHolder);
		
		Gear createdGear = getGearById(team_key,id_client , generatedKeyHolder.getKey().intValue());
		gear.setFiles(fileDAO.getFilesByIdOfForeignKey("ID_gear", gear.getId_gear(), team_key));
		
		return createdGear;

	}

	public int updateGear(Gear gear, Integer team_key, Integer id_client, Integer id_gear) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		Date date_of_purchase;
		
		if(gear.getDate_of_purchase()!=null)
			date_of_purchase = new Date(gear.getDate_of_purchase().getTime()+(24*60*60*1000));
		else
			date_of_purchase = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_gear", gear.getId_gear());
		params.addValue("gear_value", gear.getGear_value());
		params.addValue("date_of_purchase", date_of_purchase);
		params.addValue("premium_price", gear.getPremium_price());
		params.addValue("id_gear_type", gear.getId_gear_type());
		params.addValue("id_client", id_client);
		params.addValue("id_team", id_team);
		
		//KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int updatedRowsCount = jdbcTemplate.update(
				 "UPDATE "+TABLE_NAME
				+" SET (premium_price, date_of_purchase, gear_value, id_gear_type) = (:premium_price, :date_of_purchase, :gear_value, :id_gear_type) "
				+" WHERE ID_policy_product = (SELECT P.ID_policy_product "
										   + "FROM FREELANCE.POLICY_PRODUCT P "
										   + "WHERE id_product=3 "
										   + "AND id_team= :id_team "
										   + "AND id_client = :id_client) "
				+" AND id_gear = :id_gear",params);
		
			return updatedRowsCount;
	}

	public int deleteGear(Integer id_gear, Integer id_client, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_gear", id_gear);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_gear = :id_gear AND id_team = :id_team", params);
		return deletedRows;
	}

}
