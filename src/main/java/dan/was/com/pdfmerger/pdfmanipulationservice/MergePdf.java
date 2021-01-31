package dan.was.com.pdfmerger.pdfmanipulationservice;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MergePdf {

    //    public final static String DIR = "/home/rancor/Pulpit/pdf test/upload/";
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));
    Logger logger = LoggerFactory.getLogger(MergePdf.class);

//TODO extract converting methods to separate class amd package

    public File convertMultipart(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public List<File> multipartToFileList(MultipartFile[] multipartFiles) throws IOException {

        List<MultipartFile> multipartFilesList = Arrays.asList(multipartFiles);
        List<File> files = new ArrayList<>();

        logger.info(String.valueOf("Is multipartFilesList empty:: " + multipartFilesList.isEmpty()));

        for (MultipartFile file : multipartFilesList) {
            files.add(convertMultipart(file));
        }

        logger.info(String.valueOf("MultipartFilesList size: " + multipartFilesList.size()));
        return files;
    }

    public File mergePDFFiles(List<File> files,
                              String mergedFileName) {

//TODO check later if it is possible to merge without using temp directory in pdfbox

        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        logger.info(String.valueOf("Is " + newDirectory + " exists before mkdir method: " + newDirectory.exists()));

        //creates directory
        if (!newDirectory.mkdir()) {
            logger.info("Is " + newDirectory + "a DIRECTORY: " + String.valueOf(newDirectory.isDirectory()));

        }

        logger.info(String.valueOf("Is " + newDirectory + " exists:  " + newDirectory.exists()));

        String absolutePath = newDirectory.getAbsolutePath();
        PDFMergerUtility pdfmerger = new PDFMergerUtility();
        pdfmerger.setDestinationFileName(absolutePath + "/merged.pdf");

        try {
            for (File file : files) {
                PDDocument document = PDDocument.load(file);
                pdfmerger.addSource(file);
                document.close();
            }

            pdfmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

            logger.info("Os temp directory: " + System.getProperty("java.io.tmpdir"));


        } catch (IOException e) {
            logger.error("Error while merging files: " + e.getMessage());
        }
        return new File(pdfmerger.getDestinationFileName());
    }
}






