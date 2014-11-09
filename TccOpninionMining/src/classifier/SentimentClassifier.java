package classifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import beans.Tweet;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;


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
	 * É aqui que ocorre toda a mágica, no momento ele recebe aqui o texto a ser classificado e devolve a classificação dele
	 * Agora falta apenas implementar no nosso código, porém não sei se teremos que fazer outra tela ou o que para deixa separado
	 * classificação e coleta
	 */
	public Tweet openNlpClassify(Tweet tweet){
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
		double[] outcomes = myCategorizer.categorize(tweet.getTweet());
		tweet.setRating(myCategorizer.getBestCategory(outcomes));
		tweet.setScore(outcomes);
		return tweet;
	}

}
