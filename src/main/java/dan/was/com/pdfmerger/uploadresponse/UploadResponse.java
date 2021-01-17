package dan.was.com.pdfmerger.uploadresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadResponse {

    private String pdfName;
    private String pdfDownloadUrl;
    private long size;



}
