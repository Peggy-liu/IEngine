import java.util.ArrayList;

public class Algorithms {
	 protected void solve(String KB , String query){
		 
	 }
	 public ArrayList<String> ExtractSymbol(Logical_expression sentence) {
			ArrayList<String> result = new ArrayList<String>();
			if (sentence.symbol != null) {
				result.add(sentence.symbol);
				if(sentence.children != null){
					for (Logical_expression child : sentence.children) {
						result = concatenate(result, ExtractSymbol(child));
					}
				}
			} else {
				for (Logical_expression child : sentence.children) {
					result = concatenate(result, ExtractSymbol(child));
				}
			}
			return result;
		}
	 
	 protected ArrayList<String> concatenate(ArrayList<String> list1, ArrayList<String> list2) {
			ArrayList<String> result = new ArrayList<String>();
			list1.addAll(list2);
			for (String symbol : list1) {
				if (!result.contains(symbol)) {
					result.add(symbol);
				}
			}
			return result;
		}

}
