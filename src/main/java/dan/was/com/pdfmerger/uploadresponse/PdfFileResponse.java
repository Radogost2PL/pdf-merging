package dan.was.com.pdfmerger.uploadresponse;

import dan.was.com.pdfmerger.storageservice.PdfStorageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PdfFileResponse {

    //TODO to implement in the future
    private String pdfName;
    private String pdfDownloadUrl;
    private String id;
    private long size;

}
