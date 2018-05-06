package lse;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class lseTestDriver {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		// get file name that contains list of documents such as "docs.txt" as well as
		// name of file containing list of noise words such as "noisewords.txt"
		
		// create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter file name that contains list of documents: ");
		
		// get their input as a String
	    String docsFile = scanner.next();
		
		System.out.println("Enter file name that contains list of noise words: ");
		
		// get their input as a String
	    String noiseWordsFile = scanner.next();
		
	    scanner.close();
	    System.out.println("File name that contains list of noise words: " + docsFile);
	    System.out.println("File name that contains list of noise words: " + noiseWordsFile);
	    
	    // create a LittleSearchEngine object
	    
	    LittleSearchEngine lse = new LittleSearchEngine();
	    
	    // call makeIndex
	    lse.makeIndex(docsFile, noiseWordsFile);
	    
	    // call top5search
	    List<String> searchResults = new ArrayList<String>();
	    String kw1 = "deep";
	    String kw2 = "world";
	    searchResults = lse.top5search(kw1, kw2);
	    
	   // print searchResults
	    if(searchResults != null) {
	    	for(String obj:searchResults)  {
		        System.out.println(obj);  
		     }
	    } else
	    {
	    	System.out.println("Nothing to print...");
	    }
	    
	}

}
