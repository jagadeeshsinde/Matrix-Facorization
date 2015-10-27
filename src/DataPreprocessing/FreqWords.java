package DataPreprocessing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FreqWords 

{
	private ArrayList<String> topword = new ArrayList<>();
	public void Freq(Map<String, Map<String,Integer>> r) throws IOException
	{
	
		Map<String, Double> test = new LinkedHashMap();
		Map<String, Double> last = new LinkedHashMap<>();
		Map<String, Double> last1 = new LinkedHashMap<>();
		ArrayList<Integer> arr = new ArrayList<>();
		Integer words = 500;
		int i=0;
		Double tot=0.0;
		
		for (String w : r.keySet())
		{
			for (String j : r.get(w).keySet() )
			{
				
				arr.add(r.get(w).get(j));
				
				
			}
			while(i<arr.size()) 
			{
					tot=tot+arr.get(i);
					i++;
			}
			test.put(w,tot );
			tot=0.0;
		}
		/*for (String integer : test.keySet()) {
			System.out.println(test.get(integer));
			
		}*/
	
		int z=0;
		
		Map<String, Double> map = sortByValues(test);
		
		for (String d : map.keySet())
		{
			if(last.size()>words)
			{
				last1.put(d, map.get(d));
				continue;
			}
			last.put(d, map.get(d));
		}
		
		for (String t : last.keySet())
		{
				//System.out.println(t +":"+last.get(t));
				topword.add(t);
		}
		Original org = new Original();
		org.setList(topword);
	}
	
	

private static HashMap sortByValues(Map<String, Double> test)
{ 
	List list = new LinkedList(test.entrySet());
	          
	          Collections.sort(list, new Comparator() 
	          {
	               public int compare(Object o1, Object o2) 
	               {
	                  return ((Comparable) ((Map.Entry) (o2)).getValue())
	                     .compareTo(((Map.Entry) (o1)).getValue());
	               }
	          });

	         
	          HashMap sortedHashMap = new LinkedHashMap();
	          
	          for (Iterator it = list.iterator(); it.hasNext();)
	          {
	                 Map.Entry entry = (Map.Entry) it.next();
	                 sortedHashMap.put(entry.getKey(), entry.getValue());
	          } 
	          return sortedHashMap;
	          
}
}

