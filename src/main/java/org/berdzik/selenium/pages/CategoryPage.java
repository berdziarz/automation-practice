package org.berdzik.selenium.pages;

import org.berdzik.selenium.ProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoryPage extends ProductTilesPage {

    private static final By PRODUCT_TITLE = new By.ByCssSelector(".woocommerce-loop-product__title");

    private static final String CATEGORY_URL = BASE_PAGE_URL + "product-category/";

    @FindBy(css = ".products .product")
    List<WebElement> productsTiles;

    public CategoryPage(WebDriver driver, String categoryName) {
        super(driver);
        String pageUrl = CATEGORY_URL
                .concat(categoryName.toLowerCase())
                .concat("/");
        checkPageUrl(pageUrl);
        checkPageTitle(categoryName);
    }

    public ProductDetails addFirstProductToCart() {
        WebElement product = getFirstProductTile(productsTiles);
        ProductDetails productDetails = getDetailsFromProductTile(product);
        clickAddToCartOnProductTile(product);
        return productDetails;
    }

    private ProductDetails getDetailsFromProductTile(WebElement productTile) {
        return ProductDetails.builder()
                .price(getProductPrice(productTile))
                .name(getProductTitle(productTile))
                .build();
    }

    private double getProductPrice(WebElement productTile) {
        return getPriceFromText(productTile.findElement(PRODUCT_PRICE).getText());
    }

    private String getProductTitle(WebElement productTile) {
        return productTile.findElement(PRODUCT_TITLE).getText().replaceAll("\u2013", "-");
    }
}
