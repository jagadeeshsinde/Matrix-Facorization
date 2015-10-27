package MatrixFactorization;
import java.awt.Container;
import java.io.File;
import java.io.FileReader;
import java.sql.PseudoColumnUsage;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.openimaj.math.matrix.MatlibMatrixUtils;
import org.openimaj.math.matrix.PseudoInverse;

import DataPreprocessing.LogReturn;
import DataPreprocessing.Original;
import Jama.Matrix;


public class RecMF extends JFrame implements Rec {

	//user_item_rating_LinkedHashMap
	LinkedHashMap<String,LinkedHashMap<String,Double>> r;
	LinkedHashMap<String,LinkedHashMap<String,Double>> w;
	Set<String> stock, word,date;
	
	Double mu; //mean
	
	LinkedHashMap<String,Double> bu = new LinkedHashMap<String,Double>();
	LinkedHashMap<String,Double> bi = new LinkedHashMap<String,Double>();
	
	/* original setting
	int F = 20; //number of factors
	*/
	int F = 3; //number of factors
	
	static LinkedHashMap<String,Double[]> pu = new LinkedHashMap<String,Double[]>();
	static LinkedHashMap<String,Double[]> qi = new LinkedHashMap<String,Double[]>();
	static LinkedHashMap<String,Double[]> qi1 = new LinkedHashMap<String,Double[]>();
	
	Double gamma = 0.005;
	double gamma1 = 0.005;
	double lambda1 = 0.02;
	
	/* original settings
	Double lambda_bu = 0.02;
	Double lambda_bi = 0.02;
	Double lambda_pu = 0.05;
	Double lambda_qi = 0.05;
	*/
	
	Double lambda_bu = 0.02;
	Double lambda_bi = 0.02;
	Double lambda_pu = 0.2;
	 static Double lambda_qi = 0.2;
	 
	
	int iternum =45;  //70

	public void buildRecommender(LinkedHashMap<String,LinkedHashMap<String,Double>> r) {
		this.r = r;
		
		initialize();
		//this.mu = Util.computeMu(r); // Checkpoint: mu - 0.5
		//computeBuBiPuQi();
	}
	void initialize() {
		this.date = Util.r_u_i_TO_i(r);
		this.stock = Util.r_u_i_TO_u(r);
		
		for(String u : stock) 
		{
			bu.put(u, 0.0);
		}
				
	//	for(String i : date) bi.put(i, 0.0);
		
	
		
		for(String u : stock) {
			Double[] vec = new Double[F];
			for(int f=0; f<getF(); f++) {
				vec[f] = Math.abs(Math.random()/1.0);
			}
				
				
			pu.put(u, vec);
			
//			System.out.println(pu.get(u)[2]);
		}
		System.out.println("Length of pu: "+pu.size());
		
	}
	public void buildRecommender1(LinkedHashMap<String,LinkedHashMap<String,Double>> w) {
		this.w = w;
		initialize1();
		//this.mu = Util.computeMu(r); // Checkpoint: mu - 0.5
		//computeBuBiPuQi();
	}
	void initialize1() {
		//this.date = Util.r_u_i_TO_i(r);
		this.word = Util.r_u_i_TO_u(w);
		
		/*for(String u : stock) 
		{
			bu.put(u, 0.0);
		}
			*/	
		for(String i : word)
		{
			bi.put(i, 0.0);
		}
		
	/*	for(String u : stock) {
			Double[] vec = new Double[F];
			for(int f=0; f<getF(); f++) 
				vec[f] = Math.random()/1.0;
				
			pu.put(u, vec);
		}
		System.out.println("Length of pu: "+pu.size());*/
		for(String i : word)
		{
			Double[] vec = new Double[F];
			for(int f=0; f<getF(); f++)
			{
				vec[f] = (Math.random()/1.0);
			}
			qi.put(i, vec);
			//System.out.print("hello"+qi.get(i)[0]);
			
		}
		System.out.println("Length of qi: "+qi.size());
	}
	
	public void calclateWY(LinkedHashMap<String,Double[]> qi , LinkedHashMap<String,LinkedHashMap<String,Double>> w){
		//Double[][] a = new Double[100][100];
		
		ArrayList<Double> q = new ArrayList<>();
		ArrayList<Double> p = new ArrayList<>();
		ArrayList<Double> q1=new ArrayList<>();
		
		System.out.println("Word Vector is :"+w.keySet());
		
		for(String u : w.keySet()) {
			
			for(String i : w.get(u).keySet()) {
				//System.out.print(w.get(u).get(i)+"\t");
				//System.out.println(u+"\t"+i+"\t"+w.get(u).get(i));
				q.add(w.get(u).get(i));
				
			}
			
			//System.out.print("\n");
			
		}
		//System.out.println("Array Element is :"+q.get(1));
		//System.out.println("Vector of News Latent:");
		
		Integer t=0;
		//System.out.print(qi.get("s")[1]);
		//for(int f=0;f<getF();f++)
		//{
			for(String u : word)
			{
			//for(String i : w.get(u).keySet()) {
				int f=0;
				//System.out.print(w.get(u).get(i)+"\t");
				//System.out.print(qi.get(u)[t]+"\t");
				//System.out.print(qi.get(u)[1]+"\t");
				while(f<getF()){
				p.add(qi.get(u)[f]);
				f++;
				}
				//r[0][0]+"\t");
				//p.add(qi.get(u)[1]);
				//p.add(qi.get(u)[2]);
			}
			System.out.print("\n");
			t++;
		//}
		//}
		//	System.out.println("Array Element is :"+p.get(1));
		//System.out.println("Product is : "+p.size());
		
	Double[][] r = new Double[getWord()][getF()];
	Double[][] s = new Double[getWord()][getDays()];
	Integer k=0,e=0;
	for(int i = 0 ; i<getWord();i++){
		for( int j =0 ; j< getF();j++){
			r[i][j] = p.get(k);
			//System.out.print("Hello"+r[i][j]);
			k++;
		}
	}
	//System.out.println(r[1][1]);
	for(int i = 0 ; i<getWord();i++){
		for( int j =0 ; j< getDays();j++){
			s[i][j] = q.get(e);
			e++;
		}
	}
	/*for (int  c = 0 ; c < 5 ; c++ )
   	{
	   for ( int d = 0 ; d < 3 ; d++ )
	   {
		   System.out.print(r[c][d]+"\t");
   		}
	   System.out.print("\n");
   	}
	 for (int  c = 0 ; c < 5 ; c++ )
	   	{
		   for ( int d = 0 ; d < 6 ; d++ )
		   {
			   System.out.print(s[c][d]+"\t");
	   		}
		   System.out.print("\n");
	   	}*/
	
	   Double sum=0.0;
		Double[][] mul = new Double[getDays()][getF()];
		
		   for ( int c = 0 ; c < getDays() ; c++ )
	       {
	          for ( int d = 0 ; d < getF() ; d++ )
	          {   
	             for ( int f = 0 ; f< getWord() ; f++ )
	             {
	                sum = sum + s[f][c]*r[f][d];
	             }

	             mul[c][d] = sum;
	             sum = 0.0;
	          }
	       }
	  System.out.println("The Product Word Latent & Word Vector :");
		   for (int  c = 0 ; c < getDays() ; c++ )
		   	{
			   for ( int d = 0 ; d < getF() ; d++ )
			   {
				   System.out.print(mul[c][d]+"\t");
				   q1.add(mul[c][d]);
				   
		   		}
			   System.out.print("\n");
		   	}
		  // Double[] vec = new Double[100];  
		
		   
		    int i=0,n=0;
		 
			for(String u : w.keySet()) 
			  {
				for(String y : w.get(u).keySet())
				{
					Double[] vec = new Double[F];
					for(int f=0; f<getF(); f++)
					{
						vec[f] = q1.get(n);
						n++;
					}
				qi1.put(y, vec);
				}
				break;
			  }
			
				
	}
	//predict rating
	public Double r_hat(String u, String i) {
		Double bu_u = 0.0, bi_i = 0.0, pu_qi=0.0;
		if ( bu.containsKey(u) ) bu_u = bu.get(u);			
		if ( bi.containsKey(i) ) bi_i = bi.get(i);
		if ( pu.containsKey(u) && qi1.containsKey(i) ){ 
			pu_qi = Util.vecvecprod(pu.get(u), qi1.get(i));
			
		
		}
		else
			pu_qi = mu;
	
		return pu_qi;
		//return mu;
	}
	
	
	//error
	Double e(String u, String i) {
		return r.get(u).get(i) - r_hat(u,i);
	}
	
	void computeBuBiPuQi() {
	//	for(int iter=0; iter<this.iternum; iter++) {
		ArrayList<Double> rlist = new ArrayList<>();
		ArrayList<Double> ylist = new ArrayList<>();
		ArrayList<Double> ulist = new ArrayList<>();
		ArrayList<Double> wlist = new ArrayList<>();
		double[][] rmat = new double[getStocks()][getDays()];
		double[][] ymat = new double[getWord()][getDays()];
		double[][] umat = new double[getStocks()][getF()];
		double[][] wmat = new double[getWord()][getF()];
		Integer k=0,e=0,m=0,n=0;

		for(String u : r.keySet()) {
			for(String i : r.get(u).keySet()) {
				
				rlist.add(r.get(u).get(i));
				
			}
		}
		for(int i = 0 ; i<getStocks();i++){
			for( int j =0 ; j< getDays();j++){
				rmat[i][j] = rlist.get(e);
				e++;
			}
		}
		for(String u : w.keySet()) {
			for(String i : w.get(u).keySet()) {
				
				ylist.add(w.get(u).get(i));
				
			}
		}
		for(int i = 0 ; i<getWord();i++){
			for( int j =0 ; j< getDays();j++){
				ymat[i][j] = ylist.get(k);
				k++;
			}
		}
		for(String u : stock)
		{
		
			int f=0;
			
			while(f<getF()){
			ulist.add(pu.get(u)[f]);
			f++;
			}
			
		}
		for(int i = 0 ; i<getStocks();i++){
			for( int j =0 ; j< getF();j++){
				umat[i][j] = ulist.get(m);
				
				m++;
			}
		}
		for(String u : word)
		{
		
			int f=0;
			
			while(f<getF()){
			wlist.add(qi.get(u)[f]);
			f++;
			}
			
		}
		for(int i = 0 ; i<getWord();i++){
			for( int j =0 ; j< getF();j++){
				wmat[i][j] = wlist.get(n);
				
				n++;
			}
		}
		Matrix R = new Matrix(rmat);
		Matrix Y = new Matrix(ymat);
		Matrix U = new Matrix(umat);
		Matrix W = new Matrix(wmat);
		W = W.transpose();
		Matrix A = new Matrix(umat);
		Matrix B = new Matrix(wmat);
		
		
		B = B.transpose();
		Matrix C = new Matrix(umat);
		Matrix D = new Matrix(wmat);
		D = D.transpose();
		Matrix I = Matrix.identity(getF(), getF());
	
		
		
		for(int iter=0; iter<this.iternum; iter++) 
		{
			
			A = (R.times(Y.transpose()).times(B.transpose()).minus(C).plus(U.times(0.5))).times((B.times(Y).times(Y.transpose()).times(B.transpose()).plus(I.times(0.5))).inverse());	
			B = ((A.transpose().times(R).times(Y.transpose()).minus(D)).times(2)).plus(W);
			U = (A.plus(C.times(2)));
			//System.out.println(U.getRowDimension()+"before"+U.getColumnDimension());
			// U.print(6, 5);
			U = U.inverse().transpose();
			// U.print(6, 5);
			//	System.out.println(U.getRowDimension()+"before"+U.getColumnDimension());

			//	System.out.println(U.inverse().getRowDimension()+"after"+U.inverse().getColumnDimension());
			for (int valuej =0 ; valuej<getWord();valuej++)
			{
				Matrix Bj = B.getMatrix(0,getF()-1,valuej,valuej);
				Matrix Dj = D.getMatrix(0,getF()-1,valuej,valuej);
				Matrix V = Bj.plus(Dj.times(2));
				double modv = 0.0,pin = 0.0;
				for (int j =0; j < getF(); j++)
				{
					modv = modv+( V.get(j, 0)*V.get(j, 0));
						
				}
				modv = Math.sqrt(modv);
				pin = 1/(modv - (0.05/0.5));
				Matrix InW = V.times(pin).times(0.5);
				Matrix Wj = InW.times(1/((InW.norm2()-lambda1)/(InW.norm2() * 0.5)));
				W.setMatrix(0, getF()-1, valuej, valuej, Wj);
				
			}
			C = C.plus((A.minus(U)).times(0.5));
			D = D.plus((B.minus(W).times(0.5)));
			
		}
		W = W.transpose();
		//U.print(getStocks(), getF());
		ArrayList<Double> fpu = new ArrayList<>();
		ArrayList<Double> fqi = new ArrayList<>();
		for (int  c = 0 ; c < getStocks() ; c++ )
	   	{
		   for ( int d = 0 ; d < getF() ; d++ )
		   {
			   fpu.add(U.get(c, d));
		   }
		
	   	}
		//System.out.print("hiiiiiiiiiiii"+fpu.get(5));
		
		 int n1=0;
		 for(String u : stock) {
				Double[] vec = new Double[F];
				for(int f=0; f<getF(); f++) {
					//for user feature vector to be non negative
					vec[f] = Math.abs(fpu.get(n1));
					//vec[f] = fpu.get(n1);
					n1++;
				}
					
					
				pu.put(u, vec);
		 }
		
		 for (int  c = 0 ; c < getWord() ; c++ )
		   	{
			   for ( int d = 0 ; d < getF() ; d++ )
			   {
				   fqi.add(W.get(c, d));
			   }
			
		   	}
			
			
			 int w1=0;
			 for(String w : word) {
					Double[] vec = new Double[F];
					for(int f=0; f<getF(); f++) {
						vec[f] = fqi.get(w1);
						w1++;
					}
						
						
					qi.put(w, vec);
					
			 }
			
		
		}
	
	
	int getF() {return F;}
	int getWord(){
		
		Set s = w.keySet();
		return s.size();
	}
	int getDays(){
		
		Set s=	r.get("TCS").keySet();
		return s.size();
		
		
	}
	int getStocks(){
		Set s = r.keySet();
		return s.size();
	}
	void setF(int f) {F = f;}

	Double getLambda_bu() {return lambda_bu;}
	void setLambda_bu(Double lambda_bu) {this.lambda_bu = lambda_bu;}

	Double getLambda_bi() {return lambda_bi;}
	void setLambda_bi(Double lambda_bi) {this.lambda_bi = lambda_bi;}

	Double getLambda_pu() {return lambda_pu;}
	void setLambda_pu(Double lambda_pu) {this.lambda_pu = lambda_pu;}

	Double getLambda_qi() {return lambda_qi;}
	void setLambda_qi(Double lambda_qi) {this.lambda_qi = lambda_qi;}

	int getIternum() {return iternum;}
	void setIternum(int iternum) {this.iternum = iternum;}


	public void outputLatentFactors(){
		
		System.out.println("User Stock Factors");
		for(String u : pu.keySet()) {
			System.out.print(u);
			for(int f=0; f<F; f++) 
				System.out.print("\t"+pu.get(u)[f]);  // '\t' won't work
			System.out.println();
		}	
		
		System.out.println("Item News Factors");
		for(String i : qi.keySet()) {
			System.out.print(i);
			for(int f=0; f<F; f++) 
				System.out.print("\t"+qi.get(i)[f]);
			System.out.println();
		}
		
	System.out.println("News Articles Vectors");
		for(String i : w.keySet()) {
			System.out.print(i);
			for(String j : w.get(i).keySet()){
					System.out.print("\t"+w.get(i).get(j));
			}
					System.out.println();
		}
		//System.out.println(pu.entrySet());System.out.println(qi.entrySet());
			//System.out.println(qi1.get("15-04-2015")[0]);
		System.out.println("AFTER MULTIPLICATION");
		
		
		   for(String u : w.keySet()) 
			  {
				for(String y : w.get(u).keySet())
				{
					System.out.print(y);
					for(int f=0; f<F; f++) 
						System.out.print("\t"+qi1.get(y)[f]);
					System.out.println();
				
					//}
					//System.out.println("Inside"+y+"\t"+qi1.get(y)[0]);
					//System.out.println("Inside"+y+"\t"+qi1.get(y)[1]);
						
				}
				//System.out.println("Outside"+qi1.get("MATRIX")[0]);
					break;
			   }
		
	
		
		/*for(String i : qi1.keySet()) {
			System.out.print(i);
			for(int f=0; f<2; f++) 
				System.out.print("\t"+qi1.get(i)[f]);
			System.out.println();
		}*/
			
		
	}

	public static void inputMF() throws Exception {
		//File f = new File("C:/Users/JAGADEESH/Desktop/log.txt");
		//FileReader file1= new FileReader(f);
		//String file = f.getName();
		//System.out.print(file);
		
	
		LogReturn logreturn = new LogReturn();
		Original zscore = new Original();
		//File filename = new File("C:/Users/JAGADEESH/Documents/input/filenew");
		//String newname = filename.getName();
		//LinkedHashMap<String,LinkedHashMap<String,Double>> r = Util.readData("log.txt"); 
		LinkedHashMap<String,LinkedHashMap<String,Double>> r = logreturn.getlog();
		
		LinkedHashMap<String,LinkedHashMap<String,Double>> w = zscore.getZscore();
		
		LinkedHashMap<String,LinkedHashMap<String,Double>> c = logreturn.getclosingprice();

		RecMF rec = new RecMF();
		Util.Build(rec,r,w);
		//System.out.println(rec.getWord());
		//System.out.println(rec.getDays());
		//System.out.println(r.get("TCS").keySet());
		rec.computeBuBiPuQi();
		rec.calclateWY( qi, w);
		System.out.println("Frobenius="+Util.Frobenius(rec,r));
		rec.outputLatentFactors();
		ClosingPrice.calculate(qi1, pu,rec,c);
		double d=  (  lambda_qi*   SpectralNorm.cal(qi,rec))+Util.Frobenius(rec,r);
		System.out.println("Frobenius="+Util.Frobenius(rec,r));
		//System.out.print(d);
		//ClosingPrice.calculate(qi1, pu,rec,c);
		
		
	}
}
