package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<Spaceship> myfleet = new ArrayList<Spaceship>(fleet);
		Collections.sort(myfleet, new myPowerComperator());
		List<String> sortedFleet = new ArrayList<String>();
		for(Spaceship spaceship: myfleet){
			sortedFleet.add(spaceship.toString());
		}
		return sortedFleet;

	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> res = new HashMap<>();
		for(Spaceship spaceship: fleet){
			if(res.containsKey(spaceship.getClass().getSimpleName())){
				res.put(spaceship.getClass().getSimpleName(),res.get(spaceship.getClass().getSimpleName()) + 1);
			}
			else {
				res.put(spaceship.getClass().getSimpleName(),1);
			}
		}
		return res;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int totalCost = 0;
		for (Spaceship spaceship: fleet){
			totalCost+= spaceship.getAnnualMaintenanceCost();
		}
		return totalCost;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> weaponsList = new HashSet<>();
		for(Spaceship spaceship: fleet){
			if(spaceship instanceof Fighter)
			{
				for(Weapon weapon: ((Fighter) spaceship).getWeapons()) {
					weaponsList.add(weapon.getName());
				}
			}
			if(spaceship instanceof Bomber)
			{
				for(Weapon weapon: ((Bomber) spaceship).getWeapons()) {
					weaponsList.add(weapon.getName());
				}
			}
		}
		return weaponsList;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int res = 0;
		for (Spaceship spaceship: fleet){
			res += spaceship.getCrewMembers().size();
		}
		return res;

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		float res = 0f;
		int numOfOfficers = 0;
		for (Spaceship spaceship: fleet){
			for(CrewMember crewMember: spaceship.getCrewMembers()){
				if(crewMember instanceof Officer){
					numOfOfficers++;
					res+= crewMember.getAge();
				}
			}
		}
		res = res/numOfOfficers;
		return res;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> res = new HashMap<>();
		for (Spaceship spaceship:fleet){
			Officer tmpOfficer = null;
			OfficerRank tmpRank = OfficerRank.Ensign;
			for(CrewMember crewMember: spaceship.getCrewMembers()){
				if(crewMember instanceof Officer) {
					if(((Officer) crewMember).getRank().compareTo(tmpRank) > 0){
						tmpRank = ((Officer) crewMember).getRank();
						tmpOfficer = ((Officer) crewMember);

					}

				}
				}
			if (tmpOfficer != null) {
				res.put(tmpOfficer, spaceship);
			}

		}
		return res;

	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank, Integer> myMap = new HashMap<>();
		for(Spaceship spaceship: fleet){
			for (CrewMember crewMember: spaceship.getCrewMembers()){
				if (crewMember instanceof Officer){
					if (myMap.containsKey(((Officer) crewMember).getRank())){
						myMap.put(((Officer) crewMember).getRank(),myMap.get(((Officer) crewMember).getRank()) + 1);
					}
					else {
						myMap.put(((Officer) crewMember).getRank(), 1);

					}
				}
			}
		}
		Set<Map.Entry<OfficerRank, Integer>> tmp = myMap.entrySet();
		List<Map.Entry<OfficerRank, Integer>> res = new ArrayList<>(tmp);
		Collections.sort(res, new myRanksComperator());

		return res;
	}

}
