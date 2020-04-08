import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class TruthTable extends Algorithms {
	static int u = 0;

	@Override
	protected void solve(String KB, String query) {
		 Logical_expression HornKB = new Logical_expression(KB);
		 Logical_expression q = new Logical_expression(query);
		 boolean result =TT_Entails(HornKB, q);
		 if(result == true){
			 System.out.println("YES");
		 }
		 else{
			 System.out.println("NO");
		 }
	}

	public boolean TT_Entails(Logical_expression KB, Logical_expression query) {
		ArrayList<String> symbol1 = ExtractSymbol(KB);
		ArrayList<String> symbol2 = ExtractSymbol(query);
		ArrayList<String> symbols = concatenate(symbol1, symbol2);
		HashMap<String, Boolean> model = new HashMap<String, Boolean>();
		return TT_Check_All(KB, query, symbols, model);
	}

	public boolean TT_Check_All(Logical_expression KB, Logical_expression query, ArrayList<String> symbols,
			HashMap<String, Boolean> model){
		  
		if(symbols.isEmpty()){
		       if(PL_True(KB,model)){
		    	  
		    	   return PL_True(query, model);
		       }
		       else{
		    	   return true;
		       }
		    }
		else{
		       String p =symbols.get(0);
		       symbols.remove(0);
		       return (TT_Check_All(KB, query, symbols, Extend(p,true,model))) && (TT_Check_All(KB, query, symbols, Extend(p,false,model)));
		}
	}

	

	

	private boolean PL_True(Logical_expression sentence, HashMap<String, Boolean> model) {
		boolean result = true;
		if (sentence.symbol != null) {
			result = model.get(sentence.symbol);
		} 
		else {
			if (sentence.connective == "iif") {
				Logical_expression left = sentence.children.get(0);
				Logical_expression right = sentence.children.get(1);
				if (PL_True(left, model) == true && PL_True(right, model) == true) {
					result = true;
				} else {
					result = false;
				}
			}
			
			else if (sentence.connective == "if") {
				Logical_expression left = sentence.children.get(0);
				Logical_expression right = sentence.children.get(1);
				if (PL_True(left, model) == true && PL_True(right, model) == false) {
					result = false;
				} else {
					result = true;
				}
			}
			else if (sentence.connective == "or") {
				for (Logical_expression child : sentence.children) {
					if (PL_True(child, model) == true) {
						result = true;
					}
				}
				result = false;
			}
			else if (sentence.connective == "and") {
				for (Logical_expression child : sentence.children) {
					if (PL_True(child, model) == false) {
						result = false;
					}
				}
				result = true;
			}
			else if (sentence.connective == "not") {
				Logical_expression child = sentence.children.get(0);
				return(!PL_True(child, model));
			}
			
		}
		return result;
	}
	
	private HashMap<String,Boolean> Extend(String symbol, boolean value, HashMap<String, Boolean> model){
		HashMap<String,Boolean> result = model;
		result.put(symbol, value);
		
		return result;
	}

}
