package org.berdzik.selenium.pages;

import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public static final String CHECKOUT_URL = BASE_PAGE_URL + "checkout/";
    public static final String CHECKOUT_TITLE = "CHECKOUT";

    public CheckoutPage(WebDriver driver) {
        super(driver);
        checkPageUrl(CHECKOUT_URL);
    }
}
