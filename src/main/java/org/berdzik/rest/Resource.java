package org.berdzik.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class Resource {

    protected long id;
}
