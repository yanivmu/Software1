package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	final int annualMaintenanceCostBase = 3500;
	private  int annualMaintenanceCost = 0;
	private List<Weapon> weapons;

	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		this.weapons = weapons;
		super.setWeapons(this.weapons);
		this.crewMembers = crewMembers;
		this.maximalSpeed = maximalSpeed;
	}


	public int getAnnualMaintenanceCost() {
		int res = 0;
		int weaponsCost = 0;
		int engineCost = 0;
		int crewCost = 0;
		crewCost = 500* super.getCrewMembers().size();
		engineCost = (int)(1200 * this.maximalSpeed);
		for(Weapon weapon: super.getWeapons()){
			weaponsCost+= weapon.getAnnualMaintenanceCost();
		}
		res += weaponsCost + engineCost + crewCost + annualMaintenanceCostBase;
		this.annualMaintenanceCost = res;

		return res;
	}

	public String toString() {
		return "CylonRaider\n" +
				"\tName=" + this.getName() + "\n" +
				"\tCommissionYear=" + this.getCommissionYear() + "\n" +
				"\tMaximalSpeed=" + this.getMaximalSpeed() + "\n" +
				"\tFirePower="+this.getFirePower()+"\n" +
				"\tCrewMembers="+this.getCrewMembers().size()+"\n" +
				"\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n" +
				"\tWeaponArray="+this.getWeapons();
	}
}
