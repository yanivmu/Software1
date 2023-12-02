package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myAbstractSpaceship {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	final int annualMaintenanceCostBase = 5000;
	private int annualMaintenanceCost = 0;
	private List<Weapon> weapons;

	int numberOfTechnicians;
	int weaponsFirePower = 10;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name,commissionYear,maximalSpeed,0,crewMembers,0);
		this.weapons = weapons;
		this.numberOfTechnicians = numberOfTechnicians;
		this.annualMaintenanceCost = getAnnualMaintenanceCost();
		this.firePower = getFirePower();
	
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	@Override
	public int getFirePower() {
		int res = 10;
		for (Weapon weapon : this.weapons) {
			res += weapon.getFirePower();
		}
		return res;
	}

	public int getNumberOfTechnicians() {
		return this.numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int res = 0;
		double weaponsCost = 0;
		for(Weapon weapon: weapons){
			weaponsCost += weapon.getAnnualMaintenanceCost();
		}
		weaponsCost = (weaponsCost)*(1-this.numberOfTechnicians * 0.1);
		res += (int)(Math.floor(weaponsCost));
		return annualMaintenanceCostBase + res;
	}
	public String toString() {
		return "Bomber\n" +
				"\tName=" + this.getName() + "\n" +
				"\tCommissionYear=" + this.getCommissionYear() + "\n" +
				"\tMaximalSpeed=" + this.getMaximalSpeed() + "\n" +
				"\tFirePower="+this.getFirePower()+"\n" +
				"\tCrewMembers="+this.getCrewMembers().size()+"\n" +
				"\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n" +
				"\tWeaponArray="+this.getWeapons()+ "\n" +
				"\tNumberOfTechnicians="+this.getNumberOfTechnicians();
	}


}
