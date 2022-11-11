package org.berdzik.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Jacksonized
public class Post extends Resource {

    private long userId;
    private String title;
    private String body;
}
