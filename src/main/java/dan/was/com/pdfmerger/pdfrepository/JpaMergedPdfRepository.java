package dan.was.com.pdfmerger.pdfrepository;

import dan.was.com.pdfmerger.model.MergedPdfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMergedPdfRepository extends JpaRepository<MergedPdfModel, String> {
}
