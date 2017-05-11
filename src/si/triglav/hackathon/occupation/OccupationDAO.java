package si.triglav.hackathon.occupation;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class OccupationDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String OCCUPATION_COLUMN_LIST = "id_occupation, occupation";
	private static final String TABLE_NAME = "FREELANCE.OCCUPATION";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	 //this method allows occupation to be viewed by all
	
	public List<Occupation> getOccupationList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<Occupation> occupationList = jdbcTemplate.query("select "+OCCUPATION_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		return occupationList;
	}
	 
	
	public Occupation getOccupationById(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("ID_occupation", id);
		params.addValue("id_team", id_team);
		
		Occupation occupation;

		try{
			occupation = jdbcTemplate.queryForObject("select "+OCCUPATION_COLUMN_LIST+" from "+TABLE_NAME+" where ID_occupation = :ID_occupation AND id_team= :id_team",params,  new BeanPropertyRowMapper<Occupation>(Occupation.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		return occupation;
	}
	
	/*
	 * this method allows policy to be viewed only for allowed team
	 */
	
	public Occupation getAllowedOccupationById(Integer idOccupation) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", idOccupation);
		
		Occupation occupation;

		try{
			occupation = jdbcTemplate.queryForObject("select " + OCCUPATION_COLUMN_LIST + " from schema.OCCUPATION where id = :id", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		return occupation;
	}

	public Occupation createOccupation(Occupation occupation, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (occupation, id_team) values (:occupation, "+id_team+")",
				new BeanPropertySqlParameterSource(occupation), generatedKeyHolder);
		
		Occupation createdOccuptaion = getOccupationById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdOccuptaion;

	}
	
	public int updateOccupation(Occupation occupation, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (occupation) = (:occupation) "
						+" WHERE ID_occupation = :id_occupation"
						+" AND ID_team = "+id_team,
				new BeanPropertySqlParameterSource(occupation));
		return updatedRowsCount;
	}
	
	public int deleteOccupation(Integer ID_occupation, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_occupation", ID_occupation);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_occupation = :ID_occupation", params);
		return deletedRows;
	}
	
}
