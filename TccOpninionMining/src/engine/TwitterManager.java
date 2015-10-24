package engine;

import gui.StreamDialog;
import gui.TableHandler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import main.MainClass;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import utils.ApplicationUtils;
import utils.Constants;
import beans.Tweet;
import classifier.SentimentClassifier;
import engine.files.TxtHandler;

public class TwitterManager 
{
	private Configuration config;
	private SentimentClassifier sentimentClassifier;
	private Twitter twitter;
	private static TwitterStream twitterStream;
	
	
	public TwitterManager()
	{

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(Constants.TWITTERCONSUMERKEY);
		cb.setOAuthConsumerSecret(Constants.TWITTERCONSUMERSECRET);
		cb.setOAuthAccessToken(Constants.TWITTERACCESSTOKEN);
		cb.setOAuthAccessTokenSecret(Constants.TWITTERACCESSTOKENSECRET);
		config = cb.build();
		
		sentimentClassifier = new SentimentClassifier();
	}
	
	public void doQuery(String inQuery, int amount)
	{
		File file = TxtHandler.createTxt(Constants.PATH);
		twitter = new TwitterFactory(config).getInstance();
		Query query = new Query(inQuery);
		query.setCount(amount);
		query.setLang(Constants.LANGUAGE[0]);
		
		int count = 0;
		QueryResult queryResult;
		try
		{
			do
			{
				queryResult = twitter.search(query);
				List<Status> tweets = queryResult.getTweets();
				for(int i=0; i < tweets.size() && count < amount; i++)
				{
					count++;
					Status status = tweets.get(i);
					
					Tweet tweet = createTweet(status);
	            	TxtHandler.writeIntoFile(file, tweet.getRating() + " " + ApplicationUtils.formatTweetBeforeSave(tweet.getTweet()));
	            	MainClass.tweetList.add(tweet); 
	            	
	            	System.out.println(tweet.toString());
	            	System.out.println(Arrays.toString(tweet.getScore()));
				}
			} while((query = queryResult.nextQuery()) != null && count < amount);
		}
		catch(TwitterException e)
		{
			System.err.println("== error TwitterManager.performQuery(" + inQuery + "," + amount + ") " + e);
		}
	}
	
	public void startListener(String[]keywords)
	{
		twitterStream = new TwitterStreamFactory(config).getInstance();
		StatusListener listener = new StatusListener() 
		{
		

            @Override
            public void onStatus(Status status) 
            {	
            	Tweet tweet = createTweet(status);
            	MainClass.tweetList.add(tweet);
            	TableHandler.addRow(new Object[]{tweet.getRating(),tweet.getTweet()});
            	StreamDialog.textArea.setText(StreamDialog.textArea.getText()+"\n"+tweet.getTweet());
            	System.out.println(tweet.toString());	
            	System.out.println(tweet.getScore());
            }
        	
            @Override
            public void onException(Exception arg0) {}

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {}

            @Override
            public void onScrubGeo(long arg0, long arg1) {}

            @Override
            public void onTrackLimitationNotice(int arg0) {}

			@Override
			public void onStallWarning(StallWarning arg0) {}
        };
        
        FilterQuery fq = new FilterQuery();

	    fq.track(keywords);
	    fq.language(Constants.LANGUAGE);

	    twitterStream.addListener(listener);
	    twitterStream.filter(fq);

	    StreamDialog.lblNewLabel.setText("Conectando...");
	    twitterStream.addConnectionLifeCycleListener(new ConnectionLifeCycleListener() {
			
			@Override
			public void onDisconnect() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConnect() {
				StreamDialog.lblNewLabel.setText("Conectado");
				StreamDialog.btn_stop.setEnabled(true);
			}
			
			@Override
			public void onCleanUp() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public static void stopListener()
	{
		System.out.println("Shutdown");
	    twitterStream.shutdown();
	}
	
	private Tweet createTweet(Status status)
	{

		
		Tweet tweet = new Tweet();
		tweet.setId(status.getId());
    	tweet.setUser(status.getUser().getScreenName());
    	tweet.setTweetFormated(ApplicationUtils.getTweetFormated(status.getText()));
    	tweet.setTweet(status.getText());
    	tweet.setDate(ApplicationUtils.createFormatDate(status.getCreatedAt()));
    	System.out.println(ApplicationUtils.createFormatDate(status.getCreatedAt()));
    	tweet = sentimentClassifier.openNlpClassify(tweet);
    	tweet.setTerm(ApplicationUtils.getTerm(tweet));
    	
		return tweet;
	}
}
