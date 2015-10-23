package beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Tweet{
	private long id;
	private String user;
	private String tweet;
	private String tweetFormated;
	private Date date;
	private String rating;
	private List<String> term;
	private double score[];
	private Map <String, Double[]> predicates;
			
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet.replaceAll("\n", " ");
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public List<String> getTerm() {
		return term;
	}
	public void setTerm(List<String> term) {
		this.term = term;
	}
	public double[] getScore() {
		return score;
	}
	public void setScore(double[] score) {
		this.score = score;
	}
	
	public String getTweetFormated() {
		return tweetFormated;
	}
	public void setTweetFormated(String tweetFormated) {
		this.tweetFormated = tweetFormated.replaceAll("\n", " ");
	}
	public Map<String, Double[]> getPredicates() {
		return predicates;
	}
	public void setPredicates(Map<String, Double[]> predicates) {
		this.predicates = predicates;
	}
	public String toString()
	{	
		return "ID: " + this.getId() + "\nUser: " + this.getUser() + "\n" + this.getTweet() + "\n"
				+ "Date: " + this.getDate() + "\nClassificação: " + this.getRating();
	}


}
