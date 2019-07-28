package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{
    private static final String
        ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text",
        SAVED_ARTICLE_TITLE = "id:org.wikipedia:id/page_list_item_title",
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
        EXISTING_FOLDER_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";

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
        return this.waitForElementPresence(ARTICLE_TITLE, "Cannot find article title", 15);
    }

    public WebElement waitForSavedTitleElement()
    {
        return this.waitForElementPresence(SAVED_ARTICLE_TITLE, "Cannot find article title", 15);
    }

    public String getArticleTitle()
    {
        WebElement title = waitForTitleElement();
        return title.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find options button",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find reading list button",
                5);

        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                "Cannot find got it button",
                5);

        this.waitForElementAndClear(MY_LIST_NAME_INPUT,
                "Cannot find input for article folder",
                5);

        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                "Cannot find confirm button",
                5);
    }

    public void addArticleToExistingFolder(String folder_name)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find options button",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find reading list button",
                5);
        String existing_folder_xpath = getExistingFolderName(folder_name);
        this.waitForElementAndClick(existing_folder_xpath,
                "Cannot find saved folder",
                15);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
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
         this.assertElementPresent(ARTICLE_TITLE, "Cannot find title element");
     }
}
