package dan.was.com.pdfmerger.pdfmanipulationservice;

import java.io.File;

public class DeleteFiles {

    public static void deleteTemp(File file) {
        file.delete();
    }
}
