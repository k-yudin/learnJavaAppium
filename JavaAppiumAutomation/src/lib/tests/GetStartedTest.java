package lib.tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase
{
    @Test
    public void testPassThroughWelcome()
    {
        if (Platform.getInstance().isAndroid())
        {return;}
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLInk();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLanguagesText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForDataCollectedText();
        welcomePageObject.clicGetStartedButton();
    }
}
