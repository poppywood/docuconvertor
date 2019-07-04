package com.poppywood.docstopdfconverter;

import java.io.*;
import java.nio.charset.Charset;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.mozilla.universalchardet.UniversalDetector;
/**
 * Created by shelly on 23/12/14.
 */
public class TxtToPdfConverter extends Converter {
    public TxtToPdfConverter(File inFile, OutputStream outStream, boolean showMessages,
                             boolean closeStreamsWhenComplete, boolean rtl, boolean landscape) {
        super(inFile, outStream, showMessages, closeStreamsWhenComplete, rtl, landscape);
    }
    protected Font f;
    protected Document document;

    @Override
    public void convert() throws Exception {
        loading();
        DataInputStream in=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        if (landscape) {
            this.document = new Document(PageSize.A4.rotate());
        } else {
            this.document = new Document(PageSize.A4);
        }
        try{
            processing();
            PdfWriter writer = PdfWriter.getInstance(document, outStream);
            document.open();
            FontFactory.registerDirectories();
            f = new Font(BaseFont.createFont("/usr/share/fonts/local/TTF/Arial Unicode.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12);
            document.add(new Paragraph("\n"));
            RandomAccessFile ins = new RandomAccessFile(inFile, "r");

            byte[] buf = new byte[4096];
            // (1)
            UniversalDetector detector = new UniversalDetector(null);

            // (2)
            int nread;
            while ((nread = ins.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            // (3)
            detector.dataEnd();
            // (4)
            String encoding = detector.getDetectedCharset();
            if (encoding.equals("UTF-16LE") || encoding.equals("UTF-16BE")) encoding = "UTF-16";
            Charset ch = Charset.forName(encoding);
            ins.close();



            br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile),encoding));
            String strLine;
            while ((strLine = br.readLine()) != null)  {
                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100);
                PdfPCell cell = new PdfPCell();
                cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
                char[] chars = strLine.toCharArray();
                for(char c : chars)       {
                    if(c >= 0x5D0 && c <= 0x6ff){
                        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                        break;
                    }
                }
                cell.setBorder(PdfPCell.NO_BORDER);
                Paragraph para =null;
                if (encoding.equals("UTF-16")){
                    para =new Paragraph((Charset.forName(encoding).encode(strLine)).asCharBuffer().toString()+"\n",f);
                } else {
                    para = new Paragraph(strLine+"\n",f);
                }

                para.setAlignment(Element.ALIGN_LEFT);
                cell.addElement(para);
                table.addCell(cell);
                document.add(table);
            }

            document.close();

        }
        catch (Exception i1){
            i1.printStackTrace();
        }

    }



}
