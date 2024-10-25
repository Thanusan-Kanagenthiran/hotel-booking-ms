package com.tmkproperties.hotel.service.implementation;

import com.tmkproperties.hotel.dto.*;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;
import com.tmkproperties.hotel.exception.ResourceAlreadyExistsException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.exception.UnauthorizedException;
import com.tmkproperties.hotel.mapper.RoomMapper;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.repository.RoomRepository;
import com.tmkproperties.hotel.service.IRoomService;
import com.tmkproperties.hotel.service.client.BookingFeignClient;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private  HotelRepository hotelRepository;
    private  RoomRepository roomRepository;
    private  BookingFeignClient bookingFeignClient;

    @Override
    public void createRoom(RoomRequestDto roomRequestDto, String email) {

        Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId()).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with requested id: " + roomRequestDto.getHotelId())
        );

        if(!hotel.getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to create room for this hotel");
        }

        Optional<Room> existingRoom = roomRepository.findByHotelAndRoomNumber(hotel, roomRequestDto.getRoomNumber());

        if (existingRoom.isPresent()) {
            throw new ResourceAlreadyExistsException("Room already exists with number: " + roomRequestDto.getRoomNumber() + " in hotel: " + hotel.getName());
        }

        Room room = RoomMapper.toRoom(roomRequestDto);
        room.setHotel(hotel);
        roomRepository.save(room);
    }

    @Override
    public List<RoomResponseDto> findAll() {
        List<Room> rooms = roomRepository.findAll();

        if (rooms.isEmpty()) {
            throw new ResourceNotFoundException("No rooms found.");
        }

        return rooms.stream()
                .map(RoomMapper::toRoomResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public RoomResponseDtoWithDetails findByIdWithDetails(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );
        return RoomMapper.toRoomResponseDtoWithDetail(room);
    }

    @Override
    public RoomResponseDtoWithBookings findByIdWithBookings(Long id, String email) {

        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if (room.getHotel().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to view bookings for this room.");
        }

        ResponseEntity<List<BookingResponseDto>> bookingResponseEntity = bookingFeignClient.findBookings(null, room.getId(), null);
        List<BookingResponseDto> bookings =
                (bookingResponseEntity != null && bookingResponseEntity.getBody() != null)
                        ? bookingResponseEntity.getBody()
                        : new ArrayList<>();
        return RoomMapper.toRoomResponseDtoWithBookings(room, bookings);
    }

    @Override
    public void updateRoom(Long id, RoomRequestDto roomRequestDto, String email) {

        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if (!existingRoom.getHotel().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to update this room.");
        }

        if (!existingRoom.getHotel().getId().equals(roomRequestDto.getHotelId())) {
            throw new BadRequestException("You cannot add the email is not same as hotel email. Room belongs to hotel ID: " + existingRoom.getHotel().getId());
        }

        Room updatedRoom = RoomMapper.toRoom(roomRequestDto);
        updatedRoom.setId(existingRoom.getId());
        roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id, String email) {
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if (!room.getHotel().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to delete this room.");
        }

        roomRepository.delete(room);
    }

}

