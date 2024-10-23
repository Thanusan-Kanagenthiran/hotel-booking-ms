package com.tmkproperties.hotel.service.implementation;

import com.tmkproperties.hotel.dto.*;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;
import com.tmkproperties.hotel.exception.BadRequestException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.exception.ResourceAlreadyExistsException;
import com.tmkproperties.hotel.mapper.RoomMapper;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.repository.RoomRepository;
import com.tmkproperties.hotel.service.IRoomService;
import com.tmkproperties.hotel.service.client.BookingFeignClient;
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
                () -> new ResourceNotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId())
        );

        if(!hotel.getEmail().equals(email)) {
            throw new BadRequestException("Cannot create room for this hotel");
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
    public RoomResponseDto findById(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );
        return RoomMapper.toRoomResponseDto(room);
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
            ResponseEntity<List<BookingResponseDto>> bookingResponseEntity = bookingFeignClient.findBookings(null, room.getId(), null);
            List<BookingResponseDto> bookings =
                    (bookingResponseEntity != null && bookingResponseEntity.getBody() != null)
                            ? bookingResponseEntity.getBody()
                            : new ArrayList<>();
            return RoomMapper.toRoomResponseDtoWithBookings(room, bookings);
        }

        return RoomMapper.toRoomResponseDtoWithBookings(room, new ArrayList<>());
    }

    @Override
    public void updateRoom(Long id, RoomRequestDto roomRequestDto, String email) {

        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        // TODO:: Unauthorized Exception
        if (!existingRoom.getHotel().getEmail().equals(email)) {
            throw new BadRequestException("You are not authorized to update this room.");
        }

        if (!existingRoom.getHotel().getId().equals(roomRequestDto.getHotelId())) {
            String errorMessage = "Cannot change hotel ID for this room. Room belongs to hotel ID: " + existingRoom.getHotel().getId();
            throw new BadRequestException(errorMessage);
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
        // TODO:: Unauthorized Exception
        if (!room.getHotel().getEmail().equals(email)) {
            throw new BadRequestException("You are not authorized to delete this room.");
        }

        roomRepository.delete(room);
    }

}

