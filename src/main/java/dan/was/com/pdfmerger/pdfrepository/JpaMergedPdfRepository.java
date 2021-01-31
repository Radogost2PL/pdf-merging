package dan.was.com.pdfmerger.pdfrepository;

import dan.was.com.pdfmerger.model.MergedPdfModel;
import dan.was.com.pdfmerger.model.UploadedFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMergedPdfRepository extends JpaRepository<MergedPdfModel, Integer> {
}
