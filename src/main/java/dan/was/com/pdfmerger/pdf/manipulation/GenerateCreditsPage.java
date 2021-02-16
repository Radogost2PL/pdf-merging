package dan.was.com.pdfmerger.pdf.manipulation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class GenerateCreditsPage {

    public static final String PDF_PROGRAM_INFO_FIRST_LINE =
            "Ten PDF zostal wygenerowany przez program napisany przez DW. Github: ";
    public static final String PDF_PROGRAM_INFO_SECOND_LINE =
            "https://github.com//Radogost2PL przy uzyciu https://pdfbox.apache.org";
    Logger logger = LoggerFactory.getLogger(GenerateCreditsPage.class);

    public void printPage(String destinationFileName) {
        PDPage blankPage = new PDPage();
        try {

            PDDocument mergedPdf = PDDocument.load(new File(destinationFileName));
            PDPageContentStream contentStream = new PDPageContentStream(mergedPdf, blankPage);

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 13);
//            contentStream.setFont(PDType1Font.encode(), 13);
            contentStream.showText(PDF_PROGRAM_INFO_FIRST_LINE);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLine();
            contentStream.newLineAtOffset(50, 670);
            contentStream.showText(PDF_PROGRAM_INFO_SECOND_LINE);
            contentStream.endText();
            contentStream.close();
            mergedPdf.addPage(blankPage);
            mergedPdf.save(destinationFileName);
        } catch (Exception e) {
            logger.error("Can't print or add last page: " + e);
        }

    }
}
