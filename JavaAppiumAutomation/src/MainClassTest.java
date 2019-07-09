import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass mc = new MainClass();

    @Test
    public void testGetClassString()
    {
        Assert.assertTrue("String doesn't have search substring", mc.getClassString().contains("hello") ||
                mc.getClassString().contains("Hello"));
    }
}
