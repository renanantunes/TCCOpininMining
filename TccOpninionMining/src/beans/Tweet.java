package beans;

public class Tweet{
	private long id;
	private String user;
	private String tweet;
	private String date;
	private String rating;
	private String term;
	private double score[];
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public double[] getScore() {
		return score;
	}
	public void setScore(double[] score) {
		this.score = score;
	}
	public String toString()
	{	
		return "ID: " + this.getId() + "\nUser: " + this.getUser() + "\n" + this.getTweet() + "\n"
				+ "Date: " + this.getDate() + "\nClassificação: " + this.getRating();
	}

}
