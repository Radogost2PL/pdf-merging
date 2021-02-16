package dan.was.com.pdfmerger.storage.service;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.entity.UploadedFileModel;
import dan.was.com.pdfmerger.pdf.repository.JpaMergedPdfRepository;
import dan.was.com.pdfmerger.pdf.repository.JpaUploadedPdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class UploadedPdfDownloadService {

    @Autowired
    private JpaUploadedPdfRepository jpaUploadedPdfRepository;

    public UploadedFileModel getPdf(String id) {
        return jpaUploadedPdfRepository.findById(id).get();
    }
    public Stream<UploadedFileModel> getAllMergedPdfs() {
        return jpaUploadedPdfRepository.findAll().stream();
    }
}
