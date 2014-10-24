package engine;

import java.io.File;
import java.util.List;

import beans.Tweet;
import main.MainClass;
import classifier.SentimentClassifier;
import forms.MainWindowForm;
import gui.TableHandler;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import utils.ApplicationUtils;
import utils.Constants;


public class TwitterQueryManager {
	private ConfigurationBuilder cb;
	private Twitter twitter;
	private static File file = null;
	private final MainWindowForm mwf;

	public TwitterQueryManager(final MainWindowForm mwf){
		this.mwf = mwf;
		
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(Constants.TWITTERCONSUMERKEY);
		cb.setOAuthConsumerSecret(Constants.TWITTERCONSUMERSECRET);
		cb.setOAuthAccessToken(Constants.TWITTERACCESSTOKEN);
		cb.setOAuthAccessTokenSecret(Constants.TWITTERACCESSTOKENSECRET);
		twitter = new TwitterFactory(cb.build()).getInstance();	    
	}

	public void performQuery(String inQuery, int amount){
		Query query = new Query(inQuery);
		query.setCount(amount);
		query.setLang(Constants.LANGUAGE[0]);
		
		String formatedDate = ApplicationUtils.createFormatDate(Constants.DATEFORMAT1);

		SentimentClassifier sentimentClassifier = new SentimentClassifier();
		
		int count = 0;
		QueryResult queryResult;
		try{
			do{
				queryResult = twitter.search(query);
				List<Status> tweets = queryResult.getTweets();
				for(int i=0; i < tweets.size() && count < amount; i++){
					count++;
					Status status = tweets.get(i);
					Tweet tweet = new Tweet();
					tweet.setId(status.getId());
					tweet.setUser(status.getUser().getScreenName());
					tweet.setTweet(status.getText());
					tweet.setDate(formatedDate);
					
					//imprime
					System.out.println(tweet.getId());
	            	System.out.println(tweet.getUser());
	            	System.out.println(tweet.getTweet());
	            	System.out.println(tweet.getDate()+"\n");
					
	            	//tweet.setRating(sentimentClassifier.classify(tweet.getTweet()));
	            	tweet.setRating(sentimentClassifier.openNlpClassify(tweet.getTweet()));
	            	MainClass.tweetList.add(tweet);
	            	TableHandler.addRow(new Object[]{tweet.getRating(),tweet.getTweet()});
				}
			} while((query = queryResult.nextQuery()) != null && count < amount);
		}
		catch(TwitterException e){
			System.err.println("== error TwitterQueryManager.performQuery(" + inQuery + "," + amount + ") " + e);
		}
	}
}
