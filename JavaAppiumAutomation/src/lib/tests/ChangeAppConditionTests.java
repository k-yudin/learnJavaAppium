package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    public void testSearchArticleInBackground() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = articlePageObject.getArticleTitle();

        try {
            this.rotateScreenLandscape();
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_rotation = articlePageObject.getArticleTitle();
        assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_rotation);

        try {
            this.rotateScreenPortrait();
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_second_rotation = articlePageObject.getArticleTitle();
        assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation);
    }
}
