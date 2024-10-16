package com.tmkproperties.hotel.service.implementation;
import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.exception.HotelAlreadyExistException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.mapper.HotelMapper;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.service.IHotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository repository;

    @Override
    public void createHotel(HotelRequestDto hotelRequestDto) {
        Optional<Hotel> existingHotel = repository.findByNameOrPhoneOrEmail(hotelRequestDto.getEmail(), hotelRequestDto.getPhone(), hotelRequestDto.getName());

        if (existingHotel.isPresent()) {
            Hotel hotel = existingHotel.get();
            String email = hotelRequestDto.getEmail();
            String phone = hotelRequestDto.getPhone();
            String name = hotelRequestDto.getName();

            if (hotel.getEmail().equals(email)) {
                throw new HotelAlreadyExistException("Hotel with email " + email + " already exists.");
            }
            if (hotel.getPhone().equals(phone)) {
                throw new HotelAlreadyExistException("Hotel with phone " + phone + " already exists.");
            }
            if (hotel.getName().equals(name)) {
                throw new HotelAlreadyExistException("Hotel with name " + name + " already exists.");
            }
        }

        repository.save(HotelMapper.toHotel(hotelRequestDto));
    }


    @Override
    public List<HotelResponseDto> findAll() {
        List<Hotel> hotels = repository.findAll();

        if (hotels.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found.");
        }
        return hotels.stream()
                .map(HotelMapper::toHotelResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponseDto findById(Long id) {
        Hotel hotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + id)
        );
        return HotelMapper.toHotelResponseDto(hotel);
    }


    @Override
    public void updateHotel(Long id, HotelRequestDto hotelRequestDto) {
        Hotel existingHotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + id)
        );

        Hotel updatedHotel = HotelMapper.toHotel(hotelRequestDto);
        updatedHotel.setId(existingHotel.getId());
        repository.save(updatedHotel);
    }


    @Override
    public void deleteHotel(Long id) {
        Hotel hotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + id)
        );
        repository.delete(hotel);
    }
}
