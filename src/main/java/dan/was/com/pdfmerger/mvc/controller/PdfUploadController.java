package dan.was.com.pdfmerger.mvc.controller;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.entity.UploadedFileModel;
import dan.was.com.pdfmerger.model.FormModel;
import dan.was.com.pdfmerger.pdf.manipulation.DeleteFiles;
import dan.was.com.pdfmerger.pdf.repository.JpaUploadedPdfRepository;
import dan.was.com.pdfmerger.pdf.service.MergePdf;
import dan.was.com.pdfmerger.pdf.repository.JpaMergedPdfRepository;
import dan.was.com.pdfmerger.storage.service.MergedPdfDownloadService;
import dan.was.com.pdfmerger.storage.service.PdfStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller

public class PdfUploadController {

    public static final String ATTACHMENT_FILENAME = "attachment; filename= ";
    @Autowired
    MergePdf mergePdf;
    @Autowired
    PdfStorageService pdfStorageService;
    @Autowired
    MergedPdfDownloadService mergedPdfDownloadService;
    @Autowired
    JpaMergedPdfRepository jpaMergedPdfRepository;
    @Autowired
    JpaUploadedPdfRepository jpaUploadedPdfRepository;
    @Autowired
    DeleteFiles deleteFiles;


    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUploadController.class);
    public static final String HEADER_KEY = ("ContentDisposition");


    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("formModel", new FormModel());
        return "main";
    }

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("pdf") MultipartFile pdf) {

        try {
            UploadedFileModel pdfFileModel = pdfStorageService.pdfSave(pdf);
        } catch (Exception e) {
            System.out.println(e);
        }
//        return "redirect:/";
        return "/pdflist";
    }

    @PostMapping("/uploadfiles")
    public String uploadMultiplePdfs(@Valid FormModel formModel,
                                     BindingResult bindingResult,
                                     @RequestParam("pdfs")
                                             MultipartFile[] pdfs) throws IOException {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        Arrays.asList(pdfs)
                .stream()
                .map(file -> uploadPdf(file))
                .collect(Collectors.toList());

        List<File> files = mergePdf.multipartArrayToFileList(pdfs);
        File merged = mergePdf.mergePDFFiles(files, formModel.getName());
        LOGGER.info("Is File Merged: " + merged.exists() + "Name:: " + merged.getName());
        pdfStorageService.mergedPdfsave(merged);
        deleteFiles.deleteTemp(merged);
        LOGGER.info("Is  Merged File existing after delete: " + merged.exists() + "Name:: " + merged.getName());

        return "redirect:/pdflist";
    }

    @GetMapping("/pdflist")
    public String mergedList(Model model) {
        List<MergedPdfModel> listPdf = jpaMergedPdfRepository.findAll();
        LOGGER.info("Is object in model? :" + listPdf.size() + "empty: " + listPdf.isEmpty());
        model.addAttribute("listPdf", listPdf);
        return "/pdflist";
    }

    @GetMapping("/download")
    public void downloadPdf(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        Optional<MergedPdfModel> mergedPdfModel = jpaMergedPdfRepository.findById(id);
        if (!mergedPdfModel.isPresent()) {
            throw new Exception("Can't find pdf with id: " + id);
        }
        MergedPdfModel mergedPdf = mergedPdfModel.get();
        response.setContentType("application/pdf");
        String headerValue = (ATTACHMENT_FILENAME + mergedPdf.getId());
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader(HEADER_KEY, headerValue);
        outputStream.write(mergedPdf.getData());
        outputStream.close();
    }

    @GetMapping("/uploadedlist")
    public String uploadedList(Model model) {
        List<UploadedFileModel> listPdf = jpaUploadedPdfRepository.findAll();
//        LOGGER.info("Is object in model? :" + listPdf.size() + "empty: " + listPdf.isEmpty());
        model.addAttribute("listPdf", listPdf);
        return "/uploadedpdflist";
    }

    @GetMapping("/downloadpdf")
    public void downloadUploadedPdf(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        Optional<UploadedFileModel> uploadedPdfModel = jpaUploadedPdfRepository.findById(id);
        if (!uploadedPdfModel.isPresent()) {
            throw new Exception("Can't find pdf with id: " + id);
        }
        UploadedFileModel uploadedFileModel = uploadedPdfModel.get();
        response.setContentType("application/pdf");
        String headerValue = (ATTACHMENT_FILENAME + uploadedFileModel.getId());
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader(HEADER_KEY, headerValue);
        outputStream.write(uploadedFileModel.getPdfData());
        outputStream.close();
    }
}

