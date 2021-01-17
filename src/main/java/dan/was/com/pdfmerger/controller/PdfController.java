package dan.was.com.pdfmerger.controller;

import dan.was.com.pdfmerger.model.UploadedFileModel;
import dan.was.com.pdfmerger.storageservice.PdfStorageService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;


import java.util.Arrays;


import java.util.stream.Collectors;

@Controller
public class PdfController {
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    PdfStorageService pdfStorageService;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("pdf") MultipartFile pdf) {
        try {
            UploadedFileModel pdfFileModel = pdfStorageService.pdfSave(pdf);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/";

    }

    @PostMapping("/uploadfiles")
    public String uploadMultiplePdfs(@RequestParam("pdfs") MultipartFile[] pdfs) {

        Arrays.asList(pdfs)
                .stream()
                .map(file -> uploadPdf(file))
                .collect(Collectors.toList());

        return "redirect:/";
    }


}

