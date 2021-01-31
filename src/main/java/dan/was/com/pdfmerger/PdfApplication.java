package dan.was.com.pdfmerger;

import dan.was.com.pdfmerger.pdfmanipulationservice.MergePdf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories
public class PdfApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(PdfApplication.class, args);



    }



}
