package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.MainClass;
import beans.Report;
import beans.Terms;
import beans.Tweet;
import forms.MainWindowForm;
import gui.MainWindow;
import gui.TableHandler;

public class ApplicationUtils {
	
	public static Date createFormatDate(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT2);
			String formatedDate = sdf.format(date);
			date = sdf.parse(formatedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public static String parseDateToString(Date date){
		String formatedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT2);
		formatedDate = sdf.format(date);
		
		return formatedDate;
	}
	
	public static MainWindowForm getRatingCount(){
		int pos = 0;
		int neg = 0;
		int neu = 0;
		
		for (Tweet tweet : MainClass.tweetList) {
				switch (tweet.getRating()) {
				case Constants.POSITIVE:
					pos++;
					break;	
				case Constants.NEGATIVE:
					neg++;
					break;
				case Constants.NEUTRAL:
					neu++;
					break;
				}

		}
		MainWindowForm mwf = MainWindow.getMainWindowForm();
		mwf.setPositive(pos);
		mwf.setNegative(neg);
		mwf.setNeutral(neu);
		return mwf;
	}
	
	public static void populateTable(){
		for (Tweet tweet : MainClass.tweetList) {
				TableHandler.addRow(new Object[]{tweet.getRating(),tweet.getTweet()});
			}
	}
	
	public static String getCategoryName(int code){
		switch(code){
			case 0 : return Categories.POSITIVE.getCategoryName();
			case 1 : return Categories.NEGATIVE.getCategoryName();
			case 2 : return Categories.NEUTRAL.getCategoryName();
			default : return null;
		}
	}
	
	public static Report createReport(MainWindowForm mwf){
		ArrayList<Tweet> tweets = MainClass.tweetList;
		int tweetsPerCategory[] = {mwf.getPositive(), mwf.getNegative(), mwf.getNeutral()};
		
		return new Report(tweets, getDateRange(tweets), "Relatorio da Pesquisa", MainClass.terms, tweetsPerCategory);
	}
	
	public static String getDateRange(List<Tweet> tweets){
		Date recent = null;
		Date old = null;
		for (Tweet tweet : tweets) {
			if(recent == null && old == null){
				recent = old = tweet.getDate();
			}else{
				if(tweet.getDate().after(recent)){
					recent = tweet.getDate();
				}else if(tweet.getDate().before(old)){
					old = tweet.getDate();
				}
			}
		}
		
		if(old.compareTo(recent)==0){
			return parseDateToString(old);
		}else{
			return parseDateToString(old) + " - " + parseDateToString(recent);
		}
	}
	
	public static List<String> getTerm(String tweet){
		List<String> terms = new ArrayList<String>();
		
		for (Terms term : MainClass.terms) {
			if(tweet.contains(term.getName()));
				terms.add(term.getName());
		}
		
		return terms;
	}
	
	public static void populateTermsStats(){
		for (Terms term : MainClass.terms) {
			for (Tweet tweet : MainClass.tweetList) {
				if(tweet.getTerm().contains(term.getName())){
					int natScore[] = term.getNatScore();
					double absoluteScore[] = term.getAbsoluteScore();
					boolean isFirst = false;
					
					if(tweet.getRating().equalsIgnoreCase(Categories.POSITIVE.categoryNameEN))
						natScore[Categories.POSITIVE.getCode()] +=1;
					else if(tweet.getRating().equalsIgnoreCase(Categories.NEGATIVE.categoryNameEN))
						natScore[Categories.NEGATIVE.getCode()] +=1;
					else if(tweet.getRating().equalsIgnoreCase(Categories.NEUTRAL.categoryNameEN))
						natScore[Categories.NEUTRAL.getCode()] +=1;
					
					term.setNatScore(natScore);
					
					if(absoluteScore[0]==0){
						isFirst=true;
					}
					
					for (int i = 0; i < absoluteScore.length; i++) {
						absoluteScore[i] = tweet.getScore()[i];
						if(!isFirst){
							absoluteScore[i] = absoluteScore[i]/2;
						}
					}
					
					term.setAbsoluteScore(absoluteScore);
					
				}
			}
		}
	}
}
