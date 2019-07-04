package com.poppywood.docstopdfconverter;


import java.io.*;

import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;

public class TifToPDFConverter extends Converter {


	public TifToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

	@Override
	public void convert() throws Exception {
		loading();

        try{

            RandomAccessFileOrArray myTiffFile = new RandomAccessFileOrArray(inStream);
            processing();
            //Find number of images in Tiff file
            int numberOfPages=TiffImage.getNumberOfPages(myTiffFile);

            Image image = TiffImage.getTiffImage(myTiffFile, 1);
            double w = 1.1 * image.getWidth();
            double h = 1.1 * image.getHeight();
            Rectangle pageSize = new Rectangle((float)w, (float)h);

            Document document = new Document(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, outStream);
            writer.setStrictImageSequence(true);
            document.open();
            Image tempImage;
            //Run a for loop to extract images from Tiff file
            //into a Image object and add to PDF recursively
            for(int i=1;i<=numberOfPages;i++){
                tempImage=TiffImage.getTiffImage(myTiffFile, i);
                //check image size
                Rectangle ps = new Rectangle(tempImage.getWidth(),
                        tempImage.getHeight());
                //set pdf page size to equal the image size
                document.setPageSize(ps);
                document.add(tempImage);
            }
            document.close();
            writer.close();
            finished();
        }
        catch (Exception i1){
            i1.printStackTrace();
        }


    }

}
