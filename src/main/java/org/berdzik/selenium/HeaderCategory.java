package org.berdzik.selenium;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor
public enum HeaderCategory {
    ALL,
    SHIRTS,
    FEATURED,
    TRENDS,
    SCARFS,
    SHOES,
    TOPS,
    BLOUSE,
    JEANS,
    DRESSES;

    public String getName() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
