package com.tmkproperties.hotel.service.implementation;

import com.tmkproperties.hotel.dto.RoomRequestDto;
import com.tmkproperties.hotel.dto.RoomResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;
import com.tmkproperties.hotel.exception.BadRequestException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.exception.RoomNumberAlreadyExistsException;
import com.tmkproperties.hotel.mapper.RoomMapper;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.repository.RoomRepository;
import com.tmkproperties.hotel.service.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Override
    public void createRoom(RoomRequestDto roomRequestDto) {

        Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId()).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId())
        );

        Optional<Room> existingRoom = roomRepository.findByHotelAndRoomNumber(hotel, roomRequestDto.getRoomNumber());

        if (existingRoom.isPresent()) {
            throw new RoomNumberAlreadyExistsException("Room already exists with number: " + roomRequestDto.getRoomNumber() + " in hotel: " + hotel.getName());
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
    public void updateRoom(Long id, RoomRequestDto roomRequestDto) {

        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if (!existingRoom.getHotel().getId().equals(roomRequestDto.getHotelId())) {
            String errorMessage = "Cannot change hotel ID for this room. Room belongs to hotel ID: " + existingRoom.getHotel().getId();
            throw new BadRequestException(errorMessage);
        }

        Room updatedRoom = RoomMapper.toRoom(roomRequestDto);
        updatedRoom.setId(existingRoom.getId());
        roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );
        roomRepository.delete(room);
    }
}