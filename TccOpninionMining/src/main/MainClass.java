package main;

import engine.TwitterManager;
import forms.MainWindowForm;
import gui.MainWindow;

import java.util.ArrayList;

import utils.Constants;
import beans.Terms;
//import utils.GuiUtils;
import beans.Tweet;

public class MainClass {
	
	public static ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	public static ArrayList<Terms> terms;

	public static void main(String[] args) {
		
		MainWindow.main(args);
	}
	
	public static void initializeWork(MainWindowForm mwf){
		String keyWords [] = mwf.getKeyWords().split(Constants.COMMA_REGEX);
		TwitterManager manager = new TwitterManager();
		terms = new ArrayList<Terms>(keyWords.length);
		
		for(int i=0; i<keyWords.length;i++){
			Terms term = new Terms(keyWords[i]);
			terms.add(term);
		}
		
		if(mwf.getFetchType().equals(Constants.STREAMTYPE)){
			manager.startListener(keyWords);
		}
		else{
			for(String inQuery: keyWords){
				manager.doQuery(inQuery, mwf.getQuantity());
			}
		}
	}
}
