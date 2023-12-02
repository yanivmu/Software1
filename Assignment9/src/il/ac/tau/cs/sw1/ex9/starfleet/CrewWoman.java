package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class CrewWoman implements CrewMember {
	private String name;
	private int age;
	private int yearService;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CrewWoman crewWoman)) return false;
		return getName().equals(crewWoman.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

	public CrewWoman(int age, int yearsInService, String name){
		this.name = name;
		this.age = age;
		this.yearService = yearsInService;
		
	}

	public String getName(){
		return this.name;
	}

	public int getAge(){
		return this.age;
	}

	public int getYearService(){
		return this.yearService;
	}



}
