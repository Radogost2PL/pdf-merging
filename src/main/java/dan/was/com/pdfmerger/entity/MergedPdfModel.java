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

//    @NotBlank
//    @Size(min = 5, max = 35)
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
