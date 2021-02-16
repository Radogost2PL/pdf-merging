package dan.was.com.pdfmerger.uploadresponse;

import dan.was.com.pdfmerger.storageservice.PdfStorageService;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PdfFileResponse {

    //TODO to implement in the future
    private String pdfName;
    private String id;
//    private String fileType;
    private String pdfDownloadUrl;
    private long size;

//    public PdfFileResponse() {
//    }
}

