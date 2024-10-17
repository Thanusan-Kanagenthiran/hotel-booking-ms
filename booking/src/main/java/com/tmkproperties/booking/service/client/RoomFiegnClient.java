package com.tmkproperties.booking.service.client;

import com.tmkproperties.booking.dto.RoomResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("hotel")
public interface RoomFiegnClient {

    @GetMapping(path = "api/v1/rooms/{id}")
    public ResponseEntity<RoomResponseDto> findById(@PathVariable Long id);
}
