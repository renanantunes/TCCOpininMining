package engine.files;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.JFreeChart;

import charts.PieChart;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
  
public class PDFHandler {
	Document doc = null;
	
	public void createPDF(String savePath){
		try {
			OutputStream os = new FileOutputStream(savePath);
			doc = new Document(PageSize.A4, 72, 72, 72, 72);
			PdfWriter.getInstance(doc, os);
			doc.open();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (doc != null) {
                doc.close();
            }
		}
	} 
	
	public void createPDF(String savePath, JFreeChart chart){
		try {
			OutputStream os = new FileOutputStream(savePath);
			doc = new Document(PageSize.A4, 72, 72, 72, 72);
			PdfWriter writer = PdfWriter.getInstance(doc, os);
			doc.open();
			PdfContentByte contentByte = writer.getDirectContent();
	        PdfTemplate template = contentByte.createTemplate(300, 300);
	        Graphics2D graphics2d = template.createGraphics(300, 300,new DefaultFontMapper());
	        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 300,
	                300);
	 
	        chart.draw(graphics2d, rectangle2d);
	         
	        graphics2d.dispose();
	        contentByte.addTemplate(template, 0, 0);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (doc != null) {
//                doc.close();
            }
		}
	} 
}