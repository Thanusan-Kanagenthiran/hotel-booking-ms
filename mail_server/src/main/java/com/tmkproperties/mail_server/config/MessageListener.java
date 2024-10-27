package com.tmkproperties.mail_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tmkproperties.mail_server.entity.Booking;
import com.tmkproperties.mail_server.service.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private final ObjectMapper objectMapper;
    private final PdfService pdfService;

    public MessageListener(PdfService pdfService) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.pdfService = pdfService;
    }

    @KafkaListener(topics = "booking", groupId = "booking-group")
    public void listenAccounts(String data) {
        try {
            Booking booking = objectMapper.readValue(data, Booking.class);
            String fileName = pdfService.generatePdf(booking);
            logger.info("Booking processed for user: {}, PDF generated: {}", booking.getGuestContactEmail(), fileName);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Error parsing JSON data: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
        }
    }
}
