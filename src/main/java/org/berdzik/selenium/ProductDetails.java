package org.berdzik.selenium;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetails {

    private double price;
    private String name;
}
