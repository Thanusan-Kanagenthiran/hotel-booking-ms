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

}