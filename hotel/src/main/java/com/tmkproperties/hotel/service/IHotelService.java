package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface IHotelService {

    void createHotel(@Valid HotelRequestDto hotelRequestDto);

    List<HotelResponseDto> findAll();

    HotelResponseDto findById(Long id);

    void updateHotel(Long id, @Valid HotelRequestDto hotelRequestDto);

    void deleteHotel(Long id);
}
