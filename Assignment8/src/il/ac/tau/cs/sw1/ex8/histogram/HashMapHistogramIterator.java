package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{
	
	//add members here
	HashMap map;
	Iterator <Map.Entry<T, Integer>> setIterator;

	//add constructor here, if needed
	public HashMapHistogramIterator(HashMap map){
		this.map = map;
		this.setIterator =  map.entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator();
	}
	
	@Override
	public boolean hasNext() {
		return setIterator.hasNext();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		if(hasNext()){
			return setIterator.next();
		}
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
	//add private methods here, if needed

}
