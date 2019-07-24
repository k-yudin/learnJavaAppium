package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject
{
    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']",
        SAVED_ARTICLE = "org.wikipedia:id/page_list_item_title";

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
        this.waitForElementAndClick(By.xpath(folder_name_xpath),
                "Cannot find folder by namer" + name_of_folder,
                5);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getFolderXPathByNmae(article_title);
        this.swipeElementToTheLeft(By.xpath(article_xpath),
                "Cannot find saved article");
        this.waitForArticleToDisAppearByTitle(article_title);
    }

    public void waitForArticleToDisAppearByTitle(String article_title)
    {
        String article_xpath = getFolderXPathByNmae(article_title);
        this.waitForElementNotPresent(By.xpath(article_xpath), "Saved article is still present with title" + article_title, 15);
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getFolderXPathByNmae(article_title);
        this.waitForElementPresence(By.xpath(article_xpath), "Cannot find saved article by title" + article_title, 15);
    }

    public void openSavedArticleInFolder()
    {
        this.waitForElementPresence(By.id(SAVED_ARTICLE), "Cannot find saved article in the list", 15);
        this.waitForElementAndClick(By.id(SAVED_ARTICLE), "Cannot click on saved article in the list", 5);
    }
}
