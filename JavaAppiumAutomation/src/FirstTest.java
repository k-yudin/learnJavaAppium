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
import java.util.List;

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
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        waitForElementAndSendKeys(By.xpath(
                 "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        waitForElementPresence(By.xpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Object-oriented programming language",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        waitForElementAndClear(By.id(
                "org.wikipedia:id/search_src_text"),
                "Cannot find search input field",
        5
        );

        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Cannot find clear button",
                5
        );

        waitForElementNotPresent(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Clear button is still present",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Search Wikipedia input",
                5

        );

        WebElement title_element = waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals("Unexpected title!",
                "Java (programming language)",
                article_title);
    }

    @Test
    public void testCancelSearchWithAssertArticlesCount()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        waitForElementPresence(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find search results at all",
                5
        );

        List<WebElement> list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));

        Assert.assertTrue("Just 1 article was found!", list.size() > 1);

        waitForElementAndClear(By.id(
                "org.wikipedia:id/search_src_text"),
                "Cannot find search input field",
                5
        );

        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Cannot find clear button",
                5
        );

        waitForElementNotPresent(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Clear button is still present",
                5
        );
    }

    @Test
    public void checkWordsInSearchResults()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        waitForElementPresence(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find search results at all",
                5
        );

        List<WebElement> list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));

        for (WebElement item : list)
        {
            Assert.assertTrue("Search word is missing", item.getAttribute("text").contains("Java"));
        }
    }

    private WebElement waitForElementPresence(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresence(By by, String error_message)
    {
        return waitForElementPresence(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresence(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresence(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresence(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }
}
