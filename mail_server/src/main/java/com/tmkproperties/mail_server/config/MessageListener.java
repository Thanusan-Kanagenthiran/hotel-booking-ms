package com.tmkproperties.mail_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tmkproperties.mail_server.entity.Booking;
import com.tmkproperties.mail_server.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MessageListener {
    private final ObjectMapper objectMapper;
    @Autowired
    private PdfService pdfService;

    public MessageListener() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

    }

    @KafkaListener(topics = "booking",groupId = "booking-group" )
    public void listenAccounts(String data)  {
        try {
            Booking booking = objectMapper.readValue(data, Booking.class);
            String fileName = pdfService.generatePdf(booking);
            System.out.println(booking.getUserEmail() + fileName);
        } catch (Exception e) {
            System.err.println("Error parsing data: " + e.getMessage());
        }
    }
}
