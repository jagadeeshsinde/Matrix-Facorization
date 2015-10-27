package DataPreprocessing;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class LogReturn 
{
	static LinkedHashMap<String,LinkedHashMap<String,Double>> logret = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	static LinkedHashMap<String,LinkedHashMap<String,Double>> closingprice = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	private File f1;
	public void set(File f ){
		f1 = f;
	}

   public void inputlog() throws IOException
   {
	  
	   
	   BufferedReader br = new BufferedReader( new FileReader(f1) );
		String line;
		while ( (line = br.readLine()) != null )  
		{
			String[] array = line.split("\\s+");
			String stock = array[0];
			String date = array[1];
			Double price = Double.parseDouble(array[2]);
			
			if( !closingprice.containsKey(stock) )
			{
				closingprice.put(stock, new LinkedHashMap<String,Double>());
			}
			closingprice.get(stock).put(date,price);
			
			
		}
		ArrayList<Double> arr = new ArrayList<>();
		for(String u : closingprice.keySet()) 
		{
			for(String i : closingprice.get(u).keySet())
			{
				arr.add(closingprice.get(u).get(i));	
			}
		
		}
		
		String k = null;
		for(String u : closingprice.keySet()) 
		{
			
			for(String i : closingprice.get(u).keySet())
			{
				k=i;
				break;
			}
		}
		
		
		int a=0,b=-1;
		for(String u : closingprice.keySet()) 
			
		{
			for(String i :closingprice.get(u).keySet())
			{
				if(i.equalsIgnoreCase(k))
				{
					a++;
					b++;
					continue;
					
				}
				
				String stock = u;
				String date = i;
				Double logreturn = Math.log(arr.get(a)/arr.get(b));
				if( !logret.containsKey(stock) )
				{
					logret.put(stock, new LinkedHashMap<String,Double>());
				}
				logret.get(stock).put(date,logreturn);
				a++;
				b++;
			}
		
		}
		
		
	}
   public LinkedHashMap<String, LinkedHashMap<String, Double>> getlog(){
	  
	   return logret;
	   
   }
   public LinkedHashMap<String, LinkedHashMap<String, Double>> getclosingprice(){
	   return closingprice;
   }
}