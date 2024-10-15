package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.HotelDto;
import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.dto.RoomDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.service.IHotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@Validated
public class HotelController {


    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        hotelService.createHotel(hotelDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Hotel created successfully."));
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();

        List<HotelDto> hotelDtos = hotels
                .stream()
                .map(hotel -> HotelDto
                        .builder()
                        .name(hotel.getName())
                        .description(hotel.getDescription())
                        .location(hotel.getLocation())
                        .hotelType(hotel.getHotelType())
                        .rooms(
                                hotel.getRooms()
                                        .stream()
                                        .map(room -> RoomDto.builder()
                                                .roomType(room.getRoomType())
                                                .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                                                .pricePerNight(room.getPricePerNight())
                                                .build())
                                        .toList())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(hotelDtos);
    }


    @GetMapping("/{slug}")
    public ResponseEntity<Hotel> getHotelBySlug(@PathVariable String slug) {
        Hotel hotel = hotelService.getHotelBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("/host/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @PutMapping("/host/{hotelId}")
    public ResponseEntity<ResponseDto> updateHotel(@Valid @RequestBody HotelDto hotelDto,@PathVariable Long hotelId) {

        boolean isUpdated = hotelService.updateHotel(hotelId,hotelDto);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpStatus.CREATED, "Record updated successfully."));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, "Update operation failed. Please try again or contact support."));
        }
    }

    @DeleteMapping("/host/{hotelId}")
    public ResponseEntity<ResponseDto> deleteHotel(@PathVariable Long hotelId) {

        boolean isUpdated = hotelService.deleteHotel(hotelId);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpStatus.CREATED, "Record deleted successfully."));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, "Delete operation failed. Please try again or contact support."));
        }
    }

}
