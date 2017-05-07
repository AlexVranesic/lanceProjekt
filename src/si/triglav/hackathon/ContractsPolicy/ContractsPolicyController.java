package si.triglav.hackathon.ContractsPolicy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import si.triglav.hackathon.Contract.Contract;
import si.triglav.hackathon.RepairService.RepairService;

@Controller
public class ContractsPolicyController {
	
	@Autowired
	private ContractsPolicyDAO contractsPolicyDAO;
	
	@RequestMapping( path="/contractpolicies/{team_key}", method=RequestMethod.GET)
	public @ResponseBody List<Contract> getContractPoliciesList(@PathVariable(name="team_key") Integer team_key){
		return contractsPolicyDAO.getContracts(team_key);
	}
}
