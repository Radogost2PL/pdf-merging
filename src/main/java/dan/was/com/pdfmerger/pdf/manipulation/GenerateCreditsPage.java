package dan.was.com.pdfmerger.pdf.manipulation;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class GenerateCreditsPage {

    public static final String PDF_PROGRAM_INFO_FIRST_LINE =
            "Ten PDF zostal wygenerowany przez program napisany przez DW. Github: ";
    public static final String PDF_PROGRAM_INFO_SECOND_LINE =
            "https://github.com//Radogost2PL przy uzyciu https://pdfbox.apache.org";
    Logger logger = LoggerFactory.getLogger(GenerateCreditsPage.class);

    //    public void printPage(String destinationFileName) {
    public void printPage(List<File> pdfList, String destinationFileName) {
        PDPage blankPage = new PDPage();


        try {
//            File file = new File(destinationFileName);
            HashMap hashMap = new HashMap();

            PDDocument mainPage = new PDDocument();
            mainPage.addPage(blankPage);
            PDPageContentStream contentStream = new PDPageContentStream(mainPage, blankPage);

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 13);

            contentStream.showText(PDF_PROGRAM_INFO_FIRST_LINE);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(50, 670);
            contentStream.showText(PDF_PROGRAM_INFO_SECOND_LINE);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(100, 640);
            contentStream.showText("Dokumenty zalsczone do PDF. Sprawdz zalaczniki.");
            contentStream.endText();
            contentStream.close();

            PDEmbeddedFilesNameTreeNode embeddedFilesNameTreeNode = new PDEmbeddedFilesNameTreeNode();

            int counter = 0;

            for (File file : pdfList) {
                PDComplexFileSpecification pdComplexFileSpecification = new PDComplexFileSpecification();
                counter++;
                System.out.println("@@@@@@@@@@@@!!!!!!!!!!!!!!!!!File " + file.getName());

                File file1 = new File(file.getPath());
                pdComplexFileSpecification.setFile(file1.getName());
                FileInputStream fileInputStream = new FileInputStream(file1);
                pdComplexFileSpecification.setFile(file1.getName());


                byte fileContent[] = new byte[(int) file1.length()];
                fileInputStream.read(fileContent);
                fileInputStream.close();

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bao.write(fileContent);

                byte[] data = bao.toByteArray();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

                PDEmbeddedFile pdEmbeddedFile = new PDEmbeddedFile(mainPage, byteArrayInputStream);

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
