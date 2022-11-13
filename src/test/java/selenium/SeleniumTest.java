package selenium;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.berdzik.selenium.WebDriverFactory;
import org.berdzik.selenium.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

@Epic("Selenium tests")
public abstract class SeleniumTest {

    private WebDriver webdriver;
    protected SoftAssertions softly;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            webdriver = WebDriverFactory.initChromeDriver();
        } else {
            webdriver = WebDriverFactory.initFirefoxDriver();
        }
        softly = new SoftAssertions();
    }

    @AfterMethod
    public void tearDown() {
        webdriver.quit();
    }

    @Step("Load home page")
    protected HomePage loadHomePage() {
        webdriver.get("http://skleptest.pl");
        webdriver.manage().window().maximize();
        return new HomePage(webdriver);
    }

    public WebDriver getDriver() {
        return webdriver;
    }
}
