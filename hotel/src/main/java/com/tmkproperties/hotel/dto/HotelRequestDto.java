package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.HotelType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyDiscriminatorValues;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
    name = "HotelRequestDto",
    description = "Request object for creating a new hotel"
)
public class HotelRequestDto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Hotel type cannot be null")
   private HotelType hotelType;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Name cannot be empty")
    private String hotelName;

    @NotEmpty(message = "Location cannot be empty")
    @Size(min = 3, message = "Location must be at least 3 characters long")
    @Size(max = 50, message = "Location must be at most 50 characters long")
    private String hotelLocation;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 50, message = "Description must be at least 50 characters long")
    @Size(max = 500, message = "Description must be at most 500 characters long")
    private String hotelDescription;

    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
    private String hotelContactPhone;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String hotelContactEmail;

}
