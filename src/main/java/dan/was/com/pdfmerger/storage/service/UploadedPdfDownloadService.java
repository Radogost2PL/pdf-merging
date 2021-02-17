package dan.was.com.pdfmerger.storage.service;

import dan.was.com.pdfmerger.entity.UploadedFileModel;
import dan.was.com.pdfmerger.pdf.repository.JpaUploadedPdfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UploadedPdfDownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadedPdfDownloadService.class);

    @Autowired
    private JpaUploadedPdfRepository jpaUploadedPdfRepository;

    public UploadedFileModel getPdf(String id) throws Exception {
        Optional<UploadedFileModel> uploadedPdfModel = jpaUploadedPdfRepository.findById(id);

        if (!uploadedPdfModel.isPresent()) {
            throw new Exception("Can't find pdf with id: " + id);
        }
        return jpaUploadedPdfRepository.findById(id).get();
    }

    public List<UploadedFileModel> getAllUploadedPdfs() throws Exception {
        Optional<List> pdfList = Optional.of(jpaUploadedPdfRepository.findAll());
        LOGGER.info("IS STREAM PRESENT: "+pdfList.isPresent());
        if (!pdfList.isPresent()) {
            throw new Exception("Problem with pdf list");
        }

        return jpaUploadedPdfRepository.findAll();
    }
}
