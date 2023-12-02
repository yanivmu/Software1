
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = -1;
		// *** your code goes here below ***
		n = Integer.parseInt(args[0]);
		int [] fib = new int [n];
		fib[0] = 1;
		fib[1] = 1;
		fib[2] = 2;
		for (int i = 3; i < n; i++) {
			fib[i] = fib[i-1] + fib[i-2];
		}
				
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		// *** your code goes here below ***
		for (int j = 0; j < n; j++) {
			System.out.print(fib[j] + " ");
			if (fib[j] % 2 == 1) 
				numOfOdd +=1; 			
			}
		
		System.out.print("\n");
		System.out.println("The number of odd numbers is: "+numOfOdd);

	}

}
