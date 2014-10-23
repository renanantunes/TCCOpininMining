package classifier;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import utils.Constants;


public class Trainer 
{
	/**
	 * Ao usar esse metodo, se passa o path aonde estão as pastas Neg, Pos e Neu, ele avalia toda elas e na pasta informada cria o
	 * arquivo classifier.txt que é utilizado na classificação.
	 */
	
	public static boolean openNPLTrain(){
		DoccatModel model = null;
		boolean done = true;
		InputStream dataIn = null;
		OutputStream modelOut = null;
		try {
		  dataIn = new FileInputStream(Constants.PATH+File.separator+"totrain.train");
		  ObjectStream<String> lineStream =
				new PlainTextByLineStream(dataIn, "UTF-8");
		  ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		  model = DocumentCategorizerME.train("pt", sampleStream);
		  
		  modelOut = new BufferedOutputStream(new FileOutputStream("trained_file.bin"));
		  model.serialize(modelOut);
		}
		catch (IOException e) {
		  // Failed to read or parse training data, training failed
		  e.printStackTrace();
		  done = false;
		}
		finally {
		  if (dataIn != null) {
		    try {
		      dataIn.close();
		    }
		    catch (IOException e) {
		      // Not an issue, training already finished.
		      // The exception should be logged and investigated
		      // if part of a production system.
		      e.printStackTrace();
		      done=false;
		    }
		  }
		}
		return done;
	}
}
