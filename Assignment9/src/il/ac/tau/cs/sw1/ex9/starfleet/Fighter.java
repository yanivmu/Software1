package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myAbstractSpaceship {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	final int annualMaintenanceCostBase = 2500;
	private int annualMaintenanceCost = 0;
	private List<Weapon> weapons;
	private int weaponsFirePower = 10;

	private int numOfCruisers = 0;
	
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name,commissionYear,maximalSpeed,0,crewMembers,0);
		this.weapons = weapons;
		this.weaponsFirePower = getFirePower();
		this.annualMaintenanceCost = getAnnualMaintenanceCost();
		this.maximalSpeed = maximalSpeed;
		
	}

	@Override
	public int getFirePower() {
		int res = 10;
		for(Weapon weapon: weapons){
			res += weapon.getFirePower();
		}
		return res;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int weaponsCost = 0;
		for(Weapon weapon: weapons){
			weaponsCost += weapon.getAnnualMaintenanceCost();
		}
		int engineCost = (int)(Math.floor(1000 * this.maximalSpeed));
		return annualMaintenanceCostBase + weaponsCost + engineCost;
	}

	public List<Weapon> getWeapons() {
		return this.weapons;
	}

	public void setWeapons(List<Weapon> weapons){
		this.weapons = weapons;
	}

	public void addingCruisers(){
		this.numOfCruisers++;
	}

	public int getNumOfCruisers() {
		return numOfCruisers;
	}


	@Override
	public String toString() {
		return "Fighter\n" +
				"\tName=" + this.getName() + "\n" +
				"\tCommissionYear=" + this.getCommissionYear() + "\n" +
				"\tMaximalSpeed=" + this.getMaximalSpeed() + "\n" +
				"\tFirePower="+this.getFirePower()+"\n" +
				"\tCrewMembers="+this.getCrewMembers().size()+"\n" +
				"\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n" +
				"\tWeaponArray="+this.getWeapons();
	}
}
