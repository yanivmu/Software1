package il.ac.tau.cs.sw1.ex8.histogram;

import java.sql.Array;
import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	// add members here
	HashMap <T,Integer> map;
	
	//add constructor here, if needed

	
	public HashMapHistogram(){
		map = new HashMap <T, Integer>() {
		};
	}
	
	@Override
	public void addItem(T item) {
		if(map.containsKey(item)){
			map.put(item,map.get(item)+1);
		}
		else{
			map.put(item,1);
		}
	}
	
	@Override
	public boolean removeItem(T item)  {
		if(map.containsKey(item)){
			if(map.get(item) ==1){
				map.remove(item);
				return true;
			}
			else{
				map.put(item,map.get(item)-1);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for(T item: items){
			addItem(item);
		}
	}

	@Override
	public int getCountForItem(T item) {
		if(map.containsKey(item)){
			return map.get(item);
		}
		return 0; //replace this with the correct value
	}

	@Override
	public void clear() {
		map.clear();

	}

	@Override
	public Set<T> getItemsSet() {
		return map.keySet();
	}

	
	@Override
	public int getCountsSum() {
		int res = 0;
		for(Integer nums: map.values()){
			res+= nums;
		}

		return res;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<T>(this.map) ;
	}
	
	//add private methods here, if needed
}
