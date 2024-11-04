package com.tmkproperties.booking.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mailserver", fallback = ReceiptFiegnClientFallBack.class)
public interface ReceiptFiegnClient {

    @GetMapping(path = "/api/v1/receipts/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName);

}