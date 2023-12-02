

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below		
		double piEstimation = 0.0;
		int num = Integer.parseInt(args[0]);
		int j = 1;
		for(int i = 0 ; i < num ; i++) {
			if(i % 2 == 0) {
				piEstimation += 1.0/j;
			}
			else 
				piEstimation -= 1.0/j;
			j +=2;
		}
		piEstimation = 4 * piEstimation;
				
		// *** your code goes here below ***
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);
		
	}

}
