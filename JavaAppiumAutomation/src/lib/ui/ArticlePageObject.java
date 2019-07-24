package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{
    private static final String
        ARTICLE_TITLE = "org.wikipedia:id/view_page_title_text",
        SAVED_ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        EXISTING_FOLDER_NAME_TPL = "//*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static String getExistingFolderName (String folder_name)
    {
        return EXISTING_FOLDER_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresence(By.id(ARTICLE_TITLE), "Cannot find article title", 15);
    }

    public WebElement waitForSavedTitleElement()
    {
        return this.waitForElementPresence(By.id(SAVED_ARTICLE_TITLE), "Cannot find article title", 15);
    }

    public String getArticleTitle()
    {
        WebElement title = waitForTitleElement();
        return title.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find options button",
                5);

        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find reading list button",
                5);

        this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find got it button",
                5);

        this.waitForElementAndClear(By.id(MY_LIST_NAME_INPUT),
                "Cannot find input for article folder",
                5);

        this.waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        this.waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find confirm button",
                5);
    }

    public void addArticleToExistingFolder(String folder_name)
    {
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find options button",
                5);

        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find reading list button",
                5);
        String existing_folder_xpath = getExistingFolderName(folder_name);
        this.waitForElementAndClick(By.xpath(existing_folder_xpath),
                "Cannot find saved folder",
                15);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find close an article",
                5);
    }

    public String getSavedArticleTitle()
    {
        WebElement title = waitForSavedTitleElement();
        return title.getAttribute("text");
    }

     public void assertTitleElementPresent()
     {
         this.assertElementPresent(By.id(ARTICLE_TITLE), "Cannot find title element");
     }
}
