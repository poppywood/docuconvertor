package com.poppywood.docstopdfconverter;

import java.io.*;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.Transcoder;
import org.apache.fop.svg.PDFTranscoder;

public class SvgToPDFConverter extends Converter {

    public SvgToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
    }

    @Override
    public void convert() throws Exception {
        loading();


        processing();
        Transcoder transcoder = new PDFTranscoder();
        transcoder.transcode(new TranscoderInput(inStream), new TranscoderOutput(outStream));

        finished();
    }

}


