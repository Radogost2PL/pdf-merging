package dan.was.com.pdfmerger.storageservice;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.pdfrepository.JpaMergedPdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class PdfDownloadService {

    @Autowired
    private JpaMergedPdfRepository jpaMergedPdfRepository;

    public MergedPdfModel getPdf(String id) {
        return jpaMergedPdfRepository.findById(id).get();
    }
    public Stream<MergedPdfModel> getAllMergedPdfs() {
        return jpaMergedPdfRepository.findAll().stream();
    }
}
