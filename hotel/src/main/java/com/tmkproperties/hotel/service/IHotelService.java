package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface IHotelService {

    void createHotel(@Valid HotelRequestDto hotelRequestDto, String email);

    List<HotelResponseDto> findAll();

    List<HotelResponseDto> findAllByEmail(String email);

    HotelResponseDto findById(Long id);

    void updateHotel(Long id, @Valid HotelRequestDto hotelRequestDto, String email);

    void deleteHotel(Long id, String email);

    HotelResponseDto findByIdAndEmail(Long id, String email);
}
