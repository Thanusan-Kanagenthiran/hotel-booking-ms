package com.tmkproperties.hotel.service.implementation;
import com.tmkproperties.hotel.constants.HotelType;
import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.exception.ResourceAlreadyExistsException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.mapper.HotelMapper;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.service.IHotelService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository repository;

    @Override
    public void createHotel(HotelRequestDto hotelRequestDto, String email) {
        Optional<Hotel> existingHotel = repository.findByHotelName(hotelRequestDto.getHotelName());

        if (existingHotel.isPresent()) {
            throw new ResourceAlreadyExistsException("Hotel with name " + hotelRequestDto.getHotelName() + " already exists.");
        }

        if(!hotelRequestDto.getHotelContactEmail().equals(email)) {
            throw new  ResourceNotFoundException("Email mismatch. Please provide your email.");
        }

        if (hotelRequestDto.getHotelType() == null ||
                !(hotelRequestDto.getHotelType() == HotelType.LUXURY ||
                        hotelRequestDto.getHotelType() == HotelType.BOUTIQUE ||
                        hotelRequestDto.getHotelType() == HotelType.RESORT ||
                        hotelRequestDto.getHotelType() == HotelType.HOME_STAY)) {

            throw new ValidationException("Invalid hotel type. Allowed types are: " + Arrays.toString(HotelType.values()));
        }

        Hotel hotel = HotelMapper.toHotel(hotelRequestDto);
        repository.save(hotel);
    }


    @Override
    public List<HotelResponseDto> findAll() {
        List<Hotel> hotels = repository.findAll();
        return hotels.stream()
                .map(HotelMapper::toHotelResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponseDto> findAllByEmail(String email) {
        List<Hotel> hotels =repository.findByHotelContactEmail(email);
        return hotels.stream()
                .map(HotelMapper::toHotelResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public HotelResponseDto findById(Long id) {
        Hotel hotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found. ")
        );
        return HotelMapper.toHotelResponseDto(hotel);
    }

    @Override
    public HotelResponseDto findByIdAndEmail(Long id, String email) {
        Hotel hotel =repository.findByIdAndHotelContactEmail(id,email);

        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel not belongs to this user. ");
        }

        return HotelMapper.toHotelResponseDto(hotel);
    }


    @Override
    public void updateHotel(Long id, HotelRequestDto hotelRequestDto, String email) {

        Hotel hotel =repository.findByIdAndHotelContactEmail(id,email);

        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel not belongs to this user. ");
        }

        Hotel updatedHotel = HotelMapper.toHotel(hotelRequestDto);
        updatedHotel.setId(hotel.getId());
        updatedHotel.setHotelName(email);
        repository.save(updatedHotel);
    }


    @Override
    public void deleteHotel(Long id, String email) {
        Hotel hotel =repository.findByIdAndHotelContactEmail(id,email);

        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel not belongs to this user. ");
        }

        repository.delete(hotel);
    }

}
