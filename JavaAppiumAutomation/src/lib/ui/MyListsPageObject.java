package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject
{
    public static final String
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']",
        SAVED_ARTICLE = "id:org.wikipedia:id/page_list_item_title";

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static String getSavedArticleXPathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getFolderXPathByNmae(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXPathByNmae(name_of_folder);
        this.waitForElementAndClick(folder_name_xpath,
                "Cannot find folder by namer" + name_of_folder,
                5);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getFolderXPathByNmae(article_title);
        this.swipeElementToTheLeft(article_xpath,
                "Cannot find saved article");
        this.waitForArticleToDisAppearByTitle(article_title);
    }

    public void waitForArticleToDisAppearByTitle(String article_title)
    {
        String article_xpath = getFolderXPathByNmae(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article is still present with title" + article_title, 15);
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getFolderXPathByNmae(article_title);
        this.waitForElementPresence(article_xpath, "Cannot find saved article by title" + article_title, 15);
    }

    public void openSavedArticleInFolder()
    {
        this.waitForElementPresence(SAVED_ARTICLE, "Cannot find saved article in the list", 15);
        this.waitForElementAndClick(SAVED_ARTICLE, "Cannot click on saved article in the list", 5);
    }
}
