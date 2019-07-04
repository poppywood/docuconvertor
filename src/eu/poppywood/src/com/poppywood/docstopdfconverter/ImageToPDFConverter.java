package com.poppywood.docstopdfconverter;


import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

public class ImageToPDFConverter extends Converter {


	public ImageToPDFConverter(InputStream fileInStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(fileInStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

	@Override
	public void convert() throws Exception {
		loading();

        try{
            BufferedImage raw = ImageIO.read(inStream);
            double w = 1.1 * raw.getWidth();
            double h = 1.1 * raw.getHeight();
            Rectangle pageSize = new Rectangle((float)w , (float)h);
            Image image = Image.getInstance(raw, null);
            Document document = new Document(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, outStream);
            writer.setStrictImageSequence(true);
            document.open();
            document.add(image);
            document.close();
            System.out.println("Image to PDF Conversion in Java Completed" );
        }
        catch (Exception i1){
            i1.printStackTrace();
        }
        finally {
            finished();
        }


    }

}
