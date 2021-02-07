package dan.was.com.pdfmerger.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "UPLOADED_PDF")

//lombok annotations
@Setter
@Getter
public class UploadedFileModel {
    @Id
    @Column(name = "PDF_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")

    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] pdfData;


    public UploadedFileModel() {
    }

    public UploadedFileModel(String fileName, String fileType, byte[] pdfData) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.pdfData = pdfData;

    }
}