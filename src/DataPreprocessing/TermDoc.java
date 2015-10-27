package DataPreprocessing;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

public class TermDoc {

	/** Map of all stopWords */
	private static Map<String, String> stopWordMap = new HashMap<String, String>();
	/** List of all documents */
	private static List<Document> documents = new ArrayList<Document>();
	private static Map<String, Map<String, Integer>> r = new HashMap<>();
	public static  File[] f3 = null;

	
		/*public static void main(String[] args) throws IOException {
			Gui jag = new Gui();
				
				jag.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jag.setSize(500, 500);
				jag.setVisible(true);
				
			}*/
		public void input() throws IOException
		{

	
		List<File> allFiles = new ArrayList<File>();
		
		int g=0;
		while(g<f3.length)
		{
			File f = new File(f3[g].getPath());
			allFiles.add(f);
			g++;
		}

		

		// construct the stop words mapping
		constructStopWords();

		// for each file, attempt to parse it
		for (File f : allFiles) {
			try {
				parseDocument(f);
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		// try writing to map
		try {
			// want a document-term matrix A (which is just the term freq)
			writeToMap();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		FreqWords Word = new FreqWords();
		Word.Freq(r);
	//	System.out.println("(" + (System.currentTimeMillis() - startTime) + " msecs)");
	}

	/**
	 * Populates the stop words map with a mapping of all possible stop words.
	 * We use a map to increase lookup performance.
	 */
	private static void constructStopWords() {
		String stopWords = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,"
						+ "but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,"
						+ "have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,"
						+ "me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,"
						+ "say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,"
						+ "to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,"
						+ "yet,you,your,q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m";

		// create the stop words hash
		String[] words = stopWords.split(",");
		if (words != null && words.length > 0) {
			for (String w : words) {
				stopWordMap.put(w, w);
			}
		}
	}

	private static void parseDocument(File file) throws IOException {
		// maps words to the times they appear
		Map<String, Integer> wordToCount = new HashMap<String, Integer>();
		// read from this file
		BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
		
		
		// initial line
		String line = null;
		while ((line = reader.readLine()) != null) {
			// get each word
			// String[] parts = line.split("\\s");
			String[] parts = line.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");

			// for each word in the document, if it's a stop word
			for (String part : parts)
			{
				// remove whitespace, and lowercase it
				part = part.trim().toLowerCase();

				// if not a stop word
				if (!stopWordMap.containsKey(part) && !part.equalsIgnoreCase("")) 
				{

					

					// add to word counter
					if (wordToCount.containsKey(part))
						wordToCount.put(part, wordToCount.get(part) + 1);
					else
						wordToCount.put(part, 1);
				}
			}
		}
		Document d = new Document(file.getName().toString(), wordToCount);
		documents.add(d);
		

		reader.close();
	}

	private static void writeToMap() throws IOException 
	{
		

		// build all the terms first
		List<String> totalTerms = new ArrayList<String>();
		// for each file
		for (Document d : documents) 
		{
			// for each term in that file
			for (String term : d.termFrequency.keySet()) 
			{

				// if not in total terms already
				if (!totalTerms.contains(term))
				{
					totalTerms.add(term);
				}
			}
		}

		
		for (String term : totalTerms)
		{
			
			for (Document d : documents)
			{
				//Not IMP
					Map<String, Integer> map = d.termFrequency;
					Integer amount = 0;
					if (map.containsKey(term))
					{
						amount = map.get(term);
					}
					//Not IMP
				if( !r.containsKey(term) )
				{
					r.put(term, new LinkedHashMap<String,Integer>());
				}
				r.get(term).put(d.documentName.replaceAll(".txt", ""),amount);
			}
		}
	}
	
	private static double computeInvDocFreq(String term, int occurs) {
		if (occurs == 0)
			return 0;

		double idf = Math.log(((double) documents.size()) / ((double) numOfDocs(term)));
		double tfidf = occurs * idf;

		return tfidf;
	}

	
	private static int numOfDocs(String term) {
		int freq = 0;
		for (Document d : documents) {
			if (d.termFrequency.containsKey(term)) {
				freq++;
			}
		}
		return freq;
	}
	public void set(File[] f1 ){
		f3 = f1;
	}
}