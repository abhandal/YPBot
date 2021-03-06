
import java.util.ArrayList;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

// Thread that constantly searches for recent tweets
// For each tweets retrieved, create a new thread to process them.

public class TweetSearch extends Thread{
	private String query;
	private int max;
	private final int DELAY = 10000;
	
	private long maxId; //top
	private long sinceId; //bot
	
	public TweetSearch(String query, int max){
		this.query = query;
		this.max = max;
	}

	public void run() {
		ArrayList<Status> tweets = new ArrayList<Status>();
		Twitter twitter = new TwitterFactory().getInstance();
		Query search;
		QueryResult result; // To store results
		
		try {
			do {
				
				//System.out.println("Search for tweets...");
				
				// Modify filters below when needed
				search = new Query(this.query + " -filter:retweets -filter:replies");
				search.count(max).sinceId(sinceId); // Return # of tweet
				result = twitter.search(search);
				tweets = (ArrayList<Status>) result.getTweets();
				//If the current maxId IS NOT equal to search maxId. Keep going.
				//Otherwise, if maxId == maxId, it means that there are no more new tweets to process. Wait for more.
				if (this.maxId != result.getMaxId()) {
					this.maxId = result.getMaxId();
					System.out.println("Max ID is: " + this.maxId);
					System.out.println("Search done in " + result.getCompletedIn());

					// Manage results
					for (Status tweet : tweets) {
						// Useful vars
						long uID = tweet.getId();
						String text = tweet.getText();
						String user = tweet.getUser().getName();
						String screenName = tweet.getUser().getScreenName();
						String userLocation = tweet.getUser().getLocation();
						Place tweetPlace = null;
						GeoLocation tweetGeoLocation = null;
						if (tweet.getPlace() != null) { // Place can be null
							tweetPlace = tweet.getPlace();
						}
						if (tweet.getGeoLocation() != null) {
							tweetGeoLocation = tweet.getGeoLocation();
						}
						
						
				
						// Run thread
						//if(!tweet.isFavorited()){
							//twitter.createFavorite(tweet.getId());
							//System.out.println("FAVORITED");
							new TweetThread(uID, text, user, screenName, userLocation, tweetPlace, tweetGeoLocation).start();
						//}
					}
				} else {
					//System.out.println("No more tweets :(. I'm waiting for them...");
				}
				Thread.sleep(DELAY);
			} while (true); //(search = result.nextQuery()) != null
			//System.out.println("OutOfWhile. No more tweets");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
