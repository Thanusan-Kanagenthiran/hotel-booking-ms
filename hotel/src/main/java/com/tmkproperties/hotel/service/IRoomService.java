package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface IRoomService {

    void createRoom(@Valid RoomRequestDto roomRequestDto);

    List<RoomResponseDto> findAll();

    RoomResponseDto findById(Long id);

    void updateRoom(Long id, @Valid RoomRequestDto roomRequestDto);

    void deleteRoom(Long id);
}
