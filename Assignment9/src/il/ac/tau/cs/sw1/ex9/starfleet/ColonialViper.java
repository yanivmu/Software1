package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter  {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	final int annualMaintenanceCostBase = 4000;
	private int annualMaintenanceCost = 0;
	private List<Weapon> weapons;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		this.weapons = weapons;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;

	}

	@Override
	public int getAnnualMaintenanceCost() {
		int res = 0;
		int weaponsCost = 0;
		int crewCost = 0;
		int engineCost = 0;
		for(Weapon weapon: super.getWeapons()){
			weaponsCost+= weapon.getAnnualMaintenanceCost();
		}
		crewCost = 500* super.getCrewMembers().size();
		engineCost = (int)(500 * this.maximalSpeed);
		res += weaponsCost + engineCost + crewCost + annualMaintenanceCostBase;
		this.annualMaintenanceCost = res;

		return res;
	}

	public String toString() {
		return "ColonialViper\n" +
				"\tName=" + this.getName() + "\n" +
				"\tCommissionYear=" + this.getCommissionYear() + "\n" +
				"\tMaximalSpeed=" + this.getMaximalSpeed() + "\n" +
				"\tFirePower="+this.getFirePower()+"\n" +
				"\tCrewMembers="+this.getCrewMembers().size()+"\n" +
				"\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n" +
				"\tWeaponArray="+this.getWeapons();
	}
}
