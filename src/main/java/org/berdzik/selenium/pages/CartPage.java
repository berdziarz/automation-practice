package org.berdzik.selenium.pages;

import org.berdzik.selenium.ProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private static final By PRODUCT_TITLE = new By.ByCssSelector(".product-name");
    private static final By PRODUCT_PRICE = new By.ByCssSelector(".product-price");
    private static final By PRODUCT_REMOVE = new By.ByCssSelector(".remove");

    private static final String CART_URL = "cart/";
    private static final String CART_TITLE = "CART";

    @FindBy(css = ".cart_item")
    List<WebElement> products;

    @FindBy(css = ".checkout-button")
    WebElement checkout;

    public CartPage(WebDriver driver) {
        super(driver);
        checkPageUrl(BASE_PAGE_URL.concat(CART_URL));
        checkPageTitle(CART_TITLE);
    }

    public List<ProductDetails> getProductsInCart() {
        return products.stream().map(this::getProductDetails).collect(Collectors.toList());
    }

    private ProductDetails getProductDetails(WebElement product) {
        return ProductDetails.builder()
                .name(getProductTitle(product))
                .price(getProductPrice(product)).build();
    }

    public void removeFirstProduct() {
        checkIfCartIsNotEmpty();
        products.get(0).findElement(PRODUCT_REMOVE).click();
    }

    private void checkIfCartIsNotEmpty() {
        if (products.isEmpty()) {
            throw new IllegalStateException("No products in cart");
        }
    }

    private double getProductPrice(WebElement productTile) {
        return getPriceFromText(productTile.findElement(PRODUCT_PRICE).getText());
    }

    private String getProductTitle(WebElement productTile) {
        return productTile.findElement(PRODUCT_TITLE).getText();
    }

    public CheckoutPage processCheckout() {
        checkIfCartIsNotEmpty();
        checkout.click();
        return new CheckoutPage(webDriver);

    }
}
