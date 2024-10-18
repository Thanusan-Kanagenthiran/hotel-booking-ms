package com.tmkproperties.hotel.service.client;

import com.tmkproperties.hotel.constants.BookingStatus;
import com.tmkproperties.hotel.dto.BookingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("booking")
public interface BookingFeignClient {
    @GetMapping("api/v1/bookings")
    public ResponseEntity<List<BookingResponseDto>> findBookings(
           @RequestParam(required = false) BookingStatus status,
           @RequestParam(required = false) Long roomId,
           @RequestParam(required = false) Long userId);
}
