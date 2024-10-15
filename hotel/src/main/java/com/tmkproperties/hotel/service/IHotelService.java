package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.HotelDto;
import com.tmkproperties.hotel.entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface IHotelService {

    void createHotel(HotelDto hotelDto);

    List<Hotel> getAllHotels();

    Hotel getHotelBySlug(String slug);

    Optional<Hotel> findById(Long hotelId);

    boolean updateHotel(Long hotelId, HotelDto hotelDto);

    boolean deleteHotel(Long hotelId);
}
