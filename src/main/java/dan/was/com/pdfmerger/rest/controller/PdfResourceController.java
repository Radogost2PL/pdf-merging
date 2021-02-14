package dan.was.com.pdfmerger.rest.controller;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.storageservice.MergedPdfDownloadService;
import dan.was.com.pdfmerger.uploadresponse.PdfFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
public class PdfResourceController {

    public static final Function<MergedPdfModel, PdfFileResponse> MERGED_PDF_MODEL_PDF_FILE_RESPONSE_FUNCTION =
            mergedPdf -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/mergedpdf/")
                        .path(mergedPdf.getId())
                        .toUriString();

                return new PdfFileResponse(
                        mergedPdf.getPdfName(),
                        mergedPdf.getId(),
//                        mergedPdf.getFileType(),
                        fileDownloadUri,
                        mergedPdf.getData().length);
            };

    @Autowired
    MergedPdfDownloadService mergedPdfDownloadService;

    @GetMapping("/mergedpdf")
    public ResponseEntity<List<PdfFileResponse>> getAllMergedPdfs() {
        List<PdfFileResponse> files = mergedPdfDownloadService.getAllMergedPdfs().
                map(MERGED_PDF_MODEL_PDF_FILE_RESPONSE_FUNCTION).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/mergedpdf/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String id) {
        MergedPdfModel mergedPdfModel = mergedPdfDownloadService.getPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + mergedPdfModel.getPdfName() + "\"")
                .body(mergedPdfModel.getData());
    }
}

