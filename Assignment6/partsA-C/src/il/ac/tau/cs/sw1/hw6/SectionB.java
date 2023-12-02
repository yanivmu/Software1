package il.ac.tau.cs.sw1.hw6;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) {
		if(array == null){
			return false;
		}
		int len = array.length;
		for (int i = 0 ; i < len ; i++){
			if (array[i] == value){
				return true;
			}
		}
		return false;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) {

		return 0;
	}
	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] <= $ret
	*/
	public static int max(int[] array) {

		return array[array.length-1];
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) { 
		int cnt1 = Integer.MAX_VALUE;
		for (int i = 0 ; i< array.length; i++){
			if (array[i] < cnt1){
				cnt1 = array[i];
			}
		}
		return cnt1;
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(word.length() - i - 1)

	*/
	public static String reverse(String word) 
	{
		String res = "";
		for (int i = word.length() -1 ; i >= 0 ; i--){
			res += word.charAt(i);
		}
		return res;
	}
	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret))
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true
	*/
	public static int[] guess(int[] array) { 
		int num1 = array[0];
		int num2 = 0;
		for (int i = 0 ; i < array.length; i++){
			num2 = array[i];
			if (num1 != num2){
				array[0] = num2;
				array[i] = num1;
				break;
			}
		}
		return array;
	}


}
