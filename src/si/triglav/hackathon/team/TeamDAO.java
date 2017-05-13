package si.triglav.hackathon.team;

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

@Repository
public class TeamDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String TEAM_COLUMN_LIST = "id_team,team_name,team_key";
	private static final String TABLE_NAME = "FREELANCE.TEAM";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Team> getTeamList() {
		List<Team> teamList = jdbcTemplate.query("select " + TEAM_COLUMN_LIST + " from " + TABLE_NAME, new BeanPropertyRowMapper<Team>(Team.class));
		return teamList;
	}
	
	public Team getTeamNameById(Integer id_team) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		Team team = jdbcTemplate.queryForObject("select "+TEAM_COLUMN_LIST+" from "+ TABLE_NAME + " where id_team = :id_team", params , new BeanPropertyRowMapper<Team>(Team.class));
		return team;
	}
	
	//preveri spodnjo metodo kaj vraca
	public Integer getTeamIdByName(String team_name) {
		MapSqlParameterSource params = new MapSqlParameterSource("team_name", team_name);
		Integer team_id = jdbcTemplate.queryForObject("select id_team from "+ TABLE_NAME + " where team_name = :team_name", params , new BeanPropertyRowMapper<Integer>(Integer.class));
		return team_id;
	}
	
	public Team createTeam(Team team) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (team_name,team_key) VALUES (:team_name,:team_key)",
				new BeanPropertySqlParameterSource(team), generatedKeyHolder);
			
		Team createdTeam = getTeamById(generatedKeyHolder.getKey().intValue());
		return createdTeam;

	}
	
	public Team getTeamById(Integer id_team) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		Team team = jdbcTemplate.queryForObject("select "+TEAM_COLUMN_LIST+" from FREELANCE.TEAM where id_team = :id_team", params , new BeanPropertyRowMapper<Team>(Team.class));
		return team;
	}
	
	public Integer getTeamIdByKey(Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("team_key", team_key);
		
		Team team;
		
		try{
			 team = jdbcTemplate.queryForObject("select id_team from FREELANCE.TEAM where team_key = :team_key FETCH FIRST 1 ROW ONLY", params , new BeanPropertyRowMapper<Team>(Team.class));
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		return team.getId_team();
	}
	
	public int updateTeam(Team team) {
		
		int updatedRowsCount = jdbcTemplate.update(
				"update "+TABLE_NAME+" set (team_name) = (:team_name) where id_team = :id_team",
				new BeanPropertySqlParameterSource(team));
		return updatedRowsCount;
		
	}

	public int deleteTeam(Integer id_team) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_team = :id_team", new MapSqlParameterSource("id_team", id_team));
		return deletedRows;
	}
}
