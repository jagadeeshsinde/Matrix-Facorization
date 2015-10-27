package DataPreprocessing;
import jaggu.Gui;

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
import java.util.Set;

import javax.swing.JFrame;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sun.org.apache.bcel.internal.generic.GOTO;


public class Original {
	public File[] f2 = null;
	//public File filetop = null;
	static ArrayList<String> listwords  = new ArrayList<>();
	 private LinkedHashMap<String,LinkedHashMap<String,Double>> rmon = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	 private LinkedHashMap<String,LinkedHashMap<String,Double>> rev = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	 static LinkedHashMap<String,LinkedHashMap<String,Double>> ZscrMap = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	private LinkedHashMap<String, Double> just = new LinkedHashMap<>();
	 
	 public static void main(String[] args) throws IOException {
	Gui jag = new Gui();
		
		jag.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jag.setSize(500, 500);
		jag.setVisible(true);
		
	}
	@SuppressWarnings("null")
	public void inputMongo() throws IOException
	{
		MongoClient client = new MongoClient();
		DB db = client.getDB("ramesh");
		DBCollection col1 = db.getCollection("ram");
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//System.out.print("Enter Input Path :");
		/*Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		String[] inputParts = input.split("(?<!\\\\)\\s+");*/

		
		List<File> allFiles = new ArrayList<File>();
		//Reading RAW TEXT NEWS ARTICLES
		//for (String i : inputParts)
		int g=0;
		while(g<f2.length)
		{
			File f = new File(f2[g].getPath());
			allFiles.add(f);
			g++;
		}
		for (File file : allFiles)
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = br.readLine()) != null) 
			{
				map.put("name", file.getName().replaceAll(".txt", ""));
				map.put("title", line);
				col1.insert(new BasicDBObject(map));
			}	
		}		
		DBCursor cursor = col1.find();
		
		java.util.List<DBObject> list = cursor.toArray();
		ArrayList<String> dates  = new ArrayList<>();
		
		//MongoDB usage
		//int i=0;
		col1.createIndex(new BasicDBObject("title", "text"));
		int j=0;
		while(j<listwords.size()){
			int i=0;
			dates.clear();
		while(i<cursor.length()){
			
			if(!dates.contains(list.get(i).get("name")))
			{
				dates.add((String) list.get(i).get("name"));
				BasicDBObject whereQuery = new BasicDBObject("$search",  listwords.get(j) );
				BasicDBObject textSearch = new BasicDBObject("$text", whereQuery);
				//BasicDBObject textScore =  new BasicDBObject("$meta", "textScore");
				DBCursor cursor1=col1.find(textSearch.append("name",list.get(i).get("name")));
				//System.out.println(list.get(i).get("name").toString()+"\t"+listwords.get(j)+"\t "  +col1.find(textSearch).count());
				if( !rmon.containsKey(list.get(i).get("name").toString()) )
				{
					rmon.put(list.get(i).get("name").toString(), new LinkedHashMap<String,Double>());
				}
				rmon.get(list.get(i).get("name").toString()).put(listwords.get(j),(double) col1.find(textSearch).count());
			}
		
			i++;
		}
		j++;}
		col1.drop();
		
		//placing obtained data into needed format
		
		for(String u : rmon.keySet()) 
		  {
			for(String y : rmon.get(u).keySet())
			{
				
				String word = y;
				String date = u;
				Double price = rmon.get(u).get(y);
				if( !rev.containsKey(word) )
				{
					rev.put(word, new LinkedHashMap<String,Double>());
				}
				rev.get(word).put(date,price);
				
			}
			
		  }
		//File f10 = new File("C:/Users/JAGADEESH/Documents/input/filenew10");
		//String filename10 = f10.getPath();
		//String newPath10 = filename10 + ".txt";
		//FileWriter writer1 = new FileWriter(newPath10);
		//writer1.append(String.valueOf( rev.keySet().size()));
		//for(String u10 : rev.keySet()) 
		// {
			//for(String y10 : rev.get(u10).keySet())
			//{
			//	writer1.append(u10);
			//	writer1.append(' ');
			//	writer1.append(y10);
			//	writer1.append(' ');
			//	writer1.append(String.valueOf(rev.get(u10).get(y10)));
			//	writer1.append("\n");
			//}
		// }
				/*String word = y;
				String date = u;
				Double price = rmon.get(u).get(y);
				if( !rev.containsKey(word) )
				{
					rev.put(word, new LinkedHashMap<String,Double>());
				}
				rev.get(word).put(date,price);*/
				
		//	}
			
		//  }
		//Z SCORE CALUCLATION
		Double  zscore = 0.0;
		ArrayList<Double> a = new ArrayList<>();
		for (String t : rev.keySet()) {
			for (String y : rev.get(t).keySet()) {
				a.add(rev.get(t).get(y));
				
			}
			
		}
		int c=0,d=0,e=0;
		int k =0;
		for (String t : rev.keySet()) {
		 k=rev.get(t).keySet().size();
		 }
	
		ArrayList<Double> mean = new ArrayList<>();
		ArrayList<Double> sd = new ArrayList<>();
		HashMap<String, Double> meanmap = new HashMap<>();
		//HashMap<String, Double> map = new HashMap<>();
	//CALCULATING MEAN,VARIANCE,STANDARD DEVIATION;
		for (String t : rev.keySet()) {
			Double sum=0.0,var=0.0;
			//System.out.println("valu 0f c:"+c);
			while(c<k){
				sum=sum+a.get(c);
				//var=var+(a.get(c)*a.get(c));
				c++;
			}
			
			mean.add(sum/rev.get(t).keySet().size());
			meanmap.put(t,(sum/rev.get(t).keySet().size()) );
			while(d<k){
				var = var + Math.pow((a.get(d) -(sum/rev.get(t).keySet().size())), 2);
				d++;
			}
			
			
			//System.out.println("Mean " +sd.get(0));
			var=var/(rev.get(t).keySet().size()-1);
			if (Math.sqrt(var) >= 3)
			just.put(t, Math.sqrt(var));
			sd.add(Math.sqrt(var));//Math.sqrt(var)
			//System.out.println(sd.get(0));
			k=k+rev.get(t).keySet().size();
		}
		for (String double1 : just.keySet()) {
			System.out.println(double1 +":"+just.get(double1));
			
		}
		System.out.print(just.keySet().size());
		
		//FINAL ZSCORE CALCULATION;
		int g1=0;
		for (String t : rev.keySet())
		{
			for (String y : rev.get(t).keySet()) 
			{
				if(just.containsKey(t))
				{
					zscore=(rev.get(t).get(y)-meanmap.get(t))/just.get(t);
					//System.out.print(zscore);
				//if (zscore.isNaN()){zscore = 0.0;}
					if( !ZscrMap.containsKey(t) )
					{
						ZscrMap.put(t, new LinkedHashMap<String,Double>());
					}
					else
					{
						ZscrMap.get(t).put(y,zscore);
					}
				}
			}
			
			
			
		}
		System.out.println(ZscrMap.get("banks"));
		File f = new File("C:/Users/JAGADEESH/Documents/input/final1111111111111111");
		String filename = f.getPath();
		String newPath = filename + ".txt";
		FileWriter writer = new FileWriter(newPath);
		for (String u : ZscrMap.keySet()) {
			
			for (String y : ZscrMap.get(u).keySet()) {
			//	System.out.println(y);
				
				writer.append(u);
				writer.append(' ');
				writer.append(y);
				writer.append(' ');
				Double amount = ZscrMap.get(u).get(y);
				
				/*if(amount.isNaN() ){
					
					amount=0.0;
				}*/
				writer.append(String.valueOf(amount));
				writer.append("\n");
				
			}
			
		}
		//return f;
	
		
	}
	public void set(File[] f1 ){
		f2 = f1;
	}
	public void setList(ArrayList<String> topword) {
		 listwords = topword;
		 
		
		
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> getZscore() throws IOException{
		
		return ZscrMap;
	}
	
	
}
