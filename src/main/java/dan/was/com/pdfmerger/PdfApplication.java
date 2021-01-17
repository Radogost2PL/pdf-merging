package dan.was.com.pdfmerger;

import dan.was.com.pdfmerger.pdfmanipulationservice.MergePdf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class PdfApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(PdfApplication.class, args);

   //     testing merging with pdf box
//        File pdf1 = new File("");
//        File pdf2 = new File("");
//
//        MergePdf.mergePdf(pdf1, pdf2);

    }



}
