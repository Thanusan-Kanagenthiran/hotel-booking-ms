package com.tmkproperties.booking.constants;

import com.tmkproperties.booking.exception.BadRequestException;

import java.util.Arrays;

public enum BookingStatus {
    CONFIRMED,
    CANCELLED,
    REJECTED,
    PENDING,
    CHECKED_IN,
    CHECKED_OUT; // This represents the end of the user's stay

    public static void isValidStatus(String status) {
        boolean isValid = Arrays.stream(BookingStatus.values())
                .anyMatch(bookingStatus -> bookingStatus.name().equalsIgnoreCase(status));

        if (!isValid) {
            throw new BadRequestException("Invalid status: " + status);
        }
    }

}