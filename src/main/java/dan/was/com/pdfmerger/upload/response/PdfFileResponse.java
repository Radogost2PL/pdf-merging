package dan.was.com.pdfmerger.upload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

