import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;

public class iengine {

	public static void main(String[] args) {
		// args contains:
		// [0] - method name
		// [1] - file name containing TELL and ASK

		if (args.length < 2) {
			System.out.println("Usage:  iengine <method> <filename>");
			System.exit(1);
		}

		String method = args[0];
		//create algorithm object 
		Algorithms thisMethod = null;
		// initialize algorithm indicated in the arguments
		if (method.equals("TT")) {
			thisMethod = new TruthTable();
		}
		else if (method.equals("FC")) {
			thisMethod  = new ForwardChaining();
		}
		else if (method.equals("BC")) {
			thisMethod = new BackwardChaining(); 
		}
        //if method cannot be identified, exit program
        else{
            System.out.println("Method "+ method + " has not been implemented yet. Methods are case sensitive.");
            System.exit(1);
        }
		//Read information to create knowledge base
		 HashMap<String, String> tellAndAsk = new HashMap<String, String>();
		 tellAndAsk = readFromFile(args[1]);  
		 //knowledge base and query from the hashmap
		 String tell = tellAndAsk.get("TELL");
		 String ask = tellAndAsk.get("ASK");
		 thisMethod.solve(tell, ask);
	}
	
	private static HashMap<String, String> readFromFile(String filename){
		HashMap<String, String> result = new HashMap<String, String>();    
        try{
            FileReader reader = new FileReader(filename);
			BufferedReader file = new BufferedReader(reader);
           //read the TELL
           String tell, ask;
           String temp = file.readLine();
           while(temp != null){
        	   
               if(temp.equals("TELL")){  	
                   tell = file.readLine();
                   result.put(temp,tell);
                   temp = file.readLine();
               }
               if(temp.equals("ASK")){
                   ask = file.readLine();
                   result.put(temp,ask);
                   temp = file.readLine();
               }
           }
           file.close();
           return result;
        }
        catch(FileNotFoundException ex){
            // The file didn't exist, show an error
			System.out.println("Error: File \"" + filename + "\" not found.");
			System.out.println("Please check the path to the file.");
			System.exit(1);
        }
        catch(IOException ex){
            System.out.println(
					"Error in reading \"" + filename + "\". Try closing it and programs that may be accessing it.");
			System.out.println("If you're accessing this file over a network, try making a local copy.");
			System.exit(1);
        }
        
        return null;

	}

}
