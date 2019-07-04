package com.poppywood.docstopdfconverter;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.w3c.dom.Document;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XlsxToPDFConverter extends Converter {


	public XlsxToPDFConverter(InputStream inStrean, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inStrean, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

	@Override
	public void convert() throws Exception {
        loading();
        XSSFWorkbook document = new XSSFWorkbook(inStream) ;


        PdfOptions options = PdfOptions.create();


        processing();
        //PdfConverter.getInstance().convert(document, outStream, options);

        finished();
    }

}
