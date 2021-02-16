package dan.was.com.pdfmerger.storage.service;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.pdf.repository.JpaMergedPdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class MergedPdfDownloadService {

    @Autowired
    private JpaMergedPdfRepository jpaMergedPdfRepository;

    public MergedPdfModel getPdf(String id) {
        return jpaMergedPdfRepository.findById(id).get();
    }
    public Stream<MergedPdfModel> getAllMergedPdfs() {
        return jpaMergedPdfRepository.findAll().stream();
    }
}
