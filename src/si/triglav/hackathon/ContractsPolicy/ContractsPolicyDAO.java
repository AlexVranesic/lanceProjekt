package si.triglav.hackathon.ContractsPolicy;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractsPolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String REPAIR_SERVICE_COLUMN_LIST = "ID_policy_product,date_from,date_to";
	private static final String TABLE_NAME = "FREELANCE.POLICY_PRODUCT";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Contract> getContracts(Integer team_key) {
		
		Integer id_team=teamDAO.getTeamIdByKey(team_key);
		
		MapSqlParameterSource params = new MapSqlParameterSource("id_team", id_team);

		List<Contract> repairServiceList = jdbcTemplate.query("select "+REPAIR_SERVICE_COLUMN_LIST+" from "+TABLE_NAME+" WHERE id_team= :id_team ", params, new BeanPropertyRowMapper<Contract>(Contract.class));
		
		for(Contract repairService: repairServiceList){
			//repairService.setGear_type(gearTypeDAO.getGearTypeById(repairService.getId_gear_type(), team_key));
		}
		
		return repairServiceList;
	}
	
	
}
