package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.HotelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    @Enumerated(EnumType.STRING)
    private HotelType hotelType;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotEmpty(message = "Description cannot be empty")
    private String description;


    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
    private String phone;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

}
