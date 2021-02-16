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
    private String fileName;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Lob
    @Column(name = "PDF_DATA")
    private byte[] pdfData;


    public UploadedFileModel() {
    }

    public UploadedFileModel(String fileName, String fileType, byte[] pdfData) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.pdfData = pdfData;

    }
}