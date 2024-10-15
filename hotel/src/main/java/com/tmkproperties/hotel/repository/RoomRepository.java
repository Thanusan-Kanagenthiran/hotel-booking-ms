package com.tmkproperties.hotel.repository;

import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomsByHotel_HotelId(Long hotelId);
}
