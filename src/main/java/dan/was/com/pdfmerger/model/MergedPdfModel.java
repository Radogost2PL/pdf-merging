package dan.was.com.pdfmerger.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//persistence annotations
@Entity
@Table(name = "MERGED_PDF")

//Lombok annotations
@Getter
@Setter

public class MergedPdfModel {


    @Id
    @Column(name = "PDF_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String pdfName;

    private String fileType;
    private long size;

    @Lob
    private byte[] Data;

    //TODO later add foreign keys from uploaded pdfs into merged pdf
//    @ManyToOne(targetEntity = UploadedFileModel.class)
//    @JoinColumn(name = "PDF_ID")
//    private int PDFs;

    public MergedPdfModel() {
    }

    public MergedPdfModel(String name, byte[] data, Long size) {
        this.pdfName = name;
        this.size = size;
        Data = data;
    }
}
