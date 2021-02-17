package dan.was.com.pdfmerger.storage.service;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.pdf.repository.JpaMergedPdfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MergedPdfDownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergedPdfDownloadService.class);
    @Autowired
    private JpaMergedPdfRepository jpaMergedPdfRepository;

    public MergedPdfModel getPdf(String id) throws Exception {
        Optional<MergedPdfModel> mergedPdfModel = jpaMergedPdfRepository.findById(id);
        if (!mergedPdfModel.isPresent()) {
            throw new Exception("Can't find pdf with id: " + id);
        }
        return jpaMergedPdfRepository.findById(id).get();
    }


    public List<MergedPdfModel> getAllMergedPdfs() throws Exception {
        Optional<List> pdfList = Optional.of(jpaMergedPdfRepository.findAll());
        LOGGER.info("IS STREAM PRESENT: "+pdfList.isPresent());
        if (!pdfList.isPresent()) {
            throw new Exception("Problem with pdf list");
        }
        return jpaMergedPdfRepository.findAll();
    }
}
