package il.ac.tau.cs.sw1.ex5;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.lang.Math;

import org.w3c.dom.Text;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	public boolean inArray (String[] tavs, String tav, int current) {
		for (int i = 0 ; i< current; i++) {
			if (tavs[i].equals(tav)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		 String  text = "";  
		 int wordsCnt = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			   String strCurrentLine;

			   while ((strCurrentLine = br.readLine()) != null) {
				   text = text + " " + strCurrentLine;
			   }

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		  
		  String[] words = new String [MAX_VOCABULARY_SIZE];
		  String word = "";
		  boolean tav = false;
		  boolean notTavOrDigit = false;
		  boolean someNum = false;
		  
		  for (int i=0; i< text.length(); i++) {
			  word = "";
			  while (text.charAt(i) != ' ') {
				  word = word + text.charAt(i); 
				  if (i < text.length() -1) {
					  i++;
				  }
				  else {
					  break;
				  }
			  }
			 char [] tavArray = word.toLowerCase().toCharArray();
			 for (int k = 0 ; k< tavArray.length; k++) {
				 if ((int)(tavArray[k]) >= 97 && (int)(tavArray[k]) <= 122) {
					 tav = true;
				 }
				 else if ((int)(tavArray[k]) >= 48 && (int)(tavArray[k]) <= 57) {
					 notTavOrDigit = true;
				 }
			 }
			 if (wordsCnt >= MAX_VOCABULARY_SIZE) {
				 break;
				 
			 }
			 if (tav) {
				 if(!inArray(words,word.toLowerCase(), wordsCnt)) {
					words[wordsCnt] = word.toLowerCase();
					wordsCnt++;
				 }
			 }
			 else if (notTavOrDigit = false && word != "") {
				 if (!someNum) {
					 words[wordsCnt] = SOME_NUM;
					 someNum = true;
					 wordsCnt++;
				 }
			 }
			 tav = false;
			 notTavOrDigit = false;
		  }
		  
		  
		return Arrays.copyOf(words, wordsCnt);
	}
	
	public int place (String[] vocabulary,String word) {
		boolean inVocab = false;
		int cnt = -1;
		for (int i = 0 ; i <vocabulary.length; i++ ) {
			if (vocabulary[i].equals(word)) {
				cnt = i;
			}
		}
		return cnt;
	}
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		String text = "";
		String word = "";
		String word1 = "";
		String word2 = "";
		int place1 = -1;
		int place2 = -1;
		int [][] dualWords = new int [vocabulary.length][vocabulary.length];
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			   String strCurrentLine;

			   while ((strCurrentLine = br.readLine()) != null) {
				   word1 = "";
				   word2 = "";
				   for (int i=0; i< strCurrentLine.length(); i++) {
					   	  word2 = "";
						  while (strCurrentLine.charAt(i) != ' ') {
							  word2 = word2 + strCurrentLine.charAt(i); 
							  if (i < strCurrentLine.length() -1) {
								  i++;
							  }
							  else {
								  break;
							  }
						  }
						  if (word1 != "" && word2 != "") {
							  place1 = place (vocabulary, word1.toLowerCase());
							  place2 = place (vocabulary, word2.toLowerCase());
							  if (place1 != -1 && place2 != -1) {
								  dualWords[place1][place2]++;
							  }
						  }
						  word1 = word2;
					  }
			   }

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		
		  
		return dualWords;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		
		try {
	        FileWriter myWriter = new FileWriter(fileName+VOC_FILE_SUFFIX);
	        myWriter.write(mVocabulary.length + " words\n");
	        for (int i = 0; i < mVocabulary.length; i++) {
	        	myWriter.write(i + "," + mVocabulary[i]+"\n");
	        }
	        myWriter.close();
	        //System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	       // System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
		
		try {
	        FileWriter myWriter = new FileWriter(fileName+COUNTS_FILE_SUFFIX);
	        for (int k = 0; k < mBigramCounts.length; k++) {
	        	for (int j = 0; j < mBigramCounts.length; j++) {
	        		if (mBigramCounts[k][j] > 0) {
	        			myWriter.write(k + "," + j + ":" + mBigramCounts[k][j]+"\n");
	        		}
	        	}
	        }
	        myWriter.close();
	       // System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	      //  System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		int cnt = -1;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName+VOC_FILE_SUFFIX))) {
			   String strCurrentLine;
			   boolean firstloop = true;
			   while ((strCurrentLine = br.readLine()) != null) {
				   if(cnt == -1) {
					   int i = 0;
					   while (strCurrentLine.charAt(i) != ' ') {
					   i++;
					   }
					cnt = Integer.parseInt(strCurrentLine.substring(0, i));
					mVocabulary = new String [cnt];
				   }   
				   if (!firstloop) {
					   int k = 0;
					   while (strCurrentLine.charAt(k) != ',') {
						   k++;
					   }
					mVocabulary[Integer.parseInt(strCurrentLine.substring(0, k))] = strCurrentLine.substring(k+1);
				   }
				   firstloop = false;
			   }

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		mBigramCounts = new int [cnt][cnt];
		try (BufferedReader br = new BufferedReader(new FileReader(fileName+COUNTS_FILE_SUFFIX))) {
			   String strCurrentLine;
			   while ((strCurrentLine = br.readLine()) != null) {
				   int g = 0; int h = 0;
					   while (strCurrentLine.charAt(g) != ',') {
					   g++;
					   }
					   while (strCurrentLine.charAt(h) != ':') {
					   h++;
					   }
					   mBigramCounts[Integer.parseInt(strCurrentLine.substring(0, g))][Integer.parseInt(strCurrentLine.substring(g+1, h))] = 
							   Integer.parseInt(strCurrentLine.substring(h + 1));

			   }

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for (int i = 0 ; i < mVocabulary.length; i++) {
			if (mVocabulary[i].equals(word)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int word1Index = getWordIndex(word1);
		int word2Index = getWordIndex(word2);
		
		if (word1Index != -1 && word2Index!= -1 ) {
			return mBigramCounts[word1Index][word2Index];
		}
		
		return 0;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int wordIndex = getWordIndex(word);
		int max = -1;
		int res = -1;
		for (int i =0 ; i<mBigramCounts[wordIndex].length; i++) {
			if (mBigramCounts[wordIndex][i] > max) {
				max = mBigramCounts[wordIndex][i];
				res = i;
			}
		}
		if (max != 0) {
			return mVocabulary[res];
		}
		return null;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8

		int cnt = -1;
		String [] array = sentence.split(" ") ;
		if (array.length == 0) {
			return true;
		}
		if (array.length == 1) {
			if (getWordIndex(array[0]) != -1 ) {
				return true;
			}
			else {
				return false;
			}
		}
		for (int i = 0 ; i <  array.length -1 ; i++ ) {
			cnt = getBigramCount(array[i], array[i+1]);
			if (cnt == 0) {
				return false;
			}

		}
		
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		boolean array1 = false;
		boolean array2 = false;
		double a = 0;
		double b = 0;
		double c = 0;
		double res = 0;
		
		for (int i = 0 ; i < arr1.length; i++) {
			if (arr1[i] != 0) {
				array1 = true;
			}
			if (arr2[i] != 0) {
				array2 = true;
			}
			a += arr1[i]*arr1[i];
			b += arr2[i]*arr2[i];
			c += arr1[i]*arr2[i];
		}
		if(array1 == false || array2 == false) {
			return -1;
		}
		res = c / (Math.sqrt(b)*Math.sqrt(a));
		return res;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		int index = getWordIndex(word);
		double res = -1.0;
		double max = -1.0;
		int cnt = 0;
		if (mVocabulary.length == 1) {
			return word;
		}
		for (int i =0 ; i < mVocabulary.length ; i++) {
			if (i != index) {
				res = calcCosineSim(mBigramCounts[index],mBigramCounts[i]);
				if (res > max) {
					max = res;
					cnt = i;
				}
			}
		}
		return mVocabulary[cnt];
	}

	
}
