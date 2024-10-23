    package com.tmkproperties.booking.service.client;
    import com.tmkproperties.booking.dto.RoomResponseDtoWithDetails;
    import com.tmkproperties.booking.exception.ResourceNotFoundException;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;

    @Component
    public class RoomFiegnClientFallBack implements RoomFiegnClient {
        @Override
        public ResponseEntity<RoomResponseDtoWithDetails> findById(Long id) {
            throw new ResourceNotFoundException("Something went wrong. Please try again later.");
        }
    }
