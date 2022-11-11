package org.berdzik.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Jacksonized
public class Comment extends Resource {

    private long postId;
    private String email;
    private String name;
    private String body;
}
