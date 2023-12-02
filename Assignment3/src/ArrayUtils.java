import java.util.Arrays;

public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {

		
			if (((direction != 'R') && (direction != 'L')) || array.length == 0) {
				return array;
			}
			int place = move;
			int len = array.length;
			
			if (direction == 'R') {
				place = (len-move) % len;
				
			}
			if (direction == 'L') {	
				place = move % len;
			}	
			int [] newArray = Arrays.copyOf(array, len);
			for (int i=0; i<newArray.length; i++) {
				int newIndex = (i + (array.length-place))%array.length;
				array[newIndex] = newArray[i];
			}
			return array;
			}
		

	

	public static int minRes;
	
	public static int findShortestPath(int[][] m, int i, int j) {
		minRes = Integer.MAX_VALUE;
		if (i==j) {
			return 0;
			
		}
		findShortestPathRec(m,i,j,0,i);
		if (minRes == Integer.MAX_VALUE) {
			return -1;
		}
		else {
			return minRes;
		}
		}
	
	
	public static void findShortestPathRec(int[][] m, int i, int j, int len, int v) {
		int path = m.length;
		if (len < path + 1) {
			for (int h = 0; h<path; h++) {
				if (i != j ) {
					if(m[v][h] > 0) {
						if (h == j) {
							if (minRes == Integer.MAX_VALUE) {
								minRes = len +1;
							}
							else {
								minRes = Math.min(minRes, len + 1);
							}
						}
						findShortestPathRec(m,i,j,len+1,h);
						
							}
						}
					}
				}
			
		

				

	}

}
