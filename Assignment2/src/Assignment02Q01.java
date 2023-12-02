
public class Assignment02Q01 {

	public static void main(String[] args) {
		for (String arg: args) { 
			char tav = arg.charAt(0);
			int num = (int)(tav);
			if (num % 5 == 0)
				System.out.println(tav);
			
		}
		
	}

}
