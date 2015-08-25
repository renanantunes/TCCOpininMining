package engine.files;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;

import org.jfree.chart.JFreeChart;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import beans.Report;
import beans.Tweet;
import utils.ApplicationUtils;
import utils.Categories;

public class PDFHandler {
	private static Document doc = null;
	private static PdfWriter writer = null;

	public static boolean createPDF(String savePath,
			List<JFreeChart> chartList, Report report) {
		boolean success = true;
		try {
			OutputStream os = new FileOutputStream(savePath);
			doc = new Document(PageSize.A4, 72, 72, 72, 72);
			writer = PdfWriter.getInstance(doc, os);
			doc.open();

			Paragraph title = new Paragraph(report.getTitle(), new Font(
					Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
			Paragraph date = new Paragraph("Data: "+report.getDate(), new Font(
					Font.FontFamily.TIMES_ROMAN, 13));
			Paragraph tweetsCollected = new Paragraph("Tweets Coletados: "+report.getTweets().size(), new Font(
					Font.FontFamily.TIMES_ROMAN, 13));
			Paragraph searchTerms = new Paragraph("Termos Pesquisados: "+ApplicationUtils.getFormatedTermsName(report.getTerms()), new Font(
					Font.FontFamily.TIMES_ROMAN, 13));
			doc.add(title);
			doc.add(date);
			doc.add(tweetsCollected);
			doc.add(searchTerms);

			populatePDF(chartList);
			Paragraph p = new Paragraph();
			addEmptyLine(p, 2);
			doc.add(p);
			p = null;
			
			Paragraph paragraph = new Paragraph(3f, "Tweets Coletados", new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			doc.add(paragraph);
			
			p = new Paragraph();
			addEmptyLine(p, 2);
			doc.add(p);
			p=null;
			
			createTable(report.getTweets());
			
			p = new Paragraph();
			addEmptyLine(p, 3);
			doc.add(p);
			p=null;

			paragraph = new Paragraph(3f, "Palavras mais encontradas", new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			doc.add(paragraph);
			
			p = new Paragraph();
			addEmptyLine(p, 3);
			doc.add(p);
			p=null;
			
			createWordsCountTable(report);
			
			p = new Paragraph();
			addEmptyLine(p, 2);
			doc.add(p);
			p=null;
			
			
			
			doc.close();
			doc = null;
			os.close();
			os = null;
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
		} finally {
			if (null != doc) {
				try {
					doc.close();
				} catch (Exception ex) {
				}
			}

			if (null != writer) {
				try {
					writer.close();
				} catch (Exception ex) {
				}
			}
		}
		return success;
	}

	private static void populatePDF(List<JFreeChart> chartList) {
		for (JFreeChart jFreeChart : chartList) {
			try {
				BufferedImage bufferedImage = null;
				bufferedImage = ((JFreeChart) jFreeChart).createBufferedImage(
						500, 250);

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

	private static void createTable(List<Tweet> tweets)
			throws DocumentException {
		float[] columnWidths = { 2f, 8f, 1.5f };
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);

		Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 11);

		// insert column headings
		insertCell(table, "Classifica��o", Element.ALIGN_CENTER, 1, bfBold12,
				null);
		insertCell(table, "Tweet", Element.ALIGN_CENTER, 1, bfBold12, null);
		insertCell(table, "Termo", Element.ALIGN_CENTER, 1, bfBold12, null);
		table.setHeaderRows(1);

		for (Tweet tweet : tweets) {
			BaseColor color = null;
			if (tweet.getRating().equalsIgnoreCase(
					Categories.POSITIVE.getCategoryNameEN())) {
				color = new BaseColor(79, 235, 1, 20);
			} else if (tweet.getRating().equalsIgnoreCase(
					Categories.NEGATIVE.getCategoryNameEN())) {
				color = new BaseColor(239, 0, 0, 20);
			} else if (tweet.getRating().equalsIgnoreCase(
					Categories.NEUTRAL.getCategoryNameEN())) {
				color = new BaseColor(255, 255, 0, 20);
			}
			insertCell(
					table,
					ApplicationUtils.getCategoryNameByENName(tweet.getRating()),
					Element.ALIGN_LEFT, 1, bf12, color);
			insertCell(table, tweet.getTweet(), Element.ALIGN_LEFT, 1, bf12,
					null);
			insertCell(table,
					ApplicationUtils.getFormatedTweetCategory(tweet.getTerm()),
					Element.ALIGN_LEFT, 1, bf12, null);
		}
		Paragraph paragraph = new Paragraph();
		paragraph.add(table);
		doc.add(paragraph);
	}

	private static void insertCell(PdfPTable table, String text, int align,
			int colspan, Font font, BaseColor color) {
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		if (color != null) {
			cell.setBackgroundColor(color);
		}
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		table.addCell(cell);

	}
	
	private static void createWordsCountTable(Report report)
		throws DocumentException {
			

			Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 11);

			
			
			for (Entry<String, List<Entry<String, Long>>> entry : report.getWordsCount().entrySet()){
				
				Paragraph paragraph = new Paragraph(3f, entry.getKey(), new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				doc.add(paragraph);
				
				float[] columnWidths = { 2f, 8f};
				PdfPTable table = new PdfPTable(columnWidths);
				table.setWidthPercentage(50f);
				
				// insert column headings
				insertCell(table, "Palavra", Element.ALIGN_CENTER, 1, bfBold12,
						null);
				insertCell(table, "Frequência", Element.ALIGN_CENTER, 1, bfBold12, null);
				table.setHeaderRows(1);
				
				List<Entry<String, Long>> words = entry.getValue();
				
				for (int i=0; i<10 && i<words.size(); i++){
					Entry<String, Long> word = words.get(i);
					insertCell(
							table,
							word.getKey(),
							Element.ALIGN_LEFT, 1, bf12, null);
					insertCell(table, word.getValue().toString(), Element.ALIGN_LEFT, 1, bf12,
							null);
				}
				
				
				Paragraph p = new Paragraph();
				p.add(table);
				doc.add(p);
				p = new Paragraph();
				addEmptyLine(p, 2);
				doc.add(p);
			}
			
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}