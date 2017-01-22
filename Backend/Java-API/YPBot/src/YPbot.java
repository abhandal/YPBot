
import java.net.*;
import java.io.*;
import java.util.*;

public class YPbot {
	
    public static void main(String[] args) throws Exception {
    	
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What are the keywords?: ");
        String keywords = keyboard.next();
        System.out.println("What is the location?: ");
        String location = keyboard.next();
    	URL yp = new URL("http://api.sandbox.yellowapi.com/FindBusiness/?what="+keywords+"&where="+location+"&pgLen=10&pg=1&sflag=Mc+Donald%27s&dist=1&fmt=JSON&lang=en&UID=montreal&apikey=q5w9347dg7dz3rr4tjyyqk48");
        URLConnection yc = yp.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        System.out.println("What's the user's id: ");
        int id = keyboard.nextInt();
        String filename = String.valueOf(id);
    	System.out.println("Creating a file named: " + filename);
        PrintWriter out= null;
    	try 
    	{
    		out = new PrintWriter(filename +".txt"); //The output is directed to a text file
    		while ((inputLine = in.readLine()) != null) 
            out.println(inputLine);
    		in.close();
    	} 
    	catch (FileNotFoundException e) //This will catch any error found if the File does not exist
    	{
    		e.printStackTrace();
    	}
    	finally 
    	{
    		out.close(); //This will close the printer
    	}
        
    }
    

}

