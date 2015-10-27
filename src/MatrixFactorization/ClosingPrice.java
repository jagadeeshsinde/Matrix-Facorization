package MatrixFactorization;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class ClosingPrice 
{
	private static String getstock;
	private static String getdate;
	public static void  calculate(Map<String,Double[]> qi1,Map<String,Double[]> pu ,RecMF rec,LinkedHashMap<String,LinkedHashMap<String,Double>> c) 
	{
		Double cp;
		//Scanner sc = new Scanner(System.in);
		//System.out.println("Enter your stock:");
		String stock = getStock();
		//System.out.println("Enter your day:");
		String day= getDate();
		Double d = rec.r_hat(stock, day);
		System.out.println(d);
		System.out.println(Math.exp(d));
		cp=Math.exp(d);
		
		
			
			String[] array = day.split("-");
			Integer date=Integer.parseInt(array[0]);
			Integer month=Integer.parseInt(array[1]);
			Integer year=Integer.parseInt(array[2]);
		int date1;
		date1 = date-1;
		String s = String.valueOf(date1);
		System.out.print(s.length());
		StringBuilder sb = new StringBuilder();
		if(s.length()==1){
		sb.append('0');}
		sb.append(date1);
		sb.append('-');
		sb.append('0');
		sb.append(month);
		sb.append('-');
		sb.append(year);
		//sb.append('-');
	
		String day1 =sb.toString();
		System.out.println(day1);
		System.out.println("Previous day Closing Price:"+c.get(stock).get(day1));
		Double prevcl = c.get(stock).get(day1);
		System.out.println("Closing price of "+day+" is:"+cp*prevcl);
		
	}
	public void setStock( String s){
		getstock = s;
	}
	public static String getStock(){
		return getstock;
	}
	public void setDate( String d){
		getdate = d;
	}
	public static String getDate(){
		return getdate;
	}
}