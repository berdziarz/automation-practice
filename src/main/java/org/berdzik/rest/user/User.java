package org.berdzik.rest.user;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.berdzik.rest.Resource;

@SuperBuilder
@Getter
@Jacksonized
public class User  extends Resource {

    private String name;
    private String username;
    private String email;
    private UserAddress address;
    private String phone;
    private String website;
    private Company company;
}
