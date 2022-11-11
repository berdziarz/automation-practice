package org.berdzik.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Jacksonized
public class Photo extends Resource {

    private long albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}
