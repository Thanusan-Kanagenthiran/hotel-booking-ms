package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.service.implementation.HotelServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@Validated
@RequiredArgsConstructor
@Tag(
    name = "Hotels",
    description = "Hotels API"
)
public class HotelController {

    private final HotelServiceImpl service;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<HotelResponseDto> hotels = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> findById(@PathVariable Long id) {
        HotelResponseDto hotel = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("/host")
    public ResponseEntity<List<HotelResponseDto>> findAllByEmail(@RequestParam String email) {
        List<HotelResponseDto> hotels = service.findAllByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @PostMapping("/host")
    public ResponseEntity<ResponseDto> createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto, @RequestParam String email) {
        service.createHotel(hotelRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Hotel created successfully."));
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<List<HotelResponseDto>> findAllByEmailAndId(@PathVariable Long id,@RequestParam String email) {
        List<HotelResponseDto> hotels = service.findAllByIdAndEmail(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @PutMapping("/host/{id}")
    public ResponseEntity<ResponseDto> updateHotel(
            @Valid @RequestBody HotelRequestDto hotelRequestDto, @PathVariable Long id, @RequestParam String email) {
        service.updateHotel(id, hotelRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }

    @DeleteMapping("/host/{id}")
    public ResponseEntity<ResponseDto> deleteHotel(@PathVariable Long id, @RequestParam String email) {
        service.deleteHotel(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }

}
