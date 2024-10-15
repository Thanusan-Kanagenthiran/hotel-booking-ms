package com.tmkproperties.hotel.service.implementation;

import com.tmkproperties.hotel.dto.HotelDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.exception.HotelAlreadyExistException;
import com.tmkproperties.hotel.exception.ResourceNotFoundException;
import com.tmkproperties.hotel.repository.HotelRepository;
import com.tmkproperties.hotel.service.IHotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements IHotelService {

    private HotelRepository hotelRepository;

    @Override
    public void createHotel(HotelDto hotelDto) {

        Hotel hotel = new Hotel();

        if (hotelRepository.existsByName(hotelDto.getName())) {
            throw new HotelAlreadyExistException("Hotel with name " + hotelDto.getName() + " already exists.");
        }

        if(hotelRepository.existsByEmail(hotelDto.getEmail())) {
            throw new HotelAlreadyExistException("Hotel with email " + hotelDto.getEmail() + " already exists.");
        }

        if(hotelRepository.existsByPhone(hotelDto.getPhone())) {
            throw new HotelAlreadyExistException("Hotel with phone " + hotelDto.getPhone() + " already exists.");
        }

        hotel.setHotelType(hotelDto.getHotelType());
        hotel.setName(hotelDto.getName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setEmail(hotelDto.getEmail());
        hotel.setPhone(hotelDto.getPhone());
        hotel.setOwnerId(0L);

        Hotel savedHotel = hotelRepository.save(hotel);

    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelBySlug(String slug) {
        return hotelRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("No Hotel found with this URL: " + slug));
    }

    public Optional<Hotel> findById(Long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);

        if (hotelOptional.isPresent()) {
            return hotelOptional;
        } else {
            throw new ResourceNotFoundException("Hotel not found with ID: " + hotelId);
        }
    }

    @Override
    public boolean updateHotel(Long hotelId,HotelDto hotelDto) {

        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);

        if (hotelOptional.isPresent()) {

            Hotel hotel = hotelOptional.get();

            hotel.setHotelType(hotelDto.getHotelType());
            hotel.setName(hotelDto.getName());
            hotel.setLocation(hotelDto.getLocation());
            hotel.setDescription(hotelDto.getDescription());
            hotel.setEmail(hotelDto.getEmail());
            hotel.setPhone(hotelDto.getPhone());

            hotelRepository.save(hotel);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteHotel(Long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);

        if (hotelOptional.isPresent()) {
            hotelRepository.delete(hotelOptional.get());
            return true;
        } else {
            return false;
        }
    }
}
