package lib.tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListTests extends CoreTestCase
{

    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else
        {
            myListsPageObject.closeSyncDialog();
        }

        myListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticles() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();


        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS())
        {
            searchPageObject.tapToClearInputField();
        }

        searchPageObject.typeSearchLine("JavaScript");
        searchPageObject.clickByArticleWithSubstring("Programming language");

        articlePageObject.waitForTitleElement();


        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistingFolder(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else
        {
            myListsPageObject.closeSyncDialog();
        }

        myListsPageObject.swipeByArticleToDelete(article_title);

        if (Platform.getInstance().isIOS())
        {
            searchPageObject.clickByArticleWithSubstring("Programming language");
            myListsPageObject.checkSavedItemIsPresent();
        }
        else {

            String list_item_title = articlePageObject.getSavedArticleTitle();
            myListsPageObject.openSavedArticleInFolder();
            article_title = articlePageObject.getArticleTitle();

            assertEquals("Titles don't match!",
                    list_item_title,
                    article_title);
        }

    }
}
