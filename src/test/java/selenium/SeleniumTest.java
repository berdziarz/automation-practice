package selenium;

import org.assertj.core.api.SoftAssertions;
import org.berdzik.selenium.WebDriverFactory;
import org.berdzik.selenium.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class SeleniumTest {

    private WebDriver webdriver;
    protected SoftAssertions softly;

    @BeforeMethod
    public void setUp() {
        webdriver = WebDriverFactory.initChromeDriver();
        softly = new SoftAssertions();
    }

    @AfterMethod
    public void tearDown() {
        webdriver.quit();
    }

    protected HomePage loadHomePage() {
        webdriver.get("http://skleptest.pl");
        webdriver.manage().window().maximize();
        return new HomePage(webdriver);
    }
}
