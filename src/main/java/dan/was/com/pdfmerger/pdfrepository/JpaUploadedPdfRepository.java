package dan.was.com.pdfmerger.pdfrepository;

import dan.was.com.pdfmerger.model.UploadedFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUploadedPdfRepository extends JpaRepository<UploadedFileModel, Integer> {
}
