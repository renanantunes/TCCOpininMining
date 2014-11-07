package beans;

import java.util.ArrayList;
import java.util.Map;

public class Report {
	private Map<String,Tweet> tweets;
	private String date;
	private String title;
	private ArrayList<Terms> terms;

	public Report(Map<String, Tweet> tweets, String date, String title, ArrayList<Terms> terms) {
		this.tweets = tweets;
		this.date = date;
		this.title = title;
		this.terms = terms;
	}
	
	public Map<String, Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(Map<String, Tweet> tweets) {
		this.tweets = tweets;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Terms> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<Terms> terms) {
		this.terms = terms;
	}
	
}
