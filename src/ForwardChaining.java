import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ForwardChaining extends Algorithms {
	LinkedList<String> searched = new LinkedList<String>();
	
	
	@Override
	protected void solve(String KB, String query) {
		boolean result = PL_FC_Entails(KB, query);
		if(result==true){
			System.out.println("Yes: "+ searched);
		}
		else{
			System.out.println("No");
		}
		
	}

	public boolean PL_FC_Entails(String kb, String q) {
		Logical_expression HornKB = new Logical_expression(kb);
		Logical_expression query = new Logical_expression(q);
		HashMap<String, Integer> count = initCount(kb);
		HashMap<String, Boolean> inferred = initInfer(HornKB, query);
		LinkedList<String> agenda = initAgenda(kb);
		
		
		while(agenda.size()>0){
			String premise = agenda.pop();
			searched.add(premise);
			if(premise.equals(q)){
				return true;
			}
			if(inferred.get(premise)==false){
				inferred.put(premise, true);
				for (HashMap.Entry<String, Integer> entry : count.entrySet()) {
				    String clause = entry.getKey();
				    int value = entry.getValue();
				    if(clause.contains(premise) && value>0){
				    	value = value-1;
				    	count.put(clause, value);
				    	if(value==0){
				    		String[] temp = clause.split("=>");
				    		String conclusion = temp[1];
				    		agenda.addLast(conclusion);
				    		
				    	}
				    }
				}
			}
			
		}
	return false;

	}
	
	private LinkedList<String> initAgenda(String kb) {
		LinkedList<String> result = new LinkedList<String>();
		if (kb.contains(";")) {
			String[] temp = kb.split(";");
			for (String i : temp) {
				if (!i.contains("=>")) {
					result.add(i);
				}
			}
		}
		return result;
	}
	

	private HashMap<String, Integer> initCount(String kb) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		int val = 0;
		if (kb.contains(";")) {
			String[] temp = kb.split(";");
			for (String i : temp) {
				if (i.contains("=>")) {
					String[] clause = i.split("=>");
					if (clause[0] != null) {
						if (clause[0].contains("&")) {
							String[] array = clause[0].split("&");
							val = array.length;
						} else {
							val = 1;
						}
					}
					result.put(i, val);
				} 
				else {
					val = 0;
					result.put(i, val);
				}
			}
		}
		return result;
	}
	
	private HashMap<String, Boolean> initInfer(Logical_expression kb, Logical_expression query){
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        ArrayList<String> symbol1 = ExtractSymbol(kb);
        ArrayList<String> symbol2 = ExtractSymbol(query);
        ArrayList<String> symbols = concatenate(symbol1, symbol2);
        for(String i:symbols){
        	result.put(i, false);
        }
        return result;
}

}
