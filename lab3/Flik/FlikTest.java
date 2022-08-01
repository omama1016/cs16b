
import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsTheSame(){
        assertTrue(Flik.isSameNumber(3,3));
        assertTrue(Flik.isSameNumber(100,100));
        assertTrue(Flik.isSameNumber(0,0));
        assertTrue(Flik.isSameNumber(-100,-100));
        assertTrue(Flik.isSameNumber(128,128));
    }
}
