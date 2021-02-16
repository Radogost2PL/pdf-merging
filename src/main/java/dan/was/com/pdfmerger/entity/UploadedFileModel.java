package dan.was.com.pdfmerger.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;

@Entity
@Table(name = "UPLOADED_PDF")

//lombok annotations
@Data
public class UploadedFileModel {
    @Id
    @Column(name = "PDF_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "PDF_NAME")
    private String pdfName;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "PDF_SIZE")
    private long size;
    @Lob
    @Column(name = "PDF_DATA")
    private byte[] pdfData;


    public UploadedFileModel() {
    }

    public UploadedFileModel(String pdfName, String fileType, Long size, byte[] pdfData) {
        this.pdfName = pdfName;
        this.fileType = fileType;
        this.pdfData = pdfData;
        this.size = size;

    }
}