package dan.was.com.pdfmerger.controller;

import dan.was.com.pdfmerger.model.MergedPdfModel;
import dan.was.com.pdfmerger.model.UploadedFileModel;
import dan.was.com.pdfmerger.pdfmanipulationservice.MergePdf;
import dan.was.com.pdfmerger.storageservice.PdfStorageService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;


import java.util.List;
import java.util.stream.Collectors;


@Controller

public class PdfController {

    @Autowired
    MergePdf mergePdf;
    @Autowired
    PdfStorageService pdfStorageService;


    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);


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
    public String uploadMultiplePdfs(@RequestParam("pdfs") MultipartFile[] pdfs) throws IOException {


        Arrays.asList(pdfs)
                .stream()
                .map(file -> uploadPdf(file))
                .collect(Collectors.toList());


        List<File> files = mergePdf.multipartToFileList(pdfs);


        File merged = mergePdf.mergePDFFiles(files, "merged");

        System.out.println("Is File Merged: " + merged.exists() + "Name:: " + merged.getName());

        pdfStorageService.mergedPdfsave(merged);

        return "redirect:/";
    }


}

