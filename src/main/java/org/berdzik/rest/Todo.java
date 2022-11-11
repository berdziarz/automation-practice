package org.berdzik.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Jacksonized
public class Todo extends Resource {

    private long userId;
    private String title;
    private boolean completed;
}
