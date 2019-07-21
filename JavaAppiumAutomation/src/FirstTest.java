import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementPresence(By.xpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Object-oriented programming language",
                15
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        mainPageObject.waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String search_request = "Java";
        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input field",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' search result",
                15);

        String title_before_rotation = mainPageObject.waitForElementAndGetAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        try {
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_rotation = mainPageObject.waitForElementAndGetAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        Assert.assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_rotation);

        try {
            driver.rotate(ScreenOrientation.PORTRAIT);
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_second_rotation = mainPageObject.waitForElementAndGetAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        Assert.assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation);
    }

    @Test
    public void testCancelSearch() {
        mainPageObject.waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClear(By.id(
                "org.wikipedia:id/search_src_text"),
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClick(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Cannot find clear button",
                5
        );

        mainPageObject.waitForElementNotPresent(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Clear button is still present",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Search Wikipedia input",
                5

        );

        WebElement title_element = mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals("Unexpected title!",
                "Java (programming language)",
                article_title);
    }

    @Test
    public void testCancelSearchWithAssertArticlesCount() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementPresence(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find search results at all",
                5
        );

        List<WebElement> list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));

        Assert.assertTrue("Just 1 article was found!", list.size() > 1);

        mainPageObject.waitForElementAndClear(By.id(
                "org.wikipedia:id/search_src_text"),
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClick(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Cannot find clear button",
                5
        );

        mainPageObject.waitForElementNotPresent(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Clear button is still present",
                5
        );
    }

    @Test
    public void testCheckWordsInSearchResults() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementPresence(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find search results at all",
                5
        );

        List<WebElement> list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));

        for (WebElement item : list) {
            Assert.assertTrue("Search word is missing", item.getAttribute("text").contains("Java"));
        }
    }

    @Test
    public void testSwipeArticle() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5

        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search results",
                5

        );

        mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        mainPageObject.swipeUpToFindElement(By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of an article",
                20);
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        String name_of_folder = "Learning programming";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Search Wikipedia input",
                5
        );

        mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                5);

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find got it button",
                5);

        mainPageObject.waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input for article folder",
                5);

        mainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot find confirm button",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close an article",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find my list button",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5);

        mainPageObject.swipeElementToTheLeft(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        mainPageObject.waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);
    }

    @Test
    public void testSaveTwoArticles() {
        String name_of_folder = "Languages";

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input field",
                15
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input field",
                15
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find expected search result",
                15
        );

        mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                15);

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find got it button",
                15);

        mainPageObject.waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input for article folder",
                15);

        mainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot find confirm button",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close button for an article",
                15);


        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                "JavaScript",
                "Cannot find search input field",
                15
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
                "Cannot find expected search result",
                15
        );

        mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find options button",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find reading list button",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='Languages']"),
                "Cannot find saved folder",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find butto to close an article",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find my list button",
                15);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                15);

        mainPageObject.swipeElementToTheLeft(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        mainPageObject.waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                15);

        WebElement title_list_element = mainPageObject.waitForElementPresence(By.id(("org.wikipedia:id/page_list_item_title")),
                "Cannot find second article",
                15);

        String list_item_title = title_list_element.getAttribute("text");

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find second article to click",
                5);

        WebElement article_title_element = mainPageObject.waitForElementPresence(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30);

        String article_title = article_title_element.getAttribute("text");

        Assert.assertEquals("Titles don't match!",
                list_item_title,
                article_title);
    }

    @Test
    public void testAmmountOfNotEmptySearch() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "Linkin Park Diskography";
        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input field",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        mainPageObject.waitForElementPresence(By.xpath(search_result_locator),
                "Cannot find anything by request " + search_result_locator,
                15);

        int ammount_fo_search_results = mainPageObject.getAmmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue("We found too few results", ammount_fo_search_results > 0);
    }

    @Test
    public void testAmmountOfEmptySearch() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "rtertyuurwr";
        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input field",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        mainPageObject.waitForElementPresence(By.xpath(
                empty_result_label),
                "Cannot find empty result label" + search_line,
                15
        );

        mainPageObject.assertElementNotPresent(By.xpath(search_result_locator),
                "We found result by request: " + search_line);
    }

    @Test
    public void testTitleElementPresent() {
        mainPageObject.waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String search_request = "Java";
        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input field",
                5);

        mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' search result",
                5);

        String title_locator = "org.wikipedia:id/view_page_title_text";
        mainPageObject.assertElementPresent(By.id(title_locator),
                "There is no title for an article by request: " + title_locator);
    }

    @Test
    public void testSearchArticleInBackground() {

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_request = "Java";
        mainPageObject.waitForElementAndSendKeys(By.xpath(
                "//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input field",
                5
        );

        mainPageObject.waitForElementPresence(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article",
                5
        );

        driver.runAppInBackground(2);

        mainPageObject.waitForElementPresence(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article after returning from background",
                5
        );
    }

}
