package com.poppywood.docstopdfconverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;



public abstract class Converter {


	private final String LOADING_FORMAT = "\nLoading stream\n\n";
	private final String PROCESSING_FORMAT = "Load completed in %1$dms, now converting...\n\n";
	private final String SAVING_FORMAT = "Conversion took %1$dms.\n\nTotal: %2$dms\n";

	private long startTime;
	private long startOfProcessTime;

	protected InputStream inStream;
	protected OutputStream outStream;
    protected File inFile;
    protected File outFile;

    protected boolean rtl = false;
    protected boolean landscape = false;
	protected boolean showOutputMessages = false;
	protected boolean closeStreamsWhenComplete = true;

	public Converter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape){
		this.inStream = inStream;
		this.outStream = outStream;
		this.showOutputMessages = showMessages;
		this.closeStreamsWhenComplete = closeStreamsWhenComplete;
        this.rtl = rtl;
        this.landscape = landscape;
	}

    public Converter(File inFile, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape){
        this.inFile = inFile;
        this.outStream = outStream;
        this.showOutputMessages = showMessages;
        this.closeStreamsWhenComplete = closeStreamsWhenComplete;
        this.rtl = rtl;
        this.landscape = landscape;
    }

    public Converter(File inFile, File outFile, boolean showMessages, boolean closeStreamsWhenComplete, boolean rtl, boolean landscape){
        this.inFile = inFile;
        this.outFile = outFile;
        this.showOutputMessages = showMessages;
        this.closeStreamsWhenComplete = closeStreamsWhenComplete;
        this.rtl = rtl;
        this.landscape = landscape;
    }

	public abstract void convert() throws Exception;

	private void startTime(){
		startTime = System.currentTimeMillis();
		startOfProcessTime = startTime;
	}

	protected void loading(){
		sendToOutputOrNot(String.format(LOADING_FORMAT));
		startTime();
	}

	protected void processing(){
		long currentTime = System.currentTimeMillis();
		long prevProcessTook = currentTime - startOfProcessTime;

		sendToOutputOrNot(String.format(PROCESSING_FORMAT, prevProcessTook));

		startOfProcessTime = System.currentTimeMillis();

	}

	protected void finished(){
		long currentTime = System.currentTimeMillis();
		long timeTaken = currentTime - startTime;
		long prevProcessTook = currentTime - startOfProcessTime;

		startOfProcessTime = System.currentTimeMillis();

		if(closeStreamsWhenComplete){
			try {
				inStream.close();
				outStream.close();
			} catch (IOException e) {
				//Nothing done
			}
		}

		sendToOutputOrNot(String.format(SAVING_FORMAT, prevProcessTook, timeTaken));
         // convert -density 300 -quality 100  testdoc.pdf /Users/shelly/testdoc.jpg


    }


	private void sendToOutputOrNot(String toBePrinted){
		if(showOutputMessages){
			actuallySendToOutput(toBePrinted);
		}
	}
	
	
	protected void actuallySendToOutput(String toBePrinted){
		System.out.println(toBePrinted);
	}








}
