package si.triglav.hackathon.RepairService;

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
public class RepairServiceDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String REPAIR_SERVICE_COLUMN_LIST = "ID_repair_service,name,address";
	private static final String TABLE_NAME = "FREELANCE.REPAIR_SERVICE";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<RepairService> getRepairServiceList() {
		List<RepairService> repairServiceList = jdbcTemplate.query("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME, new BeanPropertyRowMapper<RepairService>(RepairService.class));
		return repairServiceList;
	}
	
	public RepairService getRepairServiceById(Integer id) {
		MapSqlParameterSource params = new MapSqlParameterSource("ID_repair_service", id);
		RepairService repairService = jdbcTemplate.queryForObject("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME+" where ID_repair_service = :ID_repair_service", params , new BeanPropertyRowMapper<RepairService>(RepairService.class));
		return repairService;
	}
	
	public RepairService createRepairService(RepairService repairService) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				"insert into "+TABLE_NAME+" (name,address) values (:name,:address)",
				new BeanPropertySqlParameterSource(repairService), generatedKeyHolder);
		
		RepairService createdRepairService = getRepairServiceById(generatedKeyHolder.getKey().intValue());
		return createdRepairService;

	}

	public int updateRepairService(RepairService repairService) {
		int updatedRowsCount = jdbcTemplate.update(
				"update "+TABLE_NAME+" set (name,address) = (:name,:address) where ID_repair_service = :ID_repair_service",
				new BeanPropertySqlParameterSource(repairService));
		return updatedRowsCount;
	}

	public int deleteRepairService(Integer ID_repair_service) {
		int deletedRows = jdbcTemplate.update("delete from "+TABLE_NAME+" where ID_repair_service = :ID_repair_service", new MapSqlParameterSource("ID_repair_service", ID_repair_service));
		return deletedRows;
	}

}
