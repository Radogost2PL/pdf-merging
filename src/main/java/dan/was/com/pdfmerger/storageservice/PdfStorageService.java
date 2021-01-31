package dan.was.com.pdfmerger.storageservice;


import dan.was.com.pdfmerger.model.MergedPdfModel;
import dan.was.com.pdfmerger.model.UploadedFileModel;
import dan.was.com.pdfmerger.pdfrepository.JpaMergedPdfRepository;
import dan.was.com.pdfmerger.pdfrepository.JpaUploadedPdfRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfStorageService {

    private static final Logger logger = LoggerFactory.getLogger(PdfStorageService.class);

    @Autowired
    private JpaUploadedPdfRepository jpaUploadedPdfRepository;
    @Autowired
    private JpaMergedPdfRepository mergedPdfRepository;


    public MergedPdfModel mergedPdfsave(File file) throws IOException {

        MockMultipartFile multipartFile = null;
        try {
            if (file.exists()) {
                logger.info("File Exists : " + file.getName() + " |path: " + file.getAbsolutePath());
            }
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file", file.getName(),
                    "application/pdf", IOUtils.toByteArray(input));

            logger.info("Is multipartFileEmpty? : " + multipartFile.isEmpty() + " |name " +
                    multipartFile.getOriginalFilename() + " |bytes " + multipartFile.getBytes() + "|size" + multipartFile.getSize());

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }

        MergedPdfModel mergedPdfModel =
                new MergedPdfModel(multipartFile.getOriginalFilename(), multipartFile.getBytes());

        logger.info("MergedPdfModel name:" + mergedPdfModel.getPdfName() +
                " Bytes: " + mergedPdfModel.getData() + "Pdf name: " + mergedPdfModel.getPdfName());

        mergedPdfRepository.save(mergedPdfModel);

        return null;
    }


    public UploadedFileModel pdfSave(MultipartFile multipartFile) throws IOException {

        String pdfName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


        UploadedFileModel pdfFileModel = new UploadedFileModel
                (multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        multipartFile.getBytes());
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getBytes());
        jpaUploadedPdfRepository.save(pdfFileModel);

        List<String> ids = new ArrayList<>();
        ids.add(pdfFileModel.getId());


        ids.forEach(i -> logger.info("IDs of saved pdfs: " + i));
        return null;
    }
}





