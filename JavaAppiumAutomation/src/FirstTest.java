import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

    @Test
    public void testSwipeArticle()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input field",
                5
        );

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search results",
                5

        );

        waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        swipeUpToFindElement(By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of an article",
                20);
    }

    @Test
    public void saveFirstArticleToMyList()
    {
        String name_of_folder = "Learning programming";

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

        waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                5);

        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find got it button",
                5);

        waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input for article folder",
                5);

        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot find confirm button",
                5);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close an article",
                5);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find my list button",
                5);

        waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5);

        swipeElementToTheLeft(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);
    }

    @Test
    public void testSaveTwoArticles()
    {
        String name_of_folder = "Languages";

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input field",
                15
        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                15
        );

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find expected search result",
                15
        );

        waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                15);

        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                15);

        waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find got it button",
                15);

        waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input for article folder",
                15);

        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot find confirm button",
                15);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close button for an article",
                15);


        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "JavaScript",
                "Cannot find search input field",
                15
        );

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
                "Cannot find expected search result",
                15
        );

        waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                15);

        waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                15);

        waitForElementAndClick(By.xpath("//*[@text='Languages']"),
                "Cannot find saved folder",
                15);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find butto to close an article",
                15);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find my list button",
                15);

        waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                15);

        swipeElementToTheLeft(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                15);

        WebElement title_list_element  = waitForElementPresence(By.id(("org.wikipedia:id/page_list_item_title")),
                "Cannot find second article",
                15);

        String list_item_title = title_list_element.getAttribute("text");

        waitForElementAndClick(By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find second article to click",
                5);

        WebElement article_title_element = waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        String article_title = article_title_element.getAttribute("text");

        Assert.assertEquals("Titles don't match!",
                list_item_title,
                article_title);
    }

    @Test
    public void testAmmountOfNotEmptySearch()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "Linkin Park Diskography";
        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input field",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresence(By.xpath(search_result_locator),
                "Cannot find anything by request " + search_result_locator,
        15);

        int ammount_fo_search_results = getAmmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue("We found too few results", ammount_fo_search_results > 0);
    }

    @Test
    public void testAmmountOfEmptySearch()
    {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "rtertyuurwr";
        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input field",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresence(By.xpath(
                empty_result_label),
                "Cannot find empty result label" + search_line,
                15
        );

        assertElementNotPresent(By.xpath(search_result_locator),
                "We found result by request: " + search_line);
    }

    @Test
    public void testTitleElementPresent()
    {
        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String search_request = "Java";
        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input field",
                5);

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' search result",
                5);

        String title_locator = "org.wikipedia:id/view_page_title_text";
        assertElementPresent(By.id(title_locator),
                "There is no title for an article by request: " + title_locator);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String search_request = "Java";
        waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input field",
                5);

        waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' search result",
                15);

        String title_before_rotation = waitForElementAndGetAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

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

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();

    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (already_swiped > max_swipes)
            {
                waitForElementPresence(by, "Cannoot find element by swipe up \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToTheLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresence(by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message)
    {
        int ammount_of_elements = getAmmountOfElements(by);
        if (ammount_of_elements > 0)
        {
            String default_message = "An element '" + by.toString() + "' supposed to be not present.";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

    private void assertElementPresent(By by, String error_message)
    {
        int ammount_of_elements = getAmmountOfElements(by);
        if (ammount_of_elements == 0)
        {
            String default_message = "An element '" + by.toString() + "' supposed to be present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresence(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
