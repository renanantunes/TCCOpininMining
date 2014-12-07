package utils;

import java.io.File;

public class Constants {

	//Dao - Não mais usado
	//public final static String STORENAME = "kvstore";
	//public final static String HOSTNAME = "localhost";
	//public final static String HOSTPORT = "5000";
	
	//Twitter Connection
	public final static String TWITTERCONSUMERKEY = "mpbw8VzN1xBO4Gefd0GSpg";
	public final static String TWITTERCONSUMERSECRET = "ehQnNojt7FUHLVQuGKmBlHDgo75z33ySKDEU8U";
	public final static String TWITTERACCESSTOKEN = "343697194-AqJGDK5eJ3a1AawDcbSb4JDysLw3kXQSpTx9QU86";
	public final static String TWITTERACCESSTOKENSECRET = "dbPG40VfbeDAZ6AW6E4sQLprqg7WWjLK7o7v8WS3uRexu";
	
	public final static String DATEFORMAT1 = "yyyy/MM/dd";
	public final static String DATEFORMAT2 = "dd-MM-yy";
	
	public final static String ARFFPATH = "dataset";
	
	//Types
	public final static String ARFFTYPE = "arff";
	public final static String TXTTYPE = "txt";
	public final static String STREAMTYPE = "stream";
	public final static String QUERYTYPE = "query";
	
	//REGEX
	public final static String COMMA_REGEX = ",";
	public final static String PIPE_REGEX = "|";
	
	//Padrões
	public final static String[] LANGUAGE = {"pt"};
	
	//PATH Classifier
	public final static String PATH = "classifier";
	public final static String CLASSIFIER_PATH = PATH + File.separator+"classifier.txt";
	public final static String NEGATIVE_PATH = PATH + File.separator+"negative"; 
	public final static String NEUTRAL_PATH = PATH + File.separator+"neutral"; 
	public final static String POSITIVE_PATH = PATH + File.separator+"positive"; 
	
	//
	public final static String POSITIVE = "positive";
	public final static String NEGATIVE = "negative";
	public final static String NEUTRAL = "neutral";
	
	public final static String PT_POSITIVE = "positivo";
	public final static String PT_NEGATIVE = "negativo";
	public final static String PT_NEUTRAL = "neutro";
	
	//Chart type
	public final static String CHARTTYPE_GENERAL = "general";
	public final static String CHARTTYPE_ABSOLUTESCORE = "abs";
	public final static String CHARTTYPE_NATSCORE = "nat";
	public final static String CHARTTYPE_DATE_NATSCORE = "date_natscore";
	public final static String CHARTTYPE_DATE_ABSSCORE = "date_absscore";
	public final static String CHARTTYPE_TERM_PIECHART = "term_piechart";
	public final static String CHARTTYPE_TERM_ABSSCORE = "term_abs";
	public final static String CHARTTYPE_TERM_DATE_NATSCORE = "term_date_natscore";
	public final static String CHARTTYPE_TERM_DATE_ABSSCORE = "term_date_absscore";
		
}
