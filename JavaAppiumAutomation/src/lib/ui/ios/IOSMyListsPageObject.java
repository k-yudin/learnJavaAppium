package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject
{
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        SAVED_ARTICLE = "id:org.wikipedia:id/page_list_item_title";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        CLOSE_SYNC_BUTTON = "id:places auth close";
    }

    public IOSMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
