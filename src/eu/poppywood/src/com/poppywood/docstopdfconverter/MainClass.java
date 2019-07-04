package com.poppywood.docstopdfconverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;



public class MainClass{


	public static final String VERSION_STRING = "0.1";
	public enum DOC_TYPE {
		DOC,
		DOCX,
		PPT,
		PPTX,
		ODT,
        TIF,
        TIFF,
        JPG,
        JPEG,
        GIF,
        BMP,
        PNG,
        WMF,
        PS,
        EPS,
        XLS,
        XLSX,
        PSD,
        SVG,
        EPUB,
        TXT
	}
	
	public static void main(String[] args){
		Converter converter;

		try{
			converter = processArguments(args);
		} catch (Exception e){
			System.out.println("\n\nInput\\Output file not specified properly.");
			return;
		}


		if(converter == null){
			System.out.println("Unable to determine type of input file.");
		} else {
			try {
				converter.convert();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	public static Converter processArguments(String[] args) throws Exception{
		CommandLineValues values = new CommandLineValues();
		CmdLineParser parser = new CmdLineParser(values);

		Converter converter = null;
		try {
			parser.parseArgument(args);

			boolean version = values.version;

			if(version){
				System.out.println(VERSION_STRING);
				System.exit(0);
			}


			String inPath = values.inFilePath;
			String outPath = values.outFilePath;
			boolean shouldShowMessages = values.verbose;
            boolean rtl = values.rtl;
            boolean landscape = values.landscape;


			if(inPath == null){
				parser.printUsage(System.err);
				throw new IllegalArgumentException();
			}

			if(outPath == null){
				outPath = changeExtensionToPDF(inPath);
			}


			String lowerCaseInPath = inPath.toLowerCase();
			
			InputStream inStream = getInFileStream(inPath);
			OutputStream outStream = getOutFileStream(outPath);
			
			if(values.type == null){
				if(lowerCaseInPath.endsWith("doc")){
					converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				} else if (lowerCaseInPath.endsWith("docx")){
					converter = new DocxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				} else if(lowerCaseInPath.endsWith("ppt")){
					converter = new PptToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				} else if(lowerCaseInPath.endsWith("pptx")){
					converter = new PptxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				} else if(lowerCaseInPath.endsWith("odt")){
					converter = new OdtToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("xls")){
                    converter = new XlsToPDFConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("epub")){
                    converter = new EpubToPDFConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("eps")){
                    converter = new EpsToPDFConverter(new File(inPath), new File(outPath), shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("psd")){
                    converter = new PsdToPDFConverter(new File(inPath), new File(outPath), shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("xlsx")){
                    converter = new XlsxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("tiff") || lowerCaseInPath.endsWith("tif")){
                    converter = new TifToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("ps")){
                    converter = new PsToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("jpg") || lowerCaseInPath.endsWith("jpeg") || lowerCaseInPath.endsWith("gif") || lowerCaseInPath.endsWith("bmp") || lowerCaseInPath.endsWith("png") || lowerCaseInPath.endsWith("wmf")){
                    converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("svg") ){
                    converter = new SvgToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                } else if(lowerCaseInPath.endsWith("txt") ){
                    converter = new TxtToPdfConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);

                } else {
					converter = null;
				}


			} else {

				switch(values.type){
				case DOC: converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				break; 
				case DOCX: converter = new DocxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				break;
				case PPT:  converter = new PptToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
		    		break;
				case PPTX: converter = new PptxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
			    	break;
				case ODT: converter = new OdtToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
				    break;
                case TIF: converter = new TifToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case TIFF: converter = new TifToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case JPG: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case JPEG: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case GIF: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case WMF: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case PS: converter = new PsToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case EPS: converter = new EpsToPDFConverter(new File(inPath), new File(outPath), shouldShowMessages, true, rtl, landscape);
                    break;
                case PSD: converter = new PsdToPDFConverter(new File(inPath), new File(outPath), shouldShowMessages, true, rtl, landscape);
                    break;
                case BMP: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case PNG: converter = new ImageToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case SVG: converter = new SvgToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case TXT: converter = new TxtToPdfConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);
                        break;
                case XLS: converter = new XlsToPDFConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case EPUB: converter = new EpubToPDFConverter(new File(inPath), outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                case XLSX: converter = new XlsxToPDFConverter(inStream, outStream, shouldShowMessages, true, rtl, landscape);
                    break;
                default: converter = null;
				break;

				}


			}
			
		} catch (CmdLineException e) {
			// handling of wrong arguments
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
		}

		return converter;

	}


	public static class CommandLineValues {

		@Option(name = "-type", aliases = "-t", required = false, usage = "Specifies doc converter. Leave blank to let program decide by input extension.")
		public DOC_TYPE type = null;


		@Option(name = "-inputPath", aliases = {"-i", "-in", "-input"}, required = false,  metaVar = "<path>",
				usage = "Specifies a path for the input file.")
		public String inFilePath = null;


		@Option(name = "-outputPath", aliases = {"-o", "-out", "-output"}, required = false, metaVar = "<path>",
				usage = "Specifies a path for the output PDF.")
		public String outFilePath = null;

		@Option(name = "-verbose", aliases = {"-v"}, required = false, usage = "To see intermediate processing messages.")
		public boolean verbose = false;

		@Option(name = "-version", aliases = {"-ver"}, required = false, usage = "To view version code.")
		public boolean version = false;

        @Option(name = "-landscape", aliases = {"-ls"}, required = false, usage = "To convert as landscape instead of portrait.")
        public boolean landscape = false;

        @Option(name = "-rtl", required = false, usage = "To specify rtl language")
        public boolean rtl = false;


	}

	public static String changeExtensionToPDF(String originalPath) {

		String filename = originalPath;
		int extensionIndex = filename.lastIndexOf(".");

		String removedExtension;
		if (extensionIndex == -1){
			removedExtension =  filename;
		} else {
			removedExtension =  filename.substring(0, extensionIndex);
		}
		String addPDFExtension = removedExtension + ".pdf";

		return addPDFExtension;
	}
	
	
	protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
		File inFile = new File(inputFilePath);
		FileInputStream iStream = new FileInputStream(inFile);
		return iStream;
	}
	
	protected static OutputStream getOutFileStream(String outputFilePath) throws IOException{
		File outFile = new File(outputFilePath);
		
		try{
			//Make all directories up to specified
			outFile.getParentFile().mkdirs();
		} catch (NullPointerException e){
			//Ignore error since it means not parent directories
		}
		
		outFile.createNewFile();
		FileOutputStream oStream = new FileOutputStream(outFile);
		return oStream;
	}











}
