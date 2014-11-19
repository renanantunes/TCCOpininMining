package engine.files;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.jfree.chart.JFreeChart;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
  
public class PDFHandler {
	private static Document doc = null;
	private static PdfWriter writer = null;
	
	public static boolean createPDF(String savePath, List<JFreeChart> chartList){
		boolean success = true;
		try {
			OutputStream os = new FileOutputStream(savePath);
			doc = new Document(PageSize.A4, 72, 72, 72, 72);
			writer = PdfWriter.getInstance(doc, os);
			doc.open();
			
			Paragraph title = new Paragraph("Titulo", new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD));
			Paragraph date = new Paragraph("data", new Font(Font.FontFamily.TIMES_ROMAN, 12));
			doc.add(title);
			doc.add(date);
			
			populatePDF(chartList);
			
			doc.close();
			doc = null;
			os.close();
			os=null;
			writer.close();
			writer = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (DocumentException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}finally {
			if(null != doc) {
                try { doc.close(); }
                catch(Exception ex) { }
            }
             
            if(null != writer) {
                try { writer.close(); }
                catch(Exception ex) { }
            }
		}
		return success;
	}
	
	private static void populatePDF(List<JFreeChart> chartList){
		for (JFreeChart jFreeChart : chartList) {
			try {
				BufferedImage bufferedImage = jFreeChart.createBufferedImage(300, 300);
				Image image = Image.getInstance(writer, bufferedImage, 1.0f);
				doc.add(image);
			} catch (BadElementException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}