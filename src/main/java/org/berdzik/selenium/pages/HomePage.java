package org.berdzik.selenium.pages;

import org.berdzik.selenium.HeaderCategory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Optional;

public class HomePage extends ProductTilesPage {

    public HomePage(WebDriver driver) {
        super(driver);
        checkPageUrl(BASE_PAGE_URL);
    }

    public CategoryPage chooseCategory(HeaderCategory headerCategory) {
        moveToElement(categoryHeaderLink);
        Optional<WebElement> category = categories.stream()
                .filter(el -> el.getAttribute("title").equals(headerCategory.getName()))
                .findFirst();
        if (category.isEmpty()) {
            throw new IllegalStateException("Missing category: " + headerCategory.getName());
        }
        wait.until(ExpectedConditions.elementToBeClickable(category.get()));
        category.get().click();
        return new CategoryPage(webDriver, headerCategory.name());
    }
}
