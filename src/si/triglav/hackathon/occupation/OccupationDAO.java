package si.triglav.hackathon.occupation;

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

import si.triglav.hackathon.RepairService.RepairService;

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class OccupationDAO {

	@Autowired
	private TeamDAO teamDAO;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String OCCUPATION_COLUMN_LIST = "id_occupation, id_team, occupation";
	private static final String OCCUPATION_COLUMN_TEAM_LIST = "id_occupation, occupation";
	private static final String TABLE_NAME = "FREELANCE.OCCUPATION";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	 //this method allows occupation to be viewed by all
	
	public List<Occupation> getOccupationList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<Occupation> occupationList = jdbcTemplate.query("select "+OCCUPATION_COLUMN_TEAM_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		return occupationList;
	}
	 
	 
/*
	public List<Occupation> getOccupationList() throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<Occupation> occupationList = jdbcTemplate.query("select * from schema.OCCUPATION where team_key = :team_key", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		
		if (occupationList == null)
		{
			throw new Exception("Occupation list " + occupationList + " does not exist in database!");
		}
		else
		{
			return occupationList;
		}
	}
	*/
	public Occupation getOccupationById(Integer id, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("ID_occupation", id);
		params.addValue("id_team", id_team);
		//System.out.println("===============================");
		//System.out.println(id_team);
		//System.out.println("===============================");
		Occupation occupation = jdbcTemplate.queryForObject("select "+OCCUPATION_COLUMN_TEAM_LIST+" from "+TABLE_NAME+" where ID_occupation = :ID_occupation AND id_team= :id_team",params,  new BeanPropertyRowMapper<Occupation>(Occupation.class));
	
		return occupation;
	}
	
	
	/*
	 * this method allows policy to be viewed only for allowed team
	 */
	
	public Occupation getAllowedOccupationById(Integer idOccupation) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", idOccupation);
		Occupation occupation = jdbcTemplate.queryForObject("select " + OCCUPATION_COLUMN_TEAM_LIST + " from schema.OCCUPATION where id = :id", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		
		//Integer currentTeamId = null;
		/* currentTeamId = KeyChecker.getTeamKey(); naceloma se bodo oni prijavili na bazo
		 * 												    z user + geslom. Na podlagi tega, jim baza dodali nek key, ki 
		 *                                                  se hrani tako da nimajo oni dostop do njega.
		 *                                                  Na tem mestu se zahteva, da program sporoci, uporabnikov ekipni key
		 *                                                  na podlagi katerega se dovoli, alipa ne, dostop do baze
		
		*/

		if (occupation == null)
		{
			throw new Exception("Occupation with id " + idOccupation + " does not exist in database!");
		}
		else
		{
//			if (occupation.getTeamId() == currentTeamId)
//			{
				return occupation;
//			}
//			else
//			{
//				throw new Exception("team " + occupation.getTeamId() + " is accessing other's team occupation " + idOccupation);
//			}
		}
	}
/*
	public Occupation createOccupation(Occupation occupation) throws Exception {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into schema.OCCUPATION (" + OCCUPATION_COLUMN_TEAM_LIST + ") values (:id_occupation, :id_team)",
				new BeanPropertySqlParameterSource(occupation), generatedKeyHolder);
 		
		Occupation createdOccupation = getOccupationById(generatedKeyHolder.getKey().intValue());
		//Policy createdPeson = getAllowedPolicyById(generatedKeyHolder.getKey().intValue());
		return createdOccupation;

	}
*/
	public Occupation createOccupation(Occupation occupation, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (occupation, id_team) values (:occupation, "+id_team+")",
				new BeanPropertySqlParameterSource(occupation), generatedKeyHolder);
		
		Occupation createdOccuptaion = getOccupationById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdOccuptaion;

	}
	/*
	public RepairService createRepairService(RepairService repairService, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name, address, id_team, id_gear_type) values (:name, :address, "+id_team+", 2)",
				new BeanPropertySqlParameterSource(repairService), generatedKeyHolder);
		
		RepairService createdRepairService = getRepairServiceById(generatedKeyHolder.getKey().intValue(), team_key);
		return createdRepairService;

	}
	*/
	public int updateOccupation(Occupation occupation, Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		int updatedRowsCount = jdbcTemplate.update(
						 "UPDATE "+TABLE_NAME
						+" SET (occupation) = (:occupation) "
						+" WHERE ID_occupation = :ID_occupation"
						+" AND ID_team = "+id_team,
				new BeanPropertySqlParameterSource(occupation));
		return updatedRowsCount;
	}
	
	/*
	public int deleteOccupation(Integer id) {
		
		// tukaj lahko tudi preverjas, ce pravilna skupina brise zapis in ce ne, potem ne dovoli
		int deletedRows = jdbcTemplate.update("delete from schema.OCCUPATION where id = :id", new MapSqlParameterSource("id", id));
		return deletedRows;
	}
	*/
	public int deleteOccupation(Integer ID_occupation, Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("ID_occupation", ID_occupation);
		
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_occupation = :ID_occupation", params);
		return deletedRows;
	}
	
}
