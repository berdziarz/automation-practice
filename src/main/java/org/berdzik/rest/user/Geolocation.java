package org.berdzik.rest.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class Geolocation {

    private double lat;
    private double lng;
}
