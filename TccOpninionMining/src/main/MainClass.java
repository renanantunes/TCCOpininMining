package main;

import engine.TwitterListener;
import engine.TwitterQueryManager;
import forms.MainWindowForm;
import gui.MainWindow;

import java.util.ArrayList;

import utils.Constants;
import utils.GuiUtils;
import beans.Tweet;

public class MainClass {
	
	public static ArrayList<Tweet> tweetList = new ArrayList<Tweet>();

	public static void main(String[] args) {
		
		MainWindow.main(args);
	}
	
	public static void initializeWork(MainWindowForm mwf){
		String keyWords [] = mwf.getKeyWords().split(Constants.COMMA_REGEX);
		
		if(mwf.getFetchType().equals(Constants.STREAMTYPE)){
			TwitterListener.createListener(mwf);
		}
		else{
			TwitterQueryManager tqm = new TwitterQueryManager(mwf);
			for(String inQuery: keyWords){
				tqm.performQuery(inQuery, mwf.getQuantity());
				GuiUtils.populateTable();
			}
		}
	}
}
