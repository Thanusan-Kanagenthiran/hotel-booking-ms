package com.tmkproperties.gateway_server.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> resourceAccess = (Map<String, Object>) source.getClaims().get("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey("tmk-hotel-ms")) {
            return new ArrayList<>();
        }

        Map<String, Object> tmkHotelMs = (Map<String, Object>) resourceAccess.get("tmk-hotel-ms");
        if (tmkHotelMs == null || !tmkHotelMs.containsKey("roles")) {
            return new ArrayList<>();
        }

        List<String> roles = (List<String>) tmkHotelMs.get("roles");
        return roles.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
