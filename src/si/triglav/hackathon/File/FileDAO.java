package si.triglav.hackathon.File;

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
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class FileDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String FILE_COLUMN_LIST = "id_file, path";
	private static final String TABLE_NAME = "FREELANCE.FILE";
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<File> getFileList(Integer team_key) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		
		List<File> fileList = jdbcTemplate.query("select " + FILE_COLUMN_LIST + " from " + TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<File>(File.class));
		return fileList;
	}
	

	public File createFile(File file, Integer team_key, Integer id_client, String idKeyName, Integer IDKey) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource("idkey", IDKey);

		params.addValue("path", file.getPath());

		jdbcTemplate.update("insert into "+TABLE_NAME+" (path, ID_team, "+idKeyName+") VALUES (:path,"+teamDAO.getTeamIdByKey(team_key)+", :idkey)",
				params, generatedKeyHolder);
			
		File created_File = getFileById(generatedKeyHolder.getKey().intValue(), team_key);
		return created_File;
	}
	
	public List<File> getFilesByIdOfForeignKey(String idKeyName, Integer IDKey, Integer team_key) {
		
		MapSqlParameterSource params = new MapSqlParameterSource(idKeyName, IDKey);
						
		List<File> fileList = jdbcTemplate.query("select "+FILE_COLUMN_LIST
												+" from "+ TABLE_NAME
												+ " where "+idKeyName+" = :"+idKeyName+" "
												+ "AND id_team= "+teamDAO.getTeamIdByKey(team_key), params,  new BeanPropertyRowMapper<File>(File.class));
		return fileList;
	}
	
	public File getFileById(Integer id_file,Integer team_key) {
		MapSqlParameterSource params = new MapSqlParameterSource("id_file", id_file);
						
		File file = jdbcTemplate.queryForObject("select "+FILE_COLUMN_LIST+" from "+ TABLE_NAME + " where id_file = :id_file AND id_team= "+teamDAO.getTeamIdByKey(team_key), params , new BeanPropertyRowMapper<File>(File.class));
		return file;
	}

	public int updateFile(File file, Integer team_key) {
		
		int updatedRowsCount = jdbcTemplate.update(
				"UPDATE "+TABLE_NAME
				+" SET (path) = (:path) "
				+" WHERE id_file = :id_file"
				+" AND ID_team = "+teamDAO.getTeamIdByKey(team_key),
				new BeanPropertySqlParameterSource(file));
		
		return updatedRowsCount;
		
	}

	public int deleteFile(Integer id_file, Integer team_key) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where id_file = :id_file AND id_team="+teamDAO.getTeamIdByKey(team_key), new MapSqlParameterSource("id_file", id_file));
		return deletedRows;
	}
	
	/*
	
	
	public List<File> getFileListFromIdContract(Integer team_key, Integer id_contract) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_contract", id_contract);
		
		List<File> fileList = jdbcTemplate.query("select " + FILE_COLUMN_LIST + " from " + TABLE_NAME+" WHERE ID_contract= :id_contract AND id_team= :id_team", params, new BeanPropertyRowMapper<File>(File.class));
		return fileList;
	}
	
	public List<File> getFilesOfGearClaimListFromIdGearClaim(Integer team_key, Integer id_gear_claim) {
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);
		params.addValue("id_gear_claim", id_gear_claim);
		
		List<File> fileList = jdbcTemplate.query("select " + FILE_COLUMN_LIST + " from " + TABLE_NAME+" WHERE ID_gear_claim= :id_gear_claim AND id_team= :id_team", params, new BeanPropertyRowMapper<File>(File.class));
		return fileList;
	}
	
	
	*/
}
