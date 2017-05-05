package si.triglav.hackathon.ClaimType;

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

@Repository
public class ClaimTypeDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;
 
	private static final String CLAIM_TYPE_COLUMN_LIST = "id_claim_type,claim_type";
	private static final String TABLE_NAME = "FREELANCE.CLAIM_TYPE";
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ClaimType> getClaimTypeList() {
		List<ClaimType> claimTypeList = jdbcTemplate.query("select " + CLAIM_TYPE_COLUMN_LIST + " from " + TABLE_NAME, new BeanPropertyRowMapper<ClaimType>(ClaimType.class));
		return claimTypeList;
	}
	
	public ClaimType getClaimTypeById(Integer id_claim_type) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_claim_type", id_claim_type);
		params.addValue("id_claim_type", id_claim_type);
		params.addValue("team_key", 123456778);
		ClaimType claim_type = jdbcTemplate.queryForObject("SELECT "+CLAIM_TYPE_COLUMN_LIST
															+" FROM "+ TABLE_NAME +" C "
															+" LEFT JOIN FREELANCE.TEAM T "
															+ " ON T.ID_team = C.ID_team"
															+ " WHERE id_claim_type = :id_claim_type"
															+ " AND T.team_key = :team_key", 
															params , new BeanPropertyRowMapper<ClaimType>(ClaimType.class));
		return claim_type;
	}
	
	public ClaimType createClaimType(ClaimType claim_type) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		claim_type.setId_team(1);
		
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (claim_type, ID_team) values (:claim_type, :id_team)",
				new BeanPropertySqlParameterSource(claim_type), generatedKeyHolder);
		
		ClaimType createdClaimType = getClaimTypeById(generatedKeyHolder.getKey().intValue());
		return createdClaimType;

	}

	public int updateClaimType(ClaimType claim_type) {
		int updatedRowsCount = jdbcTemplate.update(
				"update "+TABLE_NAME+" set (claim_type) = (:claim_type) where id_claim_type = :id_claim_type",
				new BeanPropertySqlParameterSource(claim_type));
		return updatedRowsCount;
		
	}

	public int deleteClaimType(Integer id_claim_type) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_claim_type = :id_claim_type", new MapSqlParameterSource("id_claim_type", id_claim_type));
		return deletedRows;
	}

}
