import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass mc = new MainClass();

    @Test
    public void testGetClassNumber()
    {
        Assert.assertTrue("Method getClassNumber returns unexpected number!", mc.getClassNumber() > 45);
    }
}
