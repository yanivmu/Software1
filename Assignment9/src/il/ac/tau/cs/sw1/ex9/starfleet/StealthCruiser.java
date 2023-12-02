package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {
	public static List<StealthCruiser> stealthsList = new ArrayList<StealthCruiser>();

	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private int firePower;
	private Set<? extends CrewMember> crewMembers;
	private int annualMaintenanceCost = 0;
	private List<Weapon> weapons;

	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		super.addingCruisers();
		this.weapons = weapons;
		this.stealthsList.add(this);
		this.annualMaintenanceCost = this.getAnnualMaintenanceCost();
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name,commissionYear,maximalSpeed,crewMembers,new ArrayList<Weapon>());
		this.weapons = new ArrayList<Weapon>();
		this.weapons.add((new Weapon("Laser Cannons", 10, 100)));
		super.addingCruisers();
		this.annualMaintenanceCost = this.getAnnualMaintenanceCost();
		super.setWeapons(weapons);
		super.getFirePower();
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int res = 0;
		for(StealthCruiser stealthCruiser: this.stealthsList) {
			res += 50;
		}

		return res + super.getAnnualMaintenanceCost() ;
	}

	public String toString() {
		return "StealthCruiser\n" +
				"\tName=" + this.getName() + "\n" +
				"\tCommissionYear=" + this.getCommissionYear() + "\n" +
				"\tMaximalSpeed=" + this.getMaximalSpeed() + "\n" +
				"\tFirePower="+this.getFirePower()+"\n" +
				"\tCrewMembers="+this.getCrewMembers().size()+"\n" +
				"\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n" +
				"\tWeaponArray="+this.getWeapons();
	}
}
