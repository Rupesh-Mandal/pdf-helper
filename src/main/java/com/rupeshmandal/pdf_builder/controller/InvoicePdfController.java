package com.rupeshmandal.pdf_builder.controller;

import com.itextpdf.text.DocumentException;
import com.rupeshmandal.pdf_builder.service.InvoicePdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoicePdfController {

    @Autowired
    InvoicePdfService invoicePdfService;


    @GetMapping
    public ResponseEntity<Void> downloadInvoice(HttpServletResponse response)
            throws IOException {

        var resut= invoicePdfService.generateInvoice();
        byte[] pdfReport = resut.toByteArray();
        String mimeType =  "application/pdf";
        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + "invoice.pdf"+"\""));

        response.setContentLength((int)pdfReport.length);

        InputStream inputStream = new ByteArrayInputStream(pdfReport);

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        return new ResponseEntity<Void>(HttpStatus.OK) ;
    }


}
