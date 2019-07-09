import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber()
    {
        MainClass mc = new MainClass();
        Assert.assertTrue("Method returns invalid number!", mc.getLocalNumber() == 14);
    }
}
