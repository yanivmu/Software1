import  java.util.Arrays;
import static java.lang.Math.min;

public class StringUtils {

	public static String findSortedSequence(String str) {
		if (str.equals("")) {
			return "";
		}
		String[] array = str.split(" ");
		String[] words = Arrays.copyOf(array, array.length+1);
		words[array.length] = "1";
		int len = words.length;
		int curIndex = 0;
		int startIndex = 0;
		int maxLen = 0;
		int curLen = 1;
		for (curIndex = 0;curIndex<len-1; curIndex++) {
			int check = words[curIndex].compareTo(words[curIndex+1]); 
			if (check <= 0) {
				curLen++;
			}
			if (check > 0) {
				if (curLen >= maxLen) {
					maxLen = curLen;
					startIndex = curIndex-curLen+1;
				}
				curLen = 1;
			}
			
		}
		
		
		String [] newArray = Arrays.copyOfRange(words, startIndex, startIndex+maxLen);
		
		return String.join(" ", newArray); //Replace this with the correct returned value

	}

	   
	public static boolean isEditDistanceOne(String a, String b){
		int num1 = a.length();
		int num2 = b.length();
		String bigWord = "";	
		String smallWord = "";
		int cnt = 0;
		if (num1 - num2 > 1 || num2 - num1 > 1) {
			return false; 
		}
		if (num1 == num2) {
			 cnt = 0;
			for (int i=0; i<num1; i++) {
				if (a.charAt(i) != b.charAt(i)) {
					cnt++;
				}
			}
			if (cnt > 1) {
				return false;
						
			}
			else {
				return true;
			}
		}
		
		else {
			if(num1 >= num2) {
				 bigWord = a;	
				 smallWord = b;
			}
			else {
				 bigWord = b;	
				 smallWord = a;	
			}
			int j = 0;
			
			int min = min(num1,num2);
			for(int k=0; k<min; k++) {
				if (bigWord.charAt(k) != smallWord.charAt(j)) {
					cnt++;	
					}
				else {
					j++;
				}
			}
			if (cnt < 2) {
				return true;
			}
		}
		
		
		
		return false; //Replace this with the correct returned value
	}
}
