package dan.was.com.pdfmerger.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//persistence annotations
@Entity
@Table(name = "MERGED_PDF")

//Lombok annotations
@Getter
@Setter

public class MergedPdfModel {


    @Id
    @Column(name = "MERGED_PDF_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Lob
    private byte[] Data;

    @ManyToOne(targetEntity = UploadedFileModel.class)
    @JoinColumn(name = "PDF_ID")
    private int PDF;

    public MergedPdfModel() {
    }

    public MergedPdfModel(String name, byte[] data) {
        this.name = name;
        Data = data;
    }
}
