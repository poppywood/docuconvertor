package com.poppywood.docstopdfconverter;

import org.apache.commons.io.IOUtils;
import org.ghost4j.converter.PDFConverter;
import org.ghost4j.document.PSDocument;
import java.io.InputStream;
import java.io.OutputStream;

public class PsToPDFConverter extends PptxToPDFConverter {

	public PsToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

    @Override
    public void convert() throws Exception {
        loading();


        PSDocument document = new PSDocument();
        document.load(inStream);

        processing();
        PDFConverter converter = new PDFConverter();
        converter.setPDFSettings(PDFConverter.OPTION_PDFSETTINGS_PREPRESS);

        //convert
        converter.convert(document, outStream);

        finished();
        IOUtils.closeQuietly(outStream);
    }

}
