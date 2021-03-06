

import java.text.Normalizer;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

public class TweetThread extends Thread{

	private long uID;
	private String text;
	private String user;
	private String screenName;
	private String userLocation;
	private Place tweetPlace;
	private GeoLocation tweetGeoLocation;
	
	public TweetThread(long uID, String text, String user, String screenName, String userLocation, Place tweetPlace, GeoLocation tweetGeoLocation){
		this.uID = uID;
		this.text = text.toLowerCase();
		this.user = user;
		this.screenName = screenName;
		this.userLocation = userLocation;
		this.tweetPlace = tweetPlace;
		this.tweetGeoLocation = tweetGeoLocation;
	}
	public void run(){	
		//Remove the # from the text
		String noHash = this.text.replaceAll("#ypbotconuhacks", "");
		
		
		//Set correct tweet location
		String place = this.userLocation; //By default, send location from profile.
		if(this.tweetPlace != null){ //Otherwise overwrite with tweet location.
			place = this.tweetPlace.getName();
		} 
		if(this.tweetGeoLocation != null){ //Coordinates takes priority
			double lat = this.tweetGeoLocation.getLatitude();
			double lon = this.tweetGeoLocation.getLongitude();
			place = "<" + lat + ">,<" + lon + ">"; //<lat>,<lon> format for YP API
		}

		//Send everything to keyword extraction.
		//Send to buffer first so it gets delayed.
		String[] arrayElem = {noHash, place, this.screenName};
		TweetBuffer.addToAPIBuffer(arrayElem);
	}
	
}
