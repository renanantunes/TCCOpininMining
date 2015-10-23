package classifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import beans.Tweet;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import utils.Constants;


public class SentimentClassifier {
	String[] categories;
	static DoccatModel m = null;
	
	public SentimentClassifier(){
		try{
			InputStream is = new FileInputStream("trained_file.bin");		
			m = new DoccatModel(is);
		}
		catch (IOException e) {
			System.err.println("===SentimentClassifier.SentimentClassifier() error: " + e.getMessage());
		} 
	}
	
	/**
	 * aqui que ocorre toda a magica, no momento ele recebe aqui o texto a ser classificado e devolve a classificação dele
	 * Agora falta apenas implementar no nosso código, porém não sei se teremos que fazer outra tela ou o que para deixa separado
	 * classificação e coleta
	 */
	public Tweet openNlpClassify(Tweet tweet){
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
		double[] outcomes = myCategorizer.categorize(tweet.getTweetFormated());
		tweet.setRating(getBestOutcome(outcomes, myCategorizer));
		//tweet.setPredicates(myCategorizer.getPredicatesMap(tweet.getTweetFormated()));
		//System.out.println(myCategorizer.getAllResults(outcomes));
		double[] score = new double[3];
		score[0] = outcomes[2];
		score[1] = outcomes[0];
		score[2] = outcomes[1];
		tweet.setScore(score);
		return tweet;
	}
	
	private String getBestOutcome(double[] outcomes, DocumentCategorizerME myCategorizer){
		double value = -1;

		for(int i = 0; i<outcomes.length; i++){
			if(value == outcomes[i] || value < 0){
				value = outcomes[i];
			}else{
				return myCategorizer.getBestCategory(outcomes);
			}
		}
		
		return Constants.DEFAULT_OUTCOME;
	}

}
