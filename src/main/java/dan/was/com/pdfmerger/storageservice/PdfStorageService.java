package dan.was.com.pdfmerger.storageservice;


import dan.was.com.pdfmerger.model.UploadedFileModel;
import dan.was.com.pdfmerger.pdfrepository.JpaPdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PdfStorageService {

    private static final Logger logger = LoggerFactory.getLogger(PdfStorageService.class);

    @Autowired
    private JpaPdfRepository jpaPdfRepository;


    public UploadedFileModel pdfSave(MultipartFile multipartFile) throws IOException {

        String pdfName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


        UploadedFileModel pdfFileModel = new UploadedFileModel
                (multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        multipartFile.getBytes());

        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getBytes());

        return jpaPdfRepository.save(pdfFileModel);
    }


}

