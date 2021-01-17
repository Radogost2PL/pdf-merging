package dan.was.com.pdfmerger.pdfrepository;

import dan.was.com.pdfmerger.model.UploadedFileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPdfRepository extends JpaRepository<UploadedFileModel, Integer> {
}
