package com.poppywood.docstopdfconverter;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ImageCommand;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class EpsToPDFConverter extends Converter {

	public EpsToPDFConverter(File inFile, File outFile, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
		super(inFile, outFile, showMessages, closeStreamsWhenComplete, rtl, landscape);
	}

    @Override
    public void convert() throws Exception {
        loading();

        processing();


        IMOperation op = new IMOperation();
        op.addImage(inFile.getPath());

        op.density(150);
        op.quality(100.0);
        op.addImage(outFile.getPath());
        // set up command
        try {
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(op);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            finished();
        }

    }

}
