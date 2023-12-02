package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;
import java.util.Map;

public class myRanksComperator implements Comparator<Map.Entry<OfficerRank, Integer>> {
    @Override
    public int compare(Map.Entry<OfficerRank, Integer> m1, Map.Entry<OfficerRank, Integer> m2) {
        if (m1.getValue() != m2.getValue()) {
            return Integer.compare(m1.getValue(), m2.getValue());
        } else {
            return m1.getKey().compareTo(m2.getKey());
        }
        }
}
