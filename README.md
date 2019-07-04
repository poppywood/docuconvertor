

Docs to PDF Converter
=====================


A standalone Java library/command line tool that converts DOC, DOCX, PPT, PPTX, TIFF and ODT documents to pdf files. (Requires JRE 6)


<b>Command Line Usage:</b>


Basic command line use is
java -jar poppywood.jar -input test.ppt



java -jar poppywood.jar -type "type" -input "path" -output "path" -verbose<br>
```
java -jar poppywood.jar -input test.doc
java -jar poppywood.jar -i test.ppt -o ~\output.pdf
java -jar poppywood.jar -i ~\no-extension-file -o ~\output.pdf -t docx
```

<b>Parameters:</b><br>
```
-inputPath (-i, -in, -input) "path" : specifies a path for the input file

-outputPath (-o, -out, -output) "path" : specifies a path for the output PDF, use input file directory and name.pdf if not specified (Optional)

-type (-t) [DOC | DOCX | PPT | PPTX | ODT] : Specifies doc converter. Leave blank to let program infer via file  extension (Optional)

-verbose (-v) : To view intermediate processing messages. (Optional)
```

<b>Library Usage:</b><br>
<ol>
<li>Drop the jar into your lib folder and add to build path.</li>
<li>Choose the converter of your choice, they are named DocToPDFConverter, DocxToPDFConverter, PptToPDFConverter, PptxToPDFConverter and OdtToPDFConverter.</li>
<li>Instantiate with 4 parameters</li>
3a: InputStream inStream: Document source stream to be converted<br>
3b: OutputStream outStream: Document output stream<br>
3c: boolean showMessages: Whether to show intermediate processing messages to Standard Out (stdout)<br>
3d: boolean closeStreamsWhenComplete: Whether to close input and output streams when complete<br>
<li>Call the "convert()" method and wait.</
</ol>

<b>Caveats and technical details:</b><br>
This tool relies on Apache POI, xdocreport, docx4j and odfdom libraries. They are not 100% reliable and the output format may not always be what you desire.<br>


DOC:<br>
Conversion is done using docx4j to convert DOC to DOCX then to PDF.<br>

DOCX:<br>
Conversion is done using xdocreport library as it seems faster and more accurate than docx4j.<br>

ODT:<br>
Conversion is done using odfdom of the Apache ODF Toolkit.<br>

PPTX, PPT, TIFF, RTF and Image formats:<br>
Uses the last LGPL version of iText (source code included)


<b>Main Libraries</b><br>
Apache POI:  https://poi.apache.org/<br>
xdocreport: http://code.google.com/p/xdocreport/<br>
docx4j: http://www.docx4java.org/<br>
odfdom: https://incubator.apache.org/odftoolkit/odfdom/<br>
itext lgpl: https://github.com/ymasory/iText-4.2.0<br>
wrapper: https://github.com/yeokm1/docs-to-pdf-converter<br>
and others...<br>
<br>
The MIT License (MIT)<br>
