package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.RoomDto;
import com.tmkproperties.hotel.entity.Room;

import java.util.List;

public interface IRoomService {
    List<Room> getAllRoomsByHotelId(Long hotelId);

    void createRoom(RoomDto roomDto, Long hotelId);
}
