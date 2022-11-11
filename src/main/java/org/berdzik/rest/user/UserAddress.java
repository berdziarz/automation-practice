package org.berdzik.rest.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class UserAddress {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geolocation geo;
}
