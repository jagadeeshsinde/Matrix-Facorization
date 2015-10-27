package MatrixFactorization;
import java.util.LinkedHashMap;

public interface Rec {
	public void buildRecommender(LinkedHashMap<String,LinkedHashMap<String,Double>> r);
	public Double r_hat(String u, String i);
	public void buildRecommender1(LinkedHashMap<String, LinkedHashMap<String, Double>> w);
}
