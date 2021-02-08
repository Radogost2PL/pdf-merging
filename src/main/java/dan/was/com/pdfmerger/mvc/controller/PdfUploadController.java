package dan.was.com.pdfmerger.mvc.controller;

import dan.was.com.pdfmerger.entity.MergedPdfModel;
import dan.was.com.pdfmerger.entity.UploadedFileModel;
import dan.was.com.pdfmerger.model.FormModel;
import dan.was.com.pdfmerger.pdfmanipulationservice.DeleteFiles;
import dan.was.com.pdfmerger.pdfmanipulationservice.MergePdf;
import dan.was.com.pdfmerger.pdfrepository.JpaMergedPdfRepository;
import dan.was.com.pdfmerger.storageservice.PdfDownloadService;
import dan.was.com.pdfmerger.storageservice.PdfStorageService;
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

    @Autowired
    MergePdf mergePdf;
    //    @Autowired
//    FormModel formModel;
    @Autowired
    PdfStorageService pdfStorageService;
    @Autowired
    PdfDownloadService pdfDownloadService;
    @Autowired
    JpaMergedPdfRepository jpaMergedPdfRepository;


    private static final Logger logger = LoggerFactory.getLogger(PdfUploadController.class);
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
                                     BindingResult bindingResult, @RequestParam("pdfs") MultipartFile[] pdfs) throws IOException {

        if (bindingResult.hasErrors()) {
            return "main";
        }
        System.out.println("@@######$$$$$$$" + formModel.getName());


        Arrays.asList(pdfs)
                .stream()
                .map(file -> uploadPdf(file))
                .collect(Collectors.toList());


        List<File> files = mergePdf.multipartToFileList(pdfs);

        File merged = mergePdf.mergePDFFiles(files, formModel.getName());
        logger.info("Is File Merged: " + merged.exists() + "Name:: " + merged.getName());

        pdfStorageService.mergedPdfsave(merged);
        DeleteFiles.deleteTemp(merged);

        logger.info("Is  Merged File existing after delete: " + merged.exists() + "Name:: " + merged.getName());
//        }
        return "redirect:/pdflist";
    }

    @GetMapping("/pdflist")
    public String mergedList(Model model) {
        List<MergedPdfModel> listPdf = jpaMergedPdfRepository.findAll();
        logger.info(String.valueOf("Is object in model? :" + listPdf.size() + "empty: " + listPdf.isEmpty()));
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

        String headerValue = ("attachment; filename= " + mergedPdf.getId());

        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader(HEADER_KEY, headerValue);

        outputStream.write(mergedPdf.getData());
        outputStream.close();

    }

}

