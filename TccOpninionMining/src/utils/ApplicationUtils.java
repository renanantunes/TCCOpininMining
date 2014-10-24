package utils;

import forms.MainWindowForm;
import gui.MainWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.MainClass;
import beans.Tweet;

public class ApplicationUtils {
	
	public static String createFormatDate(String pattern){
		String formatedDate;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		formatedDate = sdf.format(c.getTime());
		
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
		MainWindowForm mwf = new MainWindowForm();
		mwf.setPositive(pos);
		mwf.setNegative(neg);
		mwf.setNeutral(neu);
		return mwf;
	}
	

	
}
