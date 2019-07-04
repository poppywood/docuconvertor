package com.poppywood.docstopdfconverter;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.parser.RtfParser;

import java.io.InputStream;
import java.io.OutputStream;

public class RtfToPDFConverter extends Converter {


	public RtfToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

	@Override
	public void convert() throws Exception {
        loading();

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, outStream);
        document.open();

        processing();

        RtfParser parser = new RtfParser(null);
        parser.convertRtfDocument(inStream, document);


        document.close();
        writer.close();
        finished();
    }

}
