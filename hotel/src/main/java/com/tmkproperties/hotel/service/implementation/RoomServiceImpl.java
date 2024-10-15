package com.tmkproperties.hotel.service.implementation;

import com.tmkproperties.hotel.dto.RoomDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.repository.RoomRepository;
import com.tmkproperties.hotel.service.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;

    @Override
    public List<Room> getAllRoomsByHotelId(Long hotelId) {
        return roomRepository.findRoomsByHotel_HotelId(hotelId);
    }

    @Override
    public void createRoom(RoomDto roomDto, Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomType(roomDto.getRoomType());
        room.setMaximumNumberOfGuests(roomDto.getMaximumNumberOfGuests());
        room.setPricePerNight(roomDto.getPricePerNight());
        roomRepository.save(room);
    }
}
