package com.tmkproperties.mail_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/v1/receipts")
@Tag(
        name = "Booking Receipts API",
        description = "Operations related to booking receipts"
)
public class PdfController {

    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Operation(
            summary = "Download booking receipt",
            description = "Download booking receipt with the given file name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "PDF not found"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) {
        logger.info("Received request to download PDF: {}", fileName);

        File file = new File("./generated-pdfs/" + fileName);
        if (!file.exists()) {
            logger.warn("PDF not found: {}", fileName);
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        logger.info("PDF found: {}", fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

        logger.info("Preparing response with PDF: {}", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
