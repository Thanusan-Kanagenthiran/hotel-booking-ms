package com.tmkproperties.booking.service.client;

import com.tmkproperties.booking.dto.RoomResponseDtoWithDetails;
import com.tmkproperties.booking.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ReceiptFiegnClientFallBack implements ReceiptFiegnClient {
    @Override
    public ResponseEntity<Resource> downloadPdf(String fileName) {
        throw new ResourceNotFoundException("Something went wrong. Please try again later.");
    }
}
