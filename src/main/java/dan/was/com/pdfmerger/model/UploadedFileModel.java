package dan.was.com.pdfmerger.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "uploaded_pdf")
//lombok annotations


public class UploadedFileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getPdfData() {
        return pdfData;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }
}