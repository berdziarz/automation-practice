package org.berdzik.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public abstract class ProductTilesPage extends BasePage {

    protected static final By PRODUCT_PRICE = new By.ByCssSelector("span>.woocommerce-Price-amount, ins>.woocommerce-Price-amount");
    protected static final By PRODUCT_ADD_TO_CART = new By.ByCssSelector(".add_to_cart_button");
    protected static final By PRODUCT_VIEW_CART = new By.ByCssSelector(".added_to_cart");

    public ProductTilesPage(WebDriver driver) {
        super(driver);
    }

    protected void clickAddToCartOnProductTile(WebElement productTile) {
        productTile.findElement(PRODUCT_ADD_TO_CART).click();
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(productTile, PRODUCT_VIEW_CART));
    }

    protected WebElement getFirstProductTile(List<WebElement> productsTiles) {
        if (productsTiles.isEmpty()) {
            throw new IllegalStateException("No products tiles on page");
        }
        return productsTiles.get(0);
    }
}
