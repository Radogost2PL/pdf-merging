package dan.was.com.pdfmerger;

import dan.was.com.pdfmerger.pdf.manipulation.DeleteFiles;
import dan.was.com.pdfmerger.pdf.repository.JpaMergedPdfRepository;
import dan.was.com.pdfmerger.pdf.repository.JpaUploadedPdfRepository;
import dan.was.com.pdfmerger.pdf.service.MergePdf;
import dan.was.com.pdfmerger.storage.service.MergedPdfDownloadService;
import dan.was.com.pdfmerger.storage.service.PdfStorageService;
import dan.was.com.pdfmerger.storage.service.UploadedPdfDownloadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class MvcLayerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MergePdf mergePdf;
    @MockBean
    private PdfStorageService pdfStorageService;
    @MockBean
    private MergedPdfDownloadService mergedPdfDownloadService;
    @MockBean
    private UploadedPdfDownloadService uploadedPdfDownloadService;
    @MockBean
    private JpaMergedPdfRepository jpaMergedPdfRepository;
    @MockBean
    private JpaUploadedPdfRepository jpaUploadedPdfRepository;
    @MockBean
    private DeleteFiles deleteFiles;


    @Test
    public void defaultResponse() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }
}
