package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.ErrorResponseDto;
import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.service.implementation.HotelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@Validated
@RequiredArgsConstructor
@Tag(
    name = "Hotels",
    description = "Hotels Rest API Endpoints for create, get all and get by id"
)
@Slf4j
public class HotelController {

    private final HotelServiceImpl service;

    @Operation(
        summary = "Get all hotels",
        description = "Get all hotels"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<HotelResponseDto> hotels = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @Operation(
        summary = "Get a hotel by id",
        description = "Get a hotel by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> findById(@PathVariable Long id) {
        HotelResponseDto hotel = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @Operation(
        summary = "Get all hotels by host email",
        description = "Get all hotels by email for a host"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/host")
    public ResponseEntity<List<HotelResponseDto>> findAllByEmail(@AuthenticationPrincipal Jwt principal ) {
        String email = principal.getClaimAsString("sub");
        List<HotelResponseDto> hotels = service.findAllByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @Operation(
        summary = "Create a hotel",
        description = "Create a hotel for a host"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/host")
    public ResponseEntity<ResponseDto> createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto,@AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.createHotel(hotelRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Hotel created successfully."));
    }

    @Operation(
        summary = "Get a hotel by id and host email",
        description = "Get a hotel by id and host email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/host/{id}")
    public ResponseEntity<HotelResponseDto> findByEmailAndId(@PathVariable Long id,@AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        HotelResponseDto hotel = service.findByIdAndEmail(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @Operation(
        summary = "Update a hotel booked dates",
        description = "Update a hotel for a host based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/host/{id}")
    public ResponseEntity<ResponseDto> updateHotel(
            @Valid @RequestBody HotelRequestDto hotelRequestDto, @PathVariable Long id, @AuthenticationPrincipal Jwt principal  ) {
        String email = principal.getClaimAsString("sub");
        service.updateHotel(id, hotelRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }

    @Operation(
        summary = "Delete a hotel by id",
        description = "Delete a hotel by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/host/{id}")
    public ResponseEntity<ResponseDto> deleteHotel(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.deleteHotel(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }

}
