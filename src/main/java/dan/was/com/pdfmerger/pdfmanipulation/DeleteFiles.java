package dan.was.com.pdfmerger.pdfmanipulation;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DeleteFiles {

    public static void deleteTemp(File file) {
        file.delete();
    }
}
