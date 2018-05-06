package lse;



import java.io.*;

import java.util.*;





/**

 * This class builds an index of keywords. Each keyword maps to a set of pages in

 * which it occurs, with frequency of occurrence in each page.

 *

 */

public class LittleSearchEngine {



	/**

	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is

	 * an array list of all occurrences of the keyword in documents. The array list is maintained in

	 * DESCENDING order of frequencies.

	 */

	HashMap<String,ArrayList<Occurrence>> keywordsIndex;



	/**

	 * The hash set of all noise words.

	 */

	HashSet<String> noiseWords;



	/**

	 * Creates the keyWordsIndex and noiseWords hash tables.

	 */

	public LittleSearchEngine() {

		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);

		noiseWords = new HashSet<String>(100,2.0f);

	}



	/**

	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences

	 * in the document. Uses the getKeyWord method to separate keywords from other words.

	 *

	 * @param docFile Name of the document file to be scanned and loaded

	 * @return Hash table of keywords in the given document, each associated with an Occurrence object

	 * @throws FileNotFoundException If the document file is not found on disk

	 */

	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile)

			throws FileNotFoundException {

		/** COMPLETE THIS METHOD **/



		// System.out.println("In loadKeyWordsFromDocument...");



		HashMap<String, Occurrence> aMap = new HashMap<String, Occurrence>(1000, 2.0f);

		Scanner scanner = new Scanner(new File(docFile));

		while(scanner.hasNextLine()) {



			// another loop to parse through each word on the line

			String line = scanner.nextLine();

			String[] words = line.split("\\s+");



			for (int i = 0; i < words.length; i++) {

				// check for a non-word character before blindly performing a replacement



				words[i] = words[i].replaceAll("[^\\w]", "");

				words[i] = getKeyword(words[i]);



				if(words[i] != null) {

					if(aMap.containsKey(words[i])) {

						Occurrence occurrence = aMap.get(words[i]);

						occurrence.frequency++;

						aMap.put(words[i], occurrence);

					}else {

						Occurrence occurrence = new Occurrence(docFile, 1);

						aMap.put(words[i], occurrence);

					}

				}

			}



		}

		scanner.close();

		// System.out.println("noiseWords loaded...");

		//  System.out.println("Printing keywords...");





		return aMap;

	}



	/**

	 * Merges the keywords for a single document into the master keywordsIndex

	 * hash table. For each keyword, its Occurrence in the current document

	 * must be inserted in the correct place (according to descending order of

	 * frequency) in the same keyword's Occurrence list in the master hash table.

	 * This is done by calling the insertLastOccurrence method.

	 *

	 * @param kws Keywords hash table for a document

	 */

	public void mergeKeywords(HashMap<String,Occurrence> kws) {

		/** COMPLETE THIS METHOD **/

		//System.out.println("In mergeKeywords...");

		//System.out.println(kws);

		boolean keywordExists = false;

		for (Map.Entry<String, Occurrence> entry : kws.entrySet()) {

			// System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());



			// check if keyword already exists in the master keywordsIndex

			// if yes, add the occurrence

			// if not, add the keyword and its occurrence from current file



			ArrayList<Occurrence> occ = new ArrayList<Occurrence>();



			if (keywordsIndex.containsKey(entry.getKey())) {

				occ = keywordsIndex.get(entry.getKey());

				keywordExists = true;

			}



			Occurrence newOcc = new Occurrence(entry.getValue().document,entry.getValue().frequency);

			occ.add(newOcc);



			// sort arraylist in desc order of frequency



			for (int i=0; i< occ.size(); i++) {

				for (int j=occ.size()-1; j> i; j--) {

					if (occ.get(i).frequency < occ.get(j).frequency) {

						Occurrence temp = occ.get(i);

						occ.set(i, occ.get(j));

						occ.set(j, temp);

					}

				}

			}



			if (keywordExists) {

				System.out.println(insertLastOccurrence(occ));

			}



			keywordsIndex.put(entry.getKey(), occ);



		}

	}



	/**

	 * Given a word, returns it as a keyword if it passes the keyword test,

	 * otherwise returns null. A keyword is any word that, after being stripped of any

	 * trailing punctuation, consists only of alphabetic letters, and is not

	 * a noise word. All words are treated in a case-INsensitive manner.

	 *

	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'

	 *

	 * @param word Candidate word

	 * @return Keyword (word without trailing punctuation, LOWER CASE)

	 */

	public String getKeyword(String word) {

		/** COMPLETE THIS METHOD **/

		String new_word = "";

		word = word.toLowerCase();



		if(word.equals(null) || word.length() <= 0) {

			return null;

		}else {

			// loop through word

			for(int i = 0; i < word.length()-1; i++) {

				if(Character.isLetter(word.charAt(i))){ // if is letter add to new word

					new_word = new_word + Character.toString(word.charAt(i));

				}else if(!Character.isLetter(word.charAt(i))) { // if not letter

					if(Character.toString(word.charAt(i)).equals(".") || Character.toString(word.charAt(i)).equals(",") ||

							Character.toString(word.charAt(i)).equals("?") || Character.toString(word.charAt(i)).equals(":") ||

							Character.toString(word.charAt(i)).equals(";") || Character.toString(word.charAt(i)).equals("!")){



						if(Character.isLetter(word.charAt(i+1))){

							return null;

						}

					}else { // if number or other punctuation

						return null;

					}

				}

			}

		}



		if(Character.isLetter(word.charAt(word.length()-1))) {

			new_word = new_word + Character.toString(word.charAt(word.length()-1));

		}



		if(noiseWords.contains(word)) {

			return null;

		}



		return new_word;

	}



	/**

	 * Inserts the last occurrence in the parameter list in the correct position in the

	 * list, based on ordering occurrences on descending frequencies. The elements

	 * 0..n-2 in the list are already in the correct order. Insertion is done by

	 * first finding the correct spot using binary search, then inserting at that spot.

	 *

	 * @param occs List of Occurrences

	 * @return Sequence of mid point indexes in the input list checked by the binary search process,

	 *         null if the size of the input list is 1. This returned array list is only used to test

	 *         your code - it is not used elsewhere in the program.

	 */

	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

		/** COMPLETE THIS METHOD **/



		// following line is a placeholder to make the program compile

		// you should modify it as needed when you write your code

		int[] indexArray = new int[occs.size()];

		for (int i=0; i <occs.size(); i++) {

			indexArray[i] = occs.get(i).frequency;

		}

		int srchVal = occs.get(occs.size()-1).frequency;



		return binarySearchDesc(indexArray, srchVal);

	}



	private ArrayList<Integer> binarySearchDesc(int[] a, int srchVal){

		ArrayList<Integer> indexes = new ArrayList<Integer>();

		int lb = 0;

		int ub = a.length - 1;



		while(lb <= ub){

			int mid = (lb + ub)/2;

			indexes.add(mid);

			if(a[mid] == srchVal){

				//System.out.println("Value found, mid: " + mid);

				return indexes;

			}

			else if(srchVal < a[mid]){

				lb = mid + 1;

			}

			else{

				ub = mid - 1;

			}

		}

		if (indexes.isEmpty()) {

			return null;

		} else {

			return indexes;

		}

	}



	/**

	 * This method indexes all keywords found in all the input documents. When this

	 * method is done, the keywordsIndex hash table will be filled with all keywords,

	 * each of which is associated with an array list of Occurrence objects, arranged

	 * in decreasing frequencies of occurrence.

	 *

	 * @param docsFile Name of file that has a list of all the document file names, one name per line

	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line

	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk

	 */

	public void makeIndex(String docsFile, String noiseWordsFile)

			throws FileNotFoundException {

		//System.out.println("makeIndex called...");



		// load noise words to hash table

		Scanner sc = new Scanner(new File(noiseWordsFile));

		while (sc.hasNext()) {

			String word = sc.next();

			noiseWords.add(word);

		}



      /*System.out.println("noiseWords loaded...");

      System.out.println("Printing noiseWords...");



      for (String s : noiseWords) {

          System.out.println(s);

      }



      System.out.println("Completed printing noiseWords...");*/



		// index all keywords

		sc = new Scanner(new File(docsFile));

		while (sc.hasNext()) {

			String docFile = sc.next();

			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);

			mergeKeywords(kws);

		}

		sc.close();

		//System.out.println("Master keywords index: ");

		//System.out.println(keywordsIndex);

	}



	/**

	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that

	 * document. Result set is arranged in descending order of document frequencies. (Note that a

	 * matching document will only appear once in the result.) Ties in frequency values are broken

	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2

	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result.

	 * The result set is limited to 5 entries. If there are no matches at all, result is null.

	 *

	 * @param kw1 First keyword

	 * @param kw1 Second keyword

	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of

	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.

	 */

	public ArrayList<String> top5search(String kw1, String kw2) {

		/** COMPLETE THIS METHOD **/

		//System.out.println("In top5search...");



		// AK start

		boolean keywordExists = false;

		ArrayList<Occurrence> occ = new ArrayList<Occurrence>();

		if (keywordsIndex.containsKey(kw1)) {

			occ = keywordsIndex.get(kw1);

			keywordExists = true;

		}



		// AK end



		ArrayList<String> result = new ArrayList<>(5);

		ArrayList<Occurrence> kw1_list = new ArrayList<>();

		ArrayList<Occurrence> kw2_list = new ArrayList<>();

		// combined list

		ArrayList<Occurrence> combined = new ArrayList<>();



		if (keywordsIndex.containsKey(kw1)){

			kw1_list = keywordsIndex.get(kw1);

			System.out.println("kw1 found...");

			System.out.println(kw1_list);

			result.add(kw1_list.toString());

		}



		if (keywordsIndex.containsKey(kw2)){

			kw2_list = keywordsIndex.get(kw2);

			System.out.println("kw2 found...");

			System.out.println(kw2_list);

			result.add(kw2_list.toString());

		}



		// add to the combined list

		combined.addAll(kw1_list);

		combined.addAll(kw2_list);



/*      if(!(kw1_list.isEmpty() && kw2_list.isEmpty())){ // both lists are not empty



         // sort combined

         for(int i = 0; i < combined.size(); i++){



            for(int j = 0; j < combined.size() - i; j++){

               if(combined.get(j).frequency < combined.get(i).frequency){

                  // swap terms

                  Occurrence temp = combined.get(j-1);

                  combined.set(j-1 , combined.get(j));

                  combined.set(j, temp);

               }

            }



         }



      }*/



		// remove duplicates

/*      for(int i = 0; i < combined.size(); i++){

         for(int j = 1; j < combined.size(); j++){



            if(combined.get(i).document == combined.get(j).document){

               combined.remove(j);

            }



         }

      }*/



		// top 5

/*      while(combined.size() > 5){

         combined.remove(combined.size());

      }

      System.out.println("Top 5 search method");

      System.out.print(combined);

      for(Occurrence oc : combined){

         result.add(oc.document);

      }*/



		return result;

	}

}