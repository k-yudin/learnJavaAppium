import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/konstantin/learnJavaAppium/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){ driver.quit();}

    @Test
    public void firstTest()
    {
        waitForElementByXPathAndClick("//*[contains(@text, 'Search Wikipedia')]",
                "Cannot find Search Wikipedia input",
                5

        );

        WebElement search_field = waitForElementPresenceByXPath("//*[@resource-id='org.wikipedia:id/search_src_text']",
                "Cannot find search input field",
                5
        );

        String search_field_text = search_field.getAttribute("text");

        Assert.assertEquals("Search field doesn't have expected value",
                "Search…",
                search_field_text
        );
    }

    private WebElement waitForElementPresenceByXPath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresenceByXPath(String xpath, String error_message)
    {
        return waitForElementPresenceByXPath(xpath, error_message, 5);
    }

    private WebElement waitForElementByXPathAndClick(String xpath, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresenceByXPath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementByXPathAndSendKeys(String xpath, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresenceByXPath(xpath, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
}
