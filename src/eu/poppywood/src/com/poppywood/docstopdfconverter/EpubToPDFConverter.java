package com.poppywood.docstopdfconverter;

import java.io.*;

public class EpubToPDFConverter extends Converter {


    public EpubToPDFConverter(File inFile, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
        super(inFile, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
    }

    @Override
    public void convert() throws Exception {
        loading();

        com.amphisoft.epub2pdf.Converter converter = new com.amphisoft.epub2pdf.Converter();

        processing();
        converter.convert(inFile.getPath());

        finished();

    }

}
