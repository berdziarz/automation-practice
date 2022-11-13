package org.berdzik.selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected static WebDriver webDriver;
    protected static WebDriverWait wait;
    protected static final String BASE_PAGE_URL = "http://skleptest.pl/";

    @FindBy(css = ".page-title")
    WebElement pageTitle;

    @FindBy(css = "#desktop-menu [title='Catergries']")
    WebElement categoryHeaderLink;

    @FindBy(css = "#desktop-menu .dropdown-menu a")
    List<WebElement> categories;

    @FindBy(css = ".top-cart a")
    WebElement topCart;

    public BasePage(WebDriver driver) {
        webDriver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(webDriver, this);
    }

    protected void checkPageUrl(String expectedUrl) {
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(expectedUrl)) {
            throw new IllegalStateException(String.format("Wrong page url. Should be %s but is %s ", expectedUrl, currentUrl));
        }
    }

    protected void checkPageTitle(String expectedTitle) {
        String currentTitle = getPageTitle();
        if (!currentTitle.equals(expectedTitle)) {
            throw new IllegalStateException(String.format("Wrong page title. Should be %s but is %s ", expectedTitle, currentTitle));
        }
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    protected void moveToElement(WebElement element) {
        Actions action = new Actions(webDriver);
        action.moveToElement(element).perform();
    }

    public double getCartValue() {
        return getPriceFromText(topCart.getText());
    }

    protected double getPriceFromText(String text) {
        return Double.parseDouble(text.replaceAll("[^\\d.]", ""));
    }

    @Step("Go to cart")
    public CartPage goToCart() {
        topCart.click();
        return new CartPage(webDriver);
    }
}
