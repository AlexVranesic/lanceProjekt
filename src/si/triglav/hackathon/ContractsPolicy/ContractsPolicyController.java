package si.triglav.hackathon.ContractsPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ContractsPolicyController {
	@Autowired
	private ContractsPolicy contractsPolicyDAO;
}
