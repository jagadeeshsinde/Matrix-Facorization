package MatrixFactorization;
import java.util.ArrayList;
import java.util.Map;

import Jama.Matrix;


public class SpectralNorm {
	public static Double cal(Map<String,Double[]> qi,RecMF rec){
		
		ArrayList<Double> wd=new ArrayList<>();
		for(String u : qi.keySet()){
			int f=0;
			while(f<rec.getF()){
				wd.add(qi.get(u)[f]);
				f++;
				}
		}
		//System.out.print("Hello"+wd.get(3));
		double[][] r1 = new double[rec.getWord()][rec.getF()];
		//Double[][] s1 = new Double[rec.getWord()][rec.getF()];
		Integer k=0;
			for(int i = 0 ; i<rec.getWord();i++){
			for( int j =0 ; j< rec.getF();j++){
				r1[i][j] = wd.get(k);
				//System.out.print("Hello"+r[i][j]);
				k++;
			}
		}
		Matrix B = new Matrix(r1);
		return B.norm2();
		/*for(int i = 0 ; i<rec.getWord();i++){
			for( int j =0 ; j< rec.getF();j++){
				s1[j][i] = wd.get(e);
				e++;
			}
		}
		//System.out.println(r1[0][1]);
	//	System.out.print(s1[0][1]);
		
		Double sum1=0.0;
		double[][] mul1 = new double[rec.getF()][rec.getF()];
		
		   for ( int c = 0 ; c < rec.getF() ; c++ )
	       {
	          for ( int d = 0 ; d < rec.getF() ; d++ )
	          {   
	             for ( int f = 0 ; f< rec.getWord() ; f++ )
	             {
	                sum1 = sum1 + s1[c][f]*r1[f][d];
	             }

	             mul1[c][d] = sum1;
	             sum1 = 0.0;
	          }
	       }
		   //System.out.print(mul1[0][1]);
		   
		/*   double[][] array = {{1,2,3},{4,5,6},{7,8,10}};
		   Matrix B = new Matrix(array);
		   System.out.println("B norm : "+B.norm1());*/
		  /* Matrix A = new Matrix(mul1);
		   System.out.println("Trace is : "+A.trace());
		   System.out.print(A.norm2());
		return 10.0;*/
	}
}
