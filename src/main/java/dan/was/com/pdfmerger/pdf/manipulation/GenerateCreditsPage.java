package dan.was.com.pdfmerger.pdf.manipulation;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class GenerateCreditsPage {

    public static final String DOCUMENT_TITLE = "OPERAT ELEKTRONICZNY";
    public static final String SUBMIT_DATE = "Data utworzenia operatu: ";
    public static final String ATTACHMENTS_INFO = "Ten PDF zawiera zalaczone dokumenty. Sprawdz zalaczniki.";
    public static final String PDF_PROGRAM_INFO_FIRST_LINE =
            "Ten PDF zostal wygenerowany przez program napisany przez DW. Github: ";
    public static final String PDF_PROGRAM_INFO_SECOND_LINE =
            "https://github.com//Radogost2PL przy uzyciu https://pdfbox.apache.org";
    Logger logger = LoggerFactory.getLogger(GenerateCreditsPage.class);

    //    public void printPage(String destinationFileName) {
    public void printPage(List<File> pdfList, String destinationFileName) {
        PDPage blankPage = new PDPage();


        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            HashMap hashMap = new HashMap();

            PDDocument mainPage = new PDDocument();
            mainPage.addPage(blankPage);
            PDPageContentStream contentStream = new PDPageContentStream(mainPage, blankPage);

            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(300, 720);
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.showText(SUBMIT_DATE + dtf.format(now));
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 660);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.showText(DOCUMENT_TITLE);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(80, 570);
            contentStream.showText(ATTACHMENTS_INFO);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(50, 370);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.showText(PDF_PROGRAM_INFO_FIRST_LINE);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(50, 360);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.showText(PDF_PROGRAM_INFO_SECOND_LINE);
            contentStream.endText();

            contentStream.close();

            PDEmbeddedFilesNameTreeNode embeddedFilesNameTreeNode = new PDEmbeddedFilesNameTreeNode();

            int counter = 0;

            for (File file : pdfList) {
                PDComplexFileSpecification pdComplexFileSpecification = new PDComplexFileSpecification();
                counter++;

                File fileToAttach = new File(file.getPath());
                pdComplexFileSpecification.setFile(fileToAttach.getName());
                FileInputStream fileInputStream = new FileInputStream(fileToAttach);

                byte[] fileContent = new byte[(int) fileToAttach.length()];
                fileInputStream.read(fileContent);
                fileInputStream.close();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(fileContent);

                byte[] data = byteArrayOutputStream.toByteArray();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

                PDEmbeddedFile pdEmbeddedFile = new PDEmbeddedFile(mainPage, byteArrayInputStream);
                pdEmbeddedFile.setSize(data.length);
                pdEmbeddedFile.setCreationDate(Calendar.getInstance());


                pdComplexFileSpecification.setEmbeddedFile(pdEmbeddedFile);
                hashMap.put("Attachments" + counter, pdComplexFileSpecification);
            }

            PDEmbeddedFilesNameTreeNode pdEmbeddedFilesNameTreeNode = new PDEmbeddedFilesNameTreeNode();
            pdEmbeddedFilesNameTreeNode.setNames(hashMap);

            List<PDEmbeddedFilesNameTreeNode> filesNameTreeNodeArrayList = new ArrayList<>();
            filesNameTreeNodeArrayList.add(pdEmbeddedFilesNameTreeNode);
            embeddedFilesNameTreeNode.setKids(filesNameTreeNodeArrayList);

            PDDocumentNameDictionary names = new PDDocumentNameDictionary(mainPage.getDocumentCatalog());
            names.setEmbeddedFiles(embeddedFilesNameTreeNode);
            mainPage.getDocumentCatalog().setNames(names);

            mainPage.save(destinationFileName);
            mainPage.close();
            logger.info("Attachments added");
        } catch (Exception e) {
            logger.error("Can't attach files: " + e);
        }
    }
}
