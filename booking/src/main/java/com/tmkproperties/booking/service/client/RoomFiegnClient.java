package com.tmkproperties.booking.service.client;

import com.tmkproperties.booking.dto.RoomResponseDtoWithDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel", fallback = RoomFiegnClientFallBack.class)
public interface RoomFiegnClient {

    @GetMapping(path = "api/v1/rooms/{id}")
    public ResponseEntity<RoomResponseDtoWithDetails> findById(@PathVariable Long id);

}
