package il.ac.tau.cs.sw1.ex4;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		int len = word.length();
		char [] res = new char [len];
		for (int i=0;i<len;i++) {
			if (template[i] == true) {
				res[i] = HIDDEN_CHAR ;
			}
			else {
				res[i] = word.charAt(i);
					
				}
			}
					
		
		return res;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		int word_len = word.length();
		int tmp_len = template.length;
		if (word_len != tmp_len) {
			return false;
		}
		int cnt1 = 0;
		int cnt2 = 0;
		for (int i=0; i<word_len;i++) {
			if (template[i] == true) {
				cnt1++;
			}
			if (template[i] == false) {
				cnt2++;
			}
		if (cnt1 == 1 && cnt2 == 1) {
			break;
		}
		}
		if (cnt1 == 0 || cnt2 == 0) {
			return false;
		}
		
		for (int j=0;j<word_len;j++) {
			for (int k=j;k<word_len;k++) {
				if (word.charAt(k)== word.charAt(j)) {
					if (template[k]!=template[j]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	
	public static int subset(int n,int k) {
		   if (k == 0) {
		      return 1;
		   }
		   if (n == k) {
		      return 1;
		   } else {
		      return subset(n - 1, k - 1) + subset(n - 1, k);
		   }
		}
	
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int size = subset(word.length(),k);
		boolean[][] res = new boolean[size][word.length()] ;
		boolean [] temp;
		int last_place = 0;
		double max_res = Math.pow(2, word.length());
		
		for (int i = 1 ;i < max_res; i++ ) {
			temp = new boolean [word.length()];
			String bin = Integer.toBinaryString(i);

			for (int p = 0; p< word.length();p++) {
				if(bin.length() < word.length() ) {
				bin =  "0" + bin;
				}
			}

			int one_bin = bin.length() - bin.replace("1", "").length();
			if (one_bin == k) {
				for (int j = 0 ; j < word.length() ; j++ ) {
					if (bin.charAt(j)== '1') {
						temp[j] = true;
					}
					else {
						temp[j] = false;
					}
				}
			if (checkLegalTemplate(word,temp)) {
				res[last_place] = temp;
				last_place++;
			
			}
				
			}
		}
		if (last_place > 0) {
			return Arrays.copyOf(res, last_place);
		}
		else {
			boolean [][] empty = {};
			return empty;
		}
	}
	
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int cnt = 0;
		int len = word.length();
		
		for (int i = 0 ; i < len; i++) {
			if ( guess == word.charAt(i)) {
				if (puzzle[i] == HIDDEN_CHAR ) {
					cnt++;
					puzzle[i] = guess;
				}
			}
		}
		return cnt;
	}
	

	
	
	public static int applyGuess2(char guess, String word, char[] puzzle) { // Q - 4
		int cnt = 0;
		int len = word.length();
		
		for (int i = 0 ; i < len; i++) {
			if ( guess == word.charAt(i)) {
				if (puzzle[i] == HIDDEN_CHAR ) {
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		char [] res = new char [2];
		Random rand = new Random();
		boolean cor = false , wor = false;
		char chr1 = 'a' , chr2 = 'a' ;
		boolean [] cur_guessed = new boolean [already_guessed.length];
		cur_guessed = Arrays.copyOf(already_guessed,already_guessed.length );
		
		for (int i = 0 ; i < puzzle.length; i++) {
			if (puzzle[i] != HIDDEN_CHAR) {
				cur_guessed[(int)(puzzle[i] - 97)] = true;
			}
		}
		
		while (cor == false || wor == false) {
			int rand_int = rand.nextInt(26);
			char char_int = (char)(rand_int+97);
			if (cur_guessed[rand_int] == false) {
				
				int cnt = applyGuess2( char_int , word, puzzle);
				if (cor == false) {
					if (cnt > 0) {
						cor = true;
						chr1 = char_int;
						
					}
				}
				if (wor == false) {
					if (cnt == 0) {
						wor = true;
						chr2 = char_int;
								
					}
				}
			}
		}
		if ((int)(chr1) >= (int)(chr2)) {
			res[0] = chr2;
			res[1] = chr1;
		}
		else {
			res[0] = chr1;
			res[1] = chr2;
		}

		return res;
	}

	

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6

		System.out.println("--- stage Settings ---");
		
		while (true) {
			Random rand = new Random();
			int input = -1;
			int rand_int = -1;
			char [] puzzle = new char [word.length()];
			String [] chars = new String [word.length()];
			boolean[] template = new boolean [word.length()];
			
			System.out.println("Choose a (1) random or (2) manual template:");
			
			if (inputScanner.hasNext()) {
				input = inputScanner.nextInt();
			}
			if (input == 1) {
				System.out.println("Enter number of hidden characters:");
				
				if (inputScanner.hasNext()) {
					input = inputScanner.nextInt();
				}
				boolean [][] legal_tmp = getAllLegalTemplates(word,input);
				if (legal_tmp.length > 0) {
					
					rand_int = rand.nextInt(legal_tmp.length);
					puzzle = createPuzzleFromTemplate(word, legal_tmp[rand_int]);
					return puzzle;
				}
				else {
					System.out.println("Cannot generate puzzle, try again.");
					
				}
			}
			else if (input == 2) {
				System.out.println("Enter your puzzle template:");
				if (inputScanner.hasNext()) {
						chars = inputScanner.next().split(",");
						template = new boolean [word.length()];
						for (int i = 0 ; i < chars.length; i++) {
							if (chars[i].equals("_")) {
								template[i] = true;
							}
							if (chars[i].equals("X")) {
								template[i] = false;
							}
						}
				}
				if (checkLegalTemplate(word,template)) {
					return createPuzzleFromTemplate(word, template);
				}
				else {
					System.out.println("Cannot generate puzzle, try again.");
					
				}
						}
		}
		
	}


	
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		System.out.println("--- stage Game---");
		int cnt = 0;
		String input = "";
		
		boolean [] cur_guessed = new boolean [26];
		
		for (int i = 0 ; i < puzzle.length; i++) {
			if (puzzle[i] != HIDDEN_CHAR) {
				cur_guessed[(int)(puzzle[i] - 97)] = true;
			}
		
		for (int j = 0 ; j < puzzle.length; j++) {
			if (puzzle[i] == HIDDEN_CHAR) {
				cnt++;
				}
			}
		}
		cnt = cnt + 3;
		while (cnt > 0) {
			String curPuzzle = "";
			for (int k = 0; k < puzzle.length ; k++) {
				curPuzzle = curPuzzle + puzzle[k];
			}
			System.out.println(curPuzzle);
			System.out.println("Enter your guess:");
			if (inputScanner.hasNext()) {
				 input = inputScanner.next();
			}
			if (input.equals("H")) {
				char[] hint =  getHint(word, puzzle, cur_guessed);
				System.out.println("Here's a hint for you: choose either "+ hint[0] + " or "+ hint[1] + ".");
			}
			else {
				cnt--;
				cur_guessed[(int)(input.charAt(0) - 97)] = true;
				int cor_guess = applyGuess(input.charAt(0), word, puzzle);
				
				if (cor_guess > 0) {
					int win = 0;
					
					for (int k = 0 ; k < puzzle.length; k++) {
						if (puzzle[k] == HIDDEN_CHAR) {
							win++;
						}
					}
					if (win == 0) {
						System.out.println("Congratulations! You solved the puzzle!");
						break;
					}
					else {
						System.out.println("Correct guess, "+ cnt + " gussess left");
						if (cnt == 0) {
						System.out.println("Game over!");
						break;
						}
					}
				}
				else if (cor_guess == 0) {
					System.out.println("Wrong Guess, "+ cnt +" guesses left.");
					if (cnt == 0) {
					System.out.println("Game over!");
					break;
				}
					
				}
				
			}
			
			}
		
		
			
		
	}
				
				


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
