package org.berdzik.rest.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class Company {

    private String name;
    private String catchPhrase;
    private String bs;
}
