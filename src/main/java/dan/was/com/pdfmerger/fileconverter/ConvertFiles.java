package dan.was.com.pdfmerger.fileconverter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ConvertFiles {
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    Logger logger = LoggerFactory.getLogger(ConvertFiles.class);

    public File convertMultipartToFile(MultipartFile file) throws IOException {

        File uploadedPdfDirectory = new File(TEMP_DIRECTORY, "uploaded_directory");

        if (!uploadedPdfDirectory.mkdir()) {
            logger.info("Is " + uploadedPdfDirectory + "a DIRECTORY: " + uploadedPdfDirectory.isDirectory());
        }

        File convertedFile = new File(uploadedPdfDirectory, file.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());
        logger.info("Where is converted file: " + convertedFile.getAbsolutePath());
        fileOutputStream.close();
        return convertedFile;
    }
}
