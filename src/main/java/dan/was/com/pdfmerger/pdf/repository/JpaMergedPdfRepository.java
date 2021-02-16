package dan.was.com.pdfmerger.pdf.repository;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMergedPdfRepository extends JpaRepository<MergedPdfModel, String> {
}
