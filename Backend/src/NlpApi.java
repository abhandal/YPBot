import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.*;
import javax.net.ssl.HttpsURLConnection;

public class NlpApi {
	private Gson gson;
	private String json;
	private String text;
	private NlpApi response;
	private String geoloc;
	private String userid;
	
	private String id;
	private String language;
	private NlpApi nlp;
	
	public NlpApi() {
		
	}
	public NlpApi(String text, String geoloc, String userid) {
		
		nlp = new NlpApi();
		if(geoloc.equals("") || geoloc.equals("Montréal"))
			this.geoloc = "montreal";
		else
			this.geoloc = geoloc;
		this.userid = userid;
		gson = new Gson();
		nlp.setText(text);
		nlp.language = "en";
		nlp.id = UUID.randomUUID().toString();
		
		setToJsonConversion(nlp);
		KeyPhrase();
		
	}
	
	public String getUserid() {
		return userid;
	}
	
	public String getGeoloc() {
		return geoloc;
	}
	
	private void setToJsonConversion(NlpApi obj) {
		json = gson.toJson(obj);
	}
	
	private void setText(String text) {
		this.text = text;
	}
	
	private String getText() {
		return text;
	}
	
	public String getJson() {
		return "{\"documents\": ["+json+"]}"; 
	}
	
	public void YPBot(String result, String geoloc, String userID) throws Exception {

		
		 URL url = new URL("http://api.sandbox.yellowapi.com/FindBusiness/?what=" +result+"&where="+geoloc+"&pgLen=10&pg=1&&dist=1&fmt=JSON&lang=en&UID=montreal&apikey=q5w9347dg7dz3rr4tjyyqk48");
		 String resurl = ""+userID;
		 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        PrintWriter out= null;
	        try 
	    	{
	        	out = createFile("/wamp64/www/pinit/Search/res/"+userID +".json"); //The output is directed to a text file	    		
	        	while ((line = in.readLine()) != null)
	            out.println(line);
	    		TweetBuffer.addToBuffer(resurl, resurl);
	        in.close();
	    	} 
	    	catch (FileNotFoundException e) //This will catch any error found if the File does not exist
	    	{
	    		//e.printStackTrace();
	    		System.out.println(e.getMessage());
	    	}
	        catch(IOException ioe){
	        	System.out.println(ioe.getMessage());
	        }
	    	finally 
	    	{
	    		out.close(); //This will close the printer
	    	}
	        
	        
	    }
	
	private static PrintWriter createFile(String fileName){
        try{
            // Creates a File object that allows you to work with files on the hardrive
            File listOfNames = new File(fileName);
            // FileWriter is used to write streams of characters to a file
            // BufferedWriter gathers a bunch of characters and then writes
            // them all at one time (Speeds up the Program)
            // PrintWriter is used to write characters to the console, file
            PrintWriter infoToWrite = new PrintWriter(
            new BufferedWriter(
                    new FileWriter(listOfNames)));
            return infoToWrite;
        }
        // You have to catch this when you call FileWriter
        catch(IOException e){
            System.out.println("An I/O Error Occurred");
            // Closes the program
            System.exit(0);
        }
        return null;
    }
	
	public void KeyPhrase(){
		 
	    try {
	      //Create connection
	    	URL url = new URL("https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/keyPhrases");
	      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Ocp-Apim-Subscription-Key", "981a640d05964de3ae39dbc1209a7b3f");
	      connection.setRequestProperty("Content-Type", "application/json");
	      connection.setRequestProperty("Accept", "application/json");
	      connection.setRequestProperty("Accept-Language", "application/json"); 
	      connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      System.out.println(getJson());
	      wr.writeBytes (getJson());
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      StringBuffer response = new StringBuffer(); 
	      String line;
	      while((line = br.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      br.close();
	      
	      JsonParser parser = new JsonParser();
	      JsonElement root = parser.parse(response.toString());

	      System.out.println("root = " + root);

	      JsonElement phases = root.getAsJsonObject().get("documents");
	      ArrayList jsonObjList = gson.fromJson(phases, ArrayList.class);
	      String json1 = (String) jsonObjList.toString();
	      String extract = json1.substring(14, json1.indexOf("], "));
	        String convertPlus = extract.replaceAll(", ", "+");
	      
	        if(!convertPlus.equals("")){
	        	String spaceFix = convertPlus.replaceAll(" ", "%20");
	        	YPBot(spaceFix, getGeoloc(), getUserid());
	        }

	    } catch (Exception e) {
	    	
	      e.printStackTrace();
	    }
	}
}
