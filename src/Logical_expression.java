import java.util.*;

public class Logical_expression {
	String symbol;
	String connective;
	ArrayList<Logical_expression> children = new ArrayList<Logical_expression>();

	public Logical_expression(String sentence) {
		// when there are more than one clause in the knowledge base
		//handle top level connective
		if (sentence.contains(";")) {
			// the first occurrence of ';'
			int index = sentence.indexOf(";");
			String remain = sentence.substring(index + 1);
			// the root of the tree
			String root = sentence.substring(0, index);
			String insideP = null;
			//if there is parenthesis in the sentence, process the parenthsis first
			if(root.contains("(")&&root.contains(")")){
				int index1 = root.indexOf("(");
				int index2 = root.indexOf(")");
				insideP = root.substring(index1+1, index2);
				root = root.replace(insideP, "");
				//System.out.println(root);
				//System.out.println(insideP);
			}
			if (root.contains("<=>")) {
				String[] pair = root.split("<=>");
				for (String clause : pair) {
					//System.out.println(clause);
					Logical_expression child = new Logical_expression(clause);
					if(child!=null){
						children.add(child);
					}
					
				}
				connective = "iif";
				symbol = null;
				
			}
			
			else if (root.contains("=>")) {
				String[] pair = root.split("=>");
				for (String clause : pair) {
					//System.out.println(clause);
					Logical_expression child = new Logical_expression(clause);
					if(child!=null){
						children.add(child);
					}
					
				}
				connective = "if";
				symbol = null;
			}
			
			else if (root.contains("||")) {
				String[] pair = root.split("||");
				for (String clause : pair) {
					//System.out.println(clause);
					Logical_expression child = new Logical_expression(clause);
					if(child!=null){
						children.add(child);
					}
					
				}
				connective = "or";
				symbol = null;
			}
			else if (root.contains("&")) {
				String[] pair = root.split("&");
				for (String clause : pair) {
					//System.out.println(clause);
					Logical_expression child = new Logical_expression(clause);
					if(child!=null){
						children.add(child);
					}
					
				}
				connective = "and";
				symbol = null;
			}
			else if (root.contains("~")) {
				int i = root.indexOf("~");
				String p = root.substring(i+1);
				//System.out.println(p);
				Logical_expression child = new Logical_expression(p);
				if(child!=null){
					children.add(child);
				}
				connective = "not";
				symbol = null;
			}
			
			// when the root is a single symbol
			else {
				connective = null;
				symbol = root;
			}
			children.add(new Logical_expression(remain));
		}
		// handle a sentence with one or more symbols or connectives
		else {
			if (sentence.contains("<=>")) {
				String[] pair = sentence.split("<=>");
				for (String clause : pair) {
					children.add(new Logical_expression(clause));
				}
				connective = "iif";
				symbol = null;
			}
			else if (sentence.contains("=>")) {
				String[] pair = sentence.split("=>");
				for (String clause : pair) {
					children.add(new Logical_expression(clause));
				}
				connective = "if";
				symbol = null;
			}
			else if (sentence.contains("||")) {
				String[] pair = sentence.split("\\|\\|");
				for (String clause : pair) {
					//System.out.println(clause);
					children.add(new Logical_expression(clause));
				}
				connective = "or";
				symbol = null;
			}
			// check for any '&'
			else if (sentence.contains("&")) {
				String[] temp = sentence.split("&");
				for (String i : temp) {
					//System.out.println(i);
					children.add(new Logical_expression(i));
				}
				connective = "and";
				symbol = null;
			}
			else if (sentence.contains("~")) {
				int i = sentence.indexOf("~");
				String p = sentence.substring(i+1);
				//System.out.println(p);
				Logical_expression child = new Logical_expression(p);
				if(child!=null){
					children.add(child);
				}
				connective = "not";
				symbol = null;
			}
			// when it is just a single symbol
			else {
				//System.out.println(sentence);
				connective = null;
				symbol = sentence;
				children= null;
			}
				
		}

	}

}
