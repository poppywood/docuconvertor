package com.poppywood.docstopdfconverter;

import org.apache.poi.hssf.converter.ExcelToFoConverter;

import java.io.InputStream;
import org.w3c.dom.Document;
import java.io.OutputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.MimeConstants;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.Transformer;

public class XlsToPDFConverter extends Converter {


	public XlsToPDFConverter(File inFile, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inFile, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

	@Override
	public void convert() throws Exception {
        loading();
        //convert xls to fo
        Document document = ExcelToFoConverter.process(inFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(document);
        Result outputTarget = new StreamResult(outputStream);
        TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
        InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
        Source ins = new StreamSource(is);
        processing();
        FopFactory fopFactory = FopFactory.newInstance();
        // Construct fop with desired output format
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);
        //and the transformer
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(); // identity transformer

        // Resulting SAX events (the generated FO) must be piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        // Step 6: Start XSLT transformation and FOP processing
        transformer.transform(ins, res);


        finished();
    }

}
