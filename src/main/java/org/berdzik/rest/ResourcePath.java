package org.berdzik.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourcePath {

    ALBUMS("/albums"),
    COMMENTS("/comments"),
    PHOTOS("/photos"),
    POSTS("/posts"),
    TODOS("/todos"),
    USERS("/users");

    private String path;
}
