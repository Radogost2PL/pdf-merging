package dan.was.com.pdfmerger.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//persistence annotations
@Entity
@Table(name = "MERGED_PDF")

//Lombok annotations
@Data

public class MergedPdfModel {


    @Id
    @Column(name = "PDF_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "PDF_NAME")
    private String pdfName;
    //    private String fileType;
    @Column(name = "PDF_SIZE")
    private long size;

    @Lob
    @Column(name = "PDF_DATA")
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
