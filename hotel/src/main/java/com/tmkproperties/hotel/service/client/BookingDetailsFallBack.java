package com.tmkproperties.hotel.service.client;

import com.tmkproperties.hotel.constants.BookingStatus;
import com.tmkproperties.hotel.dto.BookingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDetailsFallBack implements BookingFeignClient {

    @Override
    public ResponseEntity<List<BookingResponseDto>> findBookings(BookingStatus status, Long roomId, Long userId) {
       return null;
    }
}
