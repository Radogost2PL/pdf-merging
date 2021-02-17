package dan.was.com.pdfmerger.pdf.service;

import dan.was.com.pdfmerger.file.converter.ConvertFiles;
import dan.was.com.pdfmerger.pdf.manipulation.DeleteFiles;
import dan.was.com.pdfmerger.pdf.manipulation.GenerateCreditsPage;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MergePdf {

    @Autowired
    ConvertFiles convertFiles;
    @Autowired
    GenerateCreditsPage generateCreditsPage;
    @Autowired
    DeleteFiles deleteFiles;


    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    private static final Logger LOGGER = LoggerFactory.getLogger(MergePdf.class);


    public List<File> multipartArrayToFileList(MultipartFile[] multipartFiles) throws IOException {

        List<MultipartFile> multipartFilesList = Arrays.asList(multipartFiles);
        List<File> files = new ArrayList<>();

        LOGGER.info("Is multipartFilesList empty: " + multipartFilesList.isEmpty());

        for (MultipartFile file : multipartFilesList) {
//            files.add(convertMultipartToFile(file));
            files.add(convertFiles.convertMultipartToFile(file));
        }

        LOGGER.info(String.valueOf("MultipartFilesList size: " + multipartFilesList.size()));
        return files;
    }

    public File mergePDFFiles(List<File> files,
                              String mergedFileName) throws IOException {

//TODO check later if it is possible to merge without using temp directory in pdfbox?

        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        LOGGER.info("Is " + newDirectory + " exists before mkdir method: " + newDirectory.exists());

        //creates directory for merged pdf
        if (!newDirectory.mkdir()) {
            LOGGER.info("Is " + newDirectory + "a DIRECTORY: " + newDirectory.isDirectory());

        }

        LOGGER.info("Is " + newDirectory + " exists:  " + newDirectory.exists());

        String absolutePath = newDirectory.getAbsolutePath();
        PDFMergerUtility pdfmerger = new PDFMergerUtility();
        pdfmerger.setDestinationFileName(absolutePath + "/" + mergedFileName + ".pdf");

        try {
            for (File file : files) {
                PDDocument document = PDDocument.load(file);
                LOGGER.info("Where is file: " + file.getAbsolutePath());
                pdfmerger.addSource(file);
                document.close();
            }
            pdfmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
            LOGGER.info("Os temp directory: " + System.getProperty("java.io.tmpdir"));
        } catch (IOException e) {
            LOGGER.error("Error while merging files: " + e.getMessage());
        }
        String destinationFileName = pdfmerger.getDestinationFileName();
        generateCreditsPage.printPage(destinationFileName);
        try {
            for (File file : files) {
                deleteFiles.deleteTemp(file);
            }
        } catch (Exception e) {
            LOGGER.error("Can't delete files: " + e);
        }
        files.forEach(file -> LOGGER.info("Does " + file + "exist after deleting " + file.exists()));

        return new File(pdfmerger.getDestinationFileName());
    }
}






