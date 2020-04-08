import java.util.ArrayList;
import java.util.LinkedList;

public class BackwardChaining extends Algorithms {
	public static LinkedList<String> agenda = new LinkedList<String>();
	public static ArrayList<String> facts = new ArrayList<String>();
	public static ArrayList<String> clauses = new ArrayList<String>();
	public static LinkedList<String> entailed = new LinkedList<String>();

	@Override
	protected void solve(String KB, String query) {
		init(KB, query);
		boolean result = PL_BC_Entails(KB, query);
		if(result==true){
			System.out.println("Yes: "+ entailed);
		}
		else{
			System.out.println("No");
		}
	}

	public boolean PL_BC_Entails(String kb, String q) {

		while (agenda.size() > 0) {
			String temp = agenda.pop();
			entailed.add(temp);
			if (!facts.contains(temp)) {
				ArrayList<String> p = new ArrayList<String>();
				for (String clause : clauses) {
					if (conclusionContains(clause, temp)) {
						ArrayList<String> temp1 = getPremises(clause);
						for (String t : temp1) {
							// add the symbols to a temp array
							p.add(t);
						}
					}
				}
				if (p.size() == 0) {
					return false;
				} else {
					// there are symbols so check for previously processed ones
					// and add to agenda
					for (int i = 0; i < p.size(); i++) {
						if (!entailed.contains(p.get(i)))
							agenda.addLast(p.get(i));
					}

				}
			}
		}
		return true;
	}

	private void init(String kb, String q) {
		agenda.addLast(q);
		String[] sentences = kb.split(";");
		for (String i : sentences) {
			if (!i.contains("=>")) {
				// add the facts
				facts.add(i);
			} else {
				// add the premises
				clauses.add(i);
			}
		}
	}

	// method that returns the conjuncts contained in a clause
	public static ArrayList<String> getPremises(String clause) {
		// get the premise
		String premise = clause.split("=>")[0];
		ArrayList<String> temp = new ArrayList<String>();
		String[] conjuncts = premise.split("&");
		// for each conjunct
		for (int i = 0; i < conjuncts.length; i++) {
			if (!agenda.contains(conjuncts[i]))
				temp.add(conjuncts[i]);
		}
		return temp;
	}

	// method which checks if c appears in the conclusion of a given clause
	// input : clause, c
	// output : true if c is in the conclusion of clause
	public static boolean conclusionContains(String clause, String c) {
		String conclusion = clause.split("=>")[1];
		if (conclusion.equals(c))
			return true;
		else
			return false;

	}

}
