package si.triglav.hackathon.policy;

import si.triglav.hackathon.ContractsPolicy.ContractsPolicy;
import si.triglav.hackathon.GearPolicy.GearPolicy;
import si.triglav.hackathon.LiabilityPolicy.LiabilityPolicy;
import si.triglav.hackathon.SickDaysPolicy.SickDaysPolicy;

public class Policy {
	private Integer id_policy;
	private ContractsPolicy contractsPolicy;	
	private LiabilityPolicy liabilityPolicy;
	private SickDaysPolicy sickDaysPolicy;
	private GearPolicy gearPolicy;
	
	public Integer getId_policy() {
		return id_policy;
	}
	public void setId_policy(Integer id_policy) {
		this.id_policy = id_policy;
	}
	public ContractsPolicy getContractsPolicy() {
		return contractsPolicy;
	}
	public void setContractsPolicy(ContractsPolicy contractsPolicy) {
		this.contractsPolicy = contractsPolicy;
	}
	public LiabilityPolicy getLiabilityPolicy() {
		return liabilityPolicy;
	}
	public void setLiabilityPolicy(LiabilityPolicy liabilityPolicy) {
		this.liabilityPolicy = liabilityPolicy;
	}
	public SickDaysPolicy getSickDaysPolicy() {
		return sickDaysPolicy;
	}
	public void setSickDaysPolicy(SickDaysPolicy sickDaysPolicy) {
		this.sickDaysPolicy = sickDaysPolicy;
	}
	public GearPolicy getGearPolicy() {
		return gearPolicy;
	}
	public void setGearPolicy(GearPolicy gearPolicy) {
		this.gearPolicy = gearPolicy;
	}
	public Integer getPolicyId() {
		return id_policy;
	}
	public void setPolicyId(Integer id_policy) {
		this.id_policy = id_policy;
	}
	
}
