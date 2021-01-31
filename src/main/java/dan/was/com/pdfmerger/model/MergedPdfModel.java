package dan.was.com.pdfmerger.model;

import com.sun.xml.bind.v2.TODO;
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

    private String pdfName;

    @Lob
    private byte[] Data;

    //TODO later add foreign keys from uploaded pdfs into merged pdf
//    @ManyToOne(targetEntity = UploadedFileModel.class)
//    @JoinColumn(name = "PDF_ID")
//    private int PDFs;

    public MergedPdfModel() {
    }

    public MergedPdfModel(String name, byte[] data) {
        this.pdfName = name;
        Data = data;
    }
}
