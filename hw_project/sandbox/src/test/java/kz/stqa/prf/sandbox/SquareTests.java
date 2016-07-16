package kz.stqa.prf.sandbox;

import kz.stqa.pft.sandbox.Square;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xeniya on 5/30/16.
 */
public class SquareTests {

    @Test

    public void testArea(){
        Square s = new Square(5);
        assert s.area() == 25;
        Assert.assertEquals(s.area(), 25.0);
    }

}
