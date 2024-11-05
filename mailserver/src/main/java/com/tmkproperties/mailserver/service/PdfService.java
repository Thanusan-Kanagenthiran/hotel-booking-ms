package com.tmkproperties.mailserver.service;

import com.itextpdf.html2pdf.HtmlConverter;

import com.tmkproperties.mailserver.entity.Booking;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    @Resource
    private TemplateEngine templateEngine;

    private final String outputDir = "./generated-pdfs/";

    public String generatePdf(Booking booking) throws IOException {

        new File(outputDir).mkdirs();

        Context context = new Context();
        context.setVariable("Booking", booking);

        String htmlContent = templateEngine.process("booking-confirmation", context);

        String fileName = booking.getId() + ".pdf";
        String filePath = outputDir + fileName;

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
        }

        return fileName;
    }
}