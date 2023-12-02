package il.ac.tau.cs.sw1.ex8.tfIdf;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {

	private boolean isInitialized = false;

	//Add members here
	HashMap<String, HashMapHistogram> filesMap = new HashMap<>();
	ArrayList<ArrayList<String>> filesArrange ;
	ArrayList<String> names;

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
	public void indexDirectory(String folderPath)  { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		HashMapHistogram map;
		for (File file : listFiles) {
			if (file.isFile()) {
				try {
					map = new HashMapHistogram();
					List<String> listString = FileUtils.readAllTokens(file);
					map.addAll(listString);
					filesMap.put(file.getName(), map);
				}
				catch (IOException exception){
					throw new RuntimeException(exception);
				}
			}
		}
		/*******************/
		names = new ArrayList<>();
		filesArrange = new ArrayList<ArrayList<String>>();
		for (Map.Entry<String, HashMapHistogram> file : filesMap.entrySet()) {
			filesArrange.add(new ArrayList<>());
			names.add(file.getKey());
		}
		String tmp = "";
		for (int h = 0; h < names.size(); h++) {
			try {
				List<String> wordTmp = filesMap.get(names.get(h)).getItemsSet().stream().toList();
				ArrayList<String> wordsOfFile = filesArrange.get(h);
				for (int k = 0; k < wordTmp.size(); k++) {
					wordsOfFile.add(k, wordTmp.get(k));
				}
				Collections.sort(wordsOfFile);
				for (int g = 0; g < wordsOfFile.size() - 1; g++) {
					for (int i = g + 1; i < wordsOfFile.size(); i++) {
						double tfidf1 = getTFIDF(wordsOfFile.get(g), names.get(h));
						double tfidf2 = getTFIDF(wordsOfFile.get(i), names.get(h));
						if (tfidf1 < tfidf2 || ((tfidf1 == tfidf2) && (wordsOfFile.get(g).compareToIgnoreCase(wordsOfFile.get(i)) > 0))){
							tmp = wordsOfFile.get(i);
							wordsOfFile.set(i, wordsOfFile.get(g));
							wordsOfFile.set(g, tmp);
						}
					}

				}

			} catch (FileIndexException ex) {
				throw new RuntimeException(ex);
			}

		}

		/*******************/
		isInitialized = true;
	}



	// Q2

	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{
		// add your code here
		if(filesMap.containsKey(fileName)){
			return filesMap.get(fileName).getCountForItem(word.toLowerCase());
		}
		throw new FileIndexException("Can not find this file");

	}

	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{
		if(filesMap.containsKey(fileName)){
			return filesMap.get(fileName).getItemsSet().size();
		}
		throw new FileIndexException("Can not find this file");
	}

	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return filesMap.size();
	}


	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		int repetitionsForWord = getCountInFile(word.toLowerCase(), fileName);
		int numOfWordsInDoc = filesMap.get(fileName).getCountsSum();
		return calcTF(repetitionsForWord,numOfWordsInDoc); //replace this with the correct value
	}

	/* @pre: isInitialized()
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int numOfDocs = getNumOfFilesInIndex();
		int numOfDocsContainingWord = 0;
		for (String file: filesMap.keySet()) {
			if (filesMap.get(file).getCountForItem(word.toLowerCase()) > 0) {
				numOfDocsContainingWord += 1;
			}
		}
		if(numOfDocsContainingWord == 0 && numOfDocs == 0){
			return calcIDF(1,1);
		}
		if(numOfDocsContainingWord ==0){
			numOfDocsContainingWord = 1;
		}

		return calcIDF(numOfDocs,numOfDocsContainingWord); //replace this with the correct value
	}


	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 *        $ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k)
			throws FileIndexException{ //Q5
		int index = names.indexOf(fileName);
		ArrayList<Map.Entry<String, Double>> topK = new ArrayList<>();
		for(int i = 0 ; i < k; i++){
			double tfidf = getTFIDF(filesArrange.get(index).get(i),fileName);
			Map.Entry<String,Double> tmp = Map.entry(filesArrange.get(index).get(i),tfidf);
			topK.add(i,tmp);
		}
		return topK; //replace this with the correct value
	}

	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		int index1 = names.indexOf(fileName1);
		int index2 = names.indexOf(fileName2);

		ArrayList<String> wordsUnit = new ArrayList<>();
		for (String word :filesArrange.get(index1)){
			wordsUnit.add(word);
		}
		for (String word :filesArrange.get(index2)){
			if(!wordsUnit.contains(word)){
				wordsUnit.add(word);
			}
		}

		double mone = 0 ;
		double mechane = 0;
		double tmp1 = 0;
		double tmp2 = 0;
		double kefel = 0;

		for (int i = 0; i < wordsUnit.size(); i++){
			if (filesArrange.get(index1).contains(wordsUnit.get(i)) && filesArrange.get(index2).contains(wordsUnit.get(i))){
				double tfidf1 = getTFIDF(wordsUnit.get(i),fileName1);
				double tfidf2 = getTFIDF(wordsUnit.get(i),fileName2);
				mone += tfidf1 * tfidf2;
			}
			if(filesArrange.get(index1).contains(wordsUnit.get(i))){
				tmp1 +=  Math.pow(getTFIDF(wordsUnit.get(i),fileName1),2);
			}
			if(filesArrange.get(index2).contains(wordsUnit.get(i))){
				tmp2 +=  Math.pow(getTFIDF(wordsUnit.get(i),fileName2),2);
			}
		}
		kefel = tmp1 * tmp2;
		mechane = Math.sqrt(kefel);
		return mone/mechane; //replace this with the correct value
	}

	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 *        $ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k)
			throws FileIndexException {//Q6
		ArrayList<Double> cosineList = new ArrayList<>();
		ArrayList<String> files = new ArrayList<>();
		ArrayList<Map.Entry<String, Double>> topK = new ArrayList<>();
		for (String file : names) {
			if (!file.equals(fileName)) {
				cosineList.add(getCosineSimilarity(file, fileName));
				files.add(file);
			}
		}
		double tmp = 0.0;
		String tmpS = "";

		for (int i = 0; i < cosineList.size() - 1; i++) {
			for (int j = i + 1; j < cosineList.size(); j++) {
				double num1 = cosineList.get(i);
				double num2 = cosineList.get(j);
				if (num1 < num2) {
					tmp = cosineList.get(i);
					cosineList.set(i, cosineList.get(j));
					cosineList.set(j, tmp);

					tmpS = files.get(i);
					files.set(i, files.get(j));
					files.set(j, tmpS);

				}
			}
		}
		for (int i = 0; i < k; i++) {
			Map.Entry<String, Double> tmpM = Map.entry(files.get(i), cosineList.get(i));
			topK.add(i, tmpM);

		}
		return topK; //replace this with the correct value
	}


	//add private methods here, if needed


	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/

	public boolean isInitialized(){
		return this.isInitialized;
	}

	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}

	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}

	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}

}