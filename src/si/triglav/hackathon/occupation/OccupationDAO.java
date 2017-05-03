package si.triglav.hackathon.occupation;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OccupationDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String OCCUPATION_COLUMN_LIST = "id_occupation, id_team, occupation";
	private static final String OCCUPATION_COLUMN_TEAM_LIST = "id_occupation, id_team, occupation";
	
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	/*
	 * this method allows occupation to be viewed by all
	 */
	
	public Occupation getOccupationList(Integer team_key) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource();
		Occupation occupationList = jdbcTemplate.queryForObject("select * from schema.OCCUPATION where team_key = :team_key", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		
		if (occupationList == null)
		{
			throw new Exception("Occupation list " + occupationList + " does not exist in database!");
		}
		else
		{
			return occupationList;
		}
	}
	
	/*
	 * this method allows policy to be viewed by all
	 */
	
	public Occupation getOccupationById(Integer idOccupation) throws Exception {
		MapSqlParameterSource params = new MapSqlParameterSource("id", idOccupation);
		Occupation occupation = jdbcTemplate.queryForObject("select " + OCCUPATION_COLUMN_LIST + " from schema.OCCUPATION where id = :id", params, new BeanPropertyRowMapper<Occupation>(Occupation.class));
		
		if (occupation == null)
		{
			throw new Exception("Occupation with id " + idOccupation + " does not exist in database!");
		}
		else
		{
			return occupation;
		}
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

	public Occupation createOccupation(Occupation occupation) throws Exception {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into schema.OCCUPATION (" + OCCUPATION_COLUMN_TEAM_LIST + ") values (:id_occupation, :id_team)",
				new BeanPropertySqlParameterSource(occupation), generatedKeyHolder);
 		
		Occupation createdOccupation = getOccupationById(generatedKeyHolder.getKey().intValue());
		//Policy createdPeson = getAllowedPolicyById(generatedKeyHolder.getKey().intValue());
		return createdOccupation;

	}

	public int deleteOccupation(Integer id) {
		
		// tukaj lahko tudi preverjas, ce pravilna skupina brise zapis in ce ne, potem ne dovoli
		int deletedRows = jdbcTemplate.update("delete from schema.OCCUPATION where id = :id", new MapSqlParameterSource("id", id));
		return deletedRows;
	}
}
