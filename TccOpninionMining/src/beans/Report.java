package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.ApplicationUtils;

public class Report {
	private List<Tweet> tweets;
	private String date;
	private String title;
	private ArrayList<Terms> terms;
	private int tweetsPerCategory[];
	private Terms termToReport;
	private Map <String,List<Entry<String, Long>>> wordsCount;

	public Report(List<Tweet> tweets, String date, String title, ArrayList<Terms> terms, int tweetsPerCategory[]) {
		this.tweets = tweets;
		this.date = date;
		this.title = title;
		this.terms = terms;
		this.tweetsPerCategory = tweetsPerCategory;
		this.wordsCount = new HashMap<>();
		setWordsCount();
	}
	
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
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
	public int[] getTweetsPerCategory() {
		return tweetsPerCategory;
	}

	public void setTweetsPerCategory(int[] tweetsPerCategory) {
		this.tweetsPerCategory = tweetsPerCategory;
	}

	public Terms getTermToReport() {
		return termToReport;
	}

	public void setTermToReport(Terms termToReport) {
		this.termToReport = termToReport;
	}

	public Map<String, List<Entry<String, Long>>> getWordsCount() {
		return wordsCount;
	}

	public void setWordsCount() {
		for (Terms term : terms) {
			List<Entry<String, Long>> wordsCount = ApplicationUtils.entriesSortedByValues(term.getWordsCount());
			getWordsCount().put(term.getName(), wordsCount);
		}
	}
	
	
}
