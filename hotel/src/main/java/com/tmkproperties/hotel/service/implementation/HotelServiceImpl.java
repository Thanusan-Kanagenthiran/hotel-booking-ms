package com.tmkproperties.hotel.service.implementation;
import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.exception.ResourceAlreadyExistsException;
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
    public void createHotel(HotelRequestDto hotelRequestDto, String email) {
        Optional<Hotel> existingHotel = repository.findByName(hotelRequestDto.getName());

        if (existingHotel.isPresent()) {
            throw new ResourceAlreadyExistsException("Hotel already exists with name: " + hotelRequestDto.getName());
        }

        if(!hotelRequestDto.getEmail().equals(email)) {
            throw new  ResourceNotFoundException("Email mismatch. Please provide your account email.");
        }

        Hotel hotel = HotelMapper.toHotel(hotelRequestDto);
        repository.save(hotel);
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
    public List<HotelResponseDto> findAllByEmail(String email) {
        List<Hotel> hotels =repository.findByEmail(email);
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
    public List<HotelResponseDto> findAllByIdAndEmail(Long id, String email) {
        List<Hotel> hotels =repository.findAllByIdAndEmail(id,email);
        if (hotels.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found.");
        }
        return hotels.stream()
                .map(HotelMapper::toHotelResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updateHotel(Long id, HotelRequestDto hotelRequestDto, String email) {
         Hotel existingHotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + id)
        );

//      TODO :: Unauthorized Exception
        if (!existingHotel.getEmail().equals(email)) {
            throw new  ResourceNotFoundException("Hotel not found with id: ");
        }

        Hotel updatedHotel = HotelMapper.toHotel(hotelRequestDto);
        updatedHotel.setId(existingHotel.getId());
        updatedHotel.setEmail(email);
        repository.save(updatedHotel);
    }




    @Override
    public void deleteHotel(Long id, String email) {
        Hotel hotel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with id: " + id)
        );


//         TODO :: Unauthorized Exception
        if (!hotel.getEmail().equals(email)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + id);
        }
        repository.delete(hotel);
    }

}
