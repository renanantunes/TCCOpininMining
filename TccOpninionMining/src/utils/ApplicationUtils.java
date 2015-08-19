package utils;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static String getCategoryNameByENName(String name){
		switch(name){
			case Constants.POSITIVE : return Categories.POSITIVE.getCategoryName();
			case Constants.NEGATIVE : return Categories.NEGATIVE.getCategoryName();
			case Constants.NEUTRAL : return Categories.NEUTRAL.getCategoryName();
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
	
	public static List<String> getTerm(Tweet tweet){
		List<String> terms = new ArrayList<String>();
		
		for (Terms term : MainClass.terms) {
			if(tweet.getTweet().toLowerCase().contains(term.getName().toLowerCase()))
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
					term.getTweets().add(tweet);
					
					populateWordsCount(term, tweet.getTweetFormated());
					
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
	
	public static Map<Date, List<Tweet>> getTweetsPerDate(List<Tweet> tweets){
		Map<Date, List<Tweet>> tweetList = new HashMap<Date, List<Tweet>>();

		Collections.sort(tweets, new Comparator<Tweet>() {	
			@Override
			public int compare(Tweet o1, Tweet o2) {
			      return o1.getDate().compareTo(o2.getDate());
			  }
		});
		
		Date dateAux = tweets.get(0).getDate();
		List<Tweet> tweetsToAdd = new ArrayList<Tweet>();
		
		for (Tweet tweet : tweets) {
			if(dateAux.compareTo(tweet.getDate())==0){
				tweetsToAdd.add(tweet);
			}else{
				List<Tweet> newList = new ArrayList<Tweet>();
				newList.addAll(tweetsToAdd);
				tweetList.put(dateAux, newList);
				dateAux = tweet.getDate();
				tweetsToAdd.clear();
				tweetsToAdd.add(tweet);
			}
		}

		return tweetList;
	}
	
	public static int[] getRatingAvg(Collection<Tweet> tweets){
		int[] ratingAvg = new int[3];
		
		for (Tweet tweet : tweets) {
			if(tweet.getRating().equalsIgnoreCase(Categories.POSITIVE.categoryNameEN))
				ratingAvg[Categories.POSITIVE.getCode()]++;
			else if(tweet.getRating().equalsIgnoreCase(Categories.NEGATIVE.categoryNameEN))
				ratingAvg[Categories.NEGATIVE.getCode()]++;
			else if(tweet.getRating().equalsIgnoreCase(Categories.NEUTRAL.categoryNameEN))
				ratingAvg[Categories.NEUTRAL.getCode()]++;
		}
		
		return ratingAvg;
	}
	
	public static double[] getAbsScoreAvg(Collection<Tweet> tweets){
		double[] ratingAvg = new double[3];
		
		for (Tweet tweet : tweets) {
			double absoluteScore[] = tweet.getScore();
			boolean isFirst = false;

			if(ratingAvg[0]==0){
				isFirst=true;
			}

			for (int i = 0; i < absoluteScore.length; i++) {
				ratingAvg[i] += absoluteScore[i];
				if(!isFirst){
					ratingAvg[i] = ratingAvg[i]/2;
				}
			}
		}
		
		return ratingAvg;
	}
	
	public static String getFormatedTweetCategory(List<String> term){
		String terms = "";
		int i = term.size();
		int j = 0;
		for (String t : term) {
			terms += t;
			if(j != i-1){
				terms += ", ";
			}
		}
		
		return terms;
	}
	
	public static String getFormatedTermsName(List<Terms> terms){
		String termsString = "";
		int i = terms.size();
		int j = 0;
		for (Terms term : terms) {
			termsString += term.getName();
			if(j != i-1){
				termsString += ", ";
			}
		}
		
		return termsString;
	}
	
	public static String removeAcentos(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
	
	public static String removeUserNameAndURLs(String str){
		str = str.replace(".", "");
		str = str.replace(",", "");
		str = removeUrl(str);
		return removeUserName(str);
	}
	
	public static String removeStopWords(String str){
		String strOut = "";
		String [] words = str.split(" ");
		for(int i=0; i<words.length; i++){
			if(!MainClass.stoplist.contains(words[i])){
				strOut += words[i] + " ";
			}
		}	
		return strOut;
	}
	
	public static String getTweetFormated(String str){
		str = str.toLowerCase();
		str = removeAcentos(str);
		str = removeUserNameAndURLs(str);
		return removeStopWords(str);
				
	}
	
	private static void populateWordsCount(Terms term, String tweet){
		String [] words = tweet.split(" ");
		for(int i = 0; i<words.length;i++){
			if(term.getWordsCount().containsKey(words[i])){
				Long j= term.getWordsCount().get(words[i]);
				term.getWordsCount().put(words[i], j++);
			}else{
				term.getWordsCount().put(words[i], (long) 1);
			}
		}
	}
	
	private static String removeUrl(String str){
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
        	str = str.replaceAll(m.group(i),"").trim();
            i++;
        }
        return str;
    }
	
	private static String removeUserName(String str){
        String urlPattern = "(?<=^|(?<=[^a-zA-Z0-9-_\\.]))@([A-Za-z]+[A-Za-z0-9_]+)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
        	str = str.replaceAll(m.group(i),"").trim();
            i++;
        }
        return str;
    }
}
