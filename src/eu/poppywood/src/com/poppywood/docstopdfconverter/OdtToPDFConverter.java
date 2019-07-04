package com.poppywood.docstopdfconverter;

import java.io.InputStream;
import java.io.OutputStream;

import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;


public class OdtToPDFConverter extends Converter {

	public OdtToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}


	@Override
	public void convert() throws Exception {
		loading();       


		OdfTextDocument document = OdfTextDocument.loadDocument(inStream);

		PdfOptions options = PdfOptions.create();

		
		processing();
		PdfConverter.getInstance().convert(document, outStream, options);

		finished();


	}

}
