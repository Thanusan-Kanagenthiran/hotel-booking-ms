package com.tmkproperties.hotel.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("booking")
public interface BookingFeignClient {
}
