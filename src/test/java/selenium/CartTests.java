package selenium;

import io.qameta.allure.Feature;
import org.berdzik.selenium.HeaderCategory;
import org.berdzik.selenium.ProductDetails;
import org.berdzik.selenium.pages.CartPage;
import org.berdzik.selenium.pages.CategoryPage;
import org.berdzik.selenium.pages.CheckoutPage;
import org.berdzik.selenium.pages.HomePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners({ScreenshotListener.class})
@Feature("Cart tests")
public class CartTests extends SeleniumTest {

    @Test
    public void addProductToCart() {
        HomePage homePage = loadHomePage();
        CategoryPage categoryPage = homePage.chooseCategory(HeaderCategory.JEANS);
        double cartValue = categoryPage.getCartValue();
        ProductDetails addedProductDetails = categoryPage.addFirstProductToCart();
        softly.assertThat(cartValue).as("Cart value after adding product")
                .isEqualTo(categoryPage.getCartValue() + addedProductDetails.getPrice());
        CartPage cartPage = categoryPage.goToCart();
        List<ProductDetails> productsInCart = cartPage.getProductsInCart();
        assertThat(productsInCart).as("Number of products in cart").hasSize(1);
        softly.assertThat(productsInCart.get(0)).as("Products details in cart").usingRecursiveComparison().isEqualTo(addedProductDetails);
        softly.assertAll();
    }

    @Test
    public void removeProductFromCartByRemoveButton() {
        HomePage homePage = loadHomePage();
        CategoryPage categoryPage = homePage.chooseCategory(HeaderCategory.JEANS);
        categoryPage.addFirstProductToCart();
        CartPage cartPage = categoryPage.goToCart();
        List<ProductDetails> productsInCart = cartPage.getProductsInCart();
        double cartValue = cartPage.getCartValue();
        cartPage.removeFirstProduct();
        softly.assertThat(cartPage.getProductsInCart()).as("Cart should be empty").isEmpty();
        softly.assertThat(cartPage.getCartValue()).as("Cart value after removing product")
                .isEqualTo(cartValue - productsInCart.get(0).getPrice());
        softly.assertAll();
    }

    @Test
    public void processThePurchaseToTheCheckoutForm() {
        HomePage homePage = loadHomePage();
        CategoryPage categoryPage = homePage.chooseCategory(HeaderCategory.JEANS);
        categoryPage.addFirstProductToCart();
        CartPage cartPage = categoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.processCheckout();
        assertThat(checkoutPage.getPageTitle()).isEqualTo(CheckoutPage.CHECKOUT_TITLE);
    }
}
