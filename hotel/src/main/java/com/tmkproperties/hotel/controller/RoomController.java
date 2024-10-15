package com.tmkproperties.hotel.controller;


import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.dto.RoomDto;
import com.tmkproperties.hotel.entity.Room;
import com.tmkproperties.hotel.service.IRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Validated
public class RoomController {

    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/{hotelId}")
    public ResponseEntity<List<RoomDto>> getAllRooms(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getAllRoomsByHotelId(hotelId);

        List<RoomDto> roomDtos = rooms
                .stream()
                .map(room -> RoomDto
                        .builder()
                        .roomType(room.getRoomType())
                        .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                        .pricePerNight(room.getPricePerNight())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(roomDtos);
    }



    @PostMapping("/{hotelId}")
    public ResponseEntity<ResponseDto> createRoom(@Valid @RequestBody RoomDto roomDto, @PathVariable Long hotelId) {
        roomService.createRoom(roomDto, hotelId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Room added successfully."));
    }


//    @GetMapping("/{roomId}")
//    public String getRoom() {
//        return "Get Room";
//    }
//
//    @PutMapping("/{roomId}")
//    public String updateRoom() {
//        return "Update Room";
//    }
//
//    @DeleteMapping("/{roomId}")
//    public String deleteRoom() {
//        return "Delete Room";
//    }
//


}
