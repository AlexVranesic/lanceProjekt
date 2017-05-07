package si.triglav.hackathon.ContractsPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import si.triglav.hackathon.team.TeamDAO;

@Repository
public class ContractsPolicyDAO {

	@Autowired
	private TeamDAO teamDAO;
}
