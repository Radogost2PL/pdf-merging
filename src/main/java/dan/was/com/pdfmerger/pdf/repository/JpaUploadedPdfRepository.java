package dan.was.com.pdfmerger.pdf.repository;

import dan.was.com.pdfmerger.entity.UploadedFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUploadedPdfRepository extends JpaRepository<UploadedFileModel, String> {
}
