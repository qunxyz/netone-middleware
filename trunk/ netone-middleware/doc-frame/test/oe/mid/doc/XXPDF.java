package oe.mid.doc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.pdf.PdfWriter;

public class XXPDF {

	public static void main(String[] args) throws Exception {
		FileOutputStream out = new FileOutputStream("C:/xxxxx.pdf");
		writePdf(out);
		out.close();
	}

	public static void writePdf(OutputStream outstream) throws Exception {

		InputStream input = new FileInputStream("c:/MyHtml.html");
		// int size = input.available();
		// byte[] info = new byte[size];
		// input.read(info);

		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, outstream);
			doc.open();
			// doc.add(new Paragraph(new String(info)));
			doc.newPage();
			HtmlParser.parse(doc, input);

		} catch (DocumentException de) {
			de.printStackTrace();
		} finally {
			doc.close();
			try {
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
