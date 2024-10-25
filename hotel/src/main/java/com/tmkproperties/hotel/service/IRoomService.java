package com.tmkproperties.hotel.service;

import com.tmkproperties.hotel.dto.*;
import jakarta.validation.Valid;
import java.util.List;

public interface IRoomService {
    void createRoom(@Valid RoomRequestDto roomRequestDto, String email);

    List<RoomResponseDto> findAll();

    RoomResponseDtoWithBookings findByIdWithBookings(Long id, String email);

    RoomResponseDtoWithDetails findByIdWithDetails(Long id);

    void updateRoom(Long id,RoomRequestDto roomRequestDto, String email);

    void deleteRoom(Long id, String email);
}
