package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class SearchPageObject extends MainPageObject
{
    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_CANCEL_SEARCH,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL;
    
    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTiTleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).
                replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElementByTiTleAndDescription(title, description);
        this.waitForElementPresence(search_result_xpath, "Cannot find result for title: " + title + " and description: " + description);
    }

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresence(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type in the search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresence(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresence(SEARCH_CANCEL_SEARCH, "Cannot find clear button", 5);
    }

    public void waitForCancelButtonToDisAppear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_SEARCH, "Clear button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_SEARCH, "Cannot find and click clear button", 5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresence(SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request ",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresence(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertNoResultSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "No results were found");
    }

    public void clearSearchInputField()
    {
        this.waitForElementAndClear(SEARCH_INPUT, "Cannot find search input field", 5);
    }
}
