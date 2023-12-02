package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon extends CrewWoman {
	private int age;
	private int yearService;
	private int modelNum;
	private String name;


	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		super(age, yearsInService, name);
		this.modelNum = modelNumber;
		
	}
	public int getModelNum(){
		return this.modelNum;
	}

}
