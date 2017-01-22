package CoreBot;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

//DRIVER

public class TwitterBot {
	
	private static int maxTweets = 100; //Max tweets to retrieve
	private final static String HASHTAG = "#ypbotconuhacks"; //#ypbotconuhacks
	/**
	 * Retrieve tweets about a certain topic
	 */
	public static void main(String[] args){
		
		//Start TweetBuffer thread to delay tweet replies
		TweetBuffer tweetBuffer = new TweetBuffer();
		tweetBuffer.start();

		//Start search thread
		TweetSearch tweetSearch = new TweetSearch(HASHTAG, maxTweets);
		tweetSearch.start();

		/*
		//Send tweet
		Twitter twitter = TwitterFactory.getSingleton();
	    Status status = twitter.updateStatus("Hello World!");
	    System.out.println("Successfully updated the status to [" + status.getText() + "].");
		*/
	}
}