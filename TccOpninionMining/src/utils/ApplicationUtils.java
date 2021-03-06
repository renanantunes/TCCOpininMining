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
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beans.Report;
import beans.Terms;
import beans.Tweet;
import forms.MainWindowForm;
import gui.MainWindow;
import gui.TableHandler;
import main.MainClass;

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
			if(ApplicationUtils.removeAcentos(tweet.getTweet().toLowerCase()).contains(ApplicationUtils.removeAcentos(term.getName().toLowerCase())))
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
		str = removeUrl(str);
		str = removeUserName(str);
		str = removeRepeatedchars(str);
		str = str.replaceAll(":", "");
		str = str.replaceAll(",", "");
		return str.replaceAll("\\.", "");
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
			if(!words[i].equalsIgnoreCase(removeAcentos(term.getName())) && words[i].trim().length()>0){
				if(term.getWordsCount().containsKey(words[i])){
					Long j= term.getWordsCount().get(words[i]);
					term.getWordsCount().put(words[i], j+1);
				}else{
					term.getWordsCount().put(words[i], (long) 1);
				}
			}
		}
	}
	
	private static String removeUrl(String str){
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
        	str = str.replaceAll(Pattern.quote(m.group(i)),"").trim();
            i++;
        }
        return str;
    }
	
	private static String removeUserName(String str){
        String usernamePattern = "@(\\w){1,15}";
        Pattern p = Pattern.compile(usernamePattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        while (m.find()) {
        	str = str.replaceAll(usernamePattern,"").trim();
        }
        return str;
    }
	
	private static String removeRepeatedchars(String str){
		String regex = "(([A-Za-z])(\\2)(\\s|$)+)";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        while (m.find()) {
        	str = str.replaceAll(regex,"$2 ").trim();
        }
        return str;
	}
	
	private static String removeExtraWhitespace(String str){
		return str.replaceAll("\\s+", " ");
	}
	
	public static <K,V extends Comparable<? super V>> 
    	List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K,V>>() {
			@Override
			public int compare(Entry<K,V> e1, Entry<K,V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		}
				);

		return sortedEntries;
	}
	
//	private static void getMostImportantWords(Tweet tweet){
//		int categorieCode = -1;
//		if(tweet.getRating().equals(Categories.POSITIVE.getCategoryNameEN()))
//			categorieCode = Categories.POSITIVE.getAPICode();
//		else if(tweet.getRating().equals(Categories.NEGATIVE.getCategoryNameEN()))
//			categorieCode = Categories.NEGATIVE.getAPICode();
//		else if(tweet.getRating().equals(Categories.NEUTRAL.getCategoryNameEN()))
//			categorieCode = Categories.NEUTRAL.getAPICode();
//		
//		Double wordRate = 0.0;
//		String mostImportantWord = "";
//		
//		Iterator entries = tweet.getPredicates().entrySet().iterator();
//		while (entries.hasNext()) {
//		  Entry entry = (Entry) entries.next();
//		  String word = (String) entry.getKey();
//		  Double [] rating = (Double[]) entry.getValue();
//		  
//		  if(rating[categorieCode] > wordRate){
//			  wordRate = rating[categorieCode];
//			  mostImportantWord = word;
//		  }
//		}
//	}
	
	public static String formatTweetBeforeSave(String str){
		str = str.toLowerCase();
		str = str.replaceAll("rt\\s|rt:\\s", "");
		str = removeUserName(str);
		str = removeUrl(str);
		str = removeRepeatedchars(str);
		str = str.replaceAll(":", "");
		str = str.replaceAll(",", "");
		str = removeAcentos(str);
		return removeExtraWhitespace(str);
	}
	
	public static void main(String[] args) {
		System.out.println(formatTweetBeforeSave("rt Teestee dee @remocao , dee"));
	}

}
