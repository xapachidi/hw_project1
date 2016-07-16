package kz.stqa.prf.sandbox;

import kz.stqa.pft.sandbox.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xeniya on 5/30/16.
 */
public class PointTests {

    @Test

    public void testPoint(){

        Point p1 = new Point(5,2);
        Point p2 = new Point(4,3);

                assert p1.distance(p2) == 1.4142135623730951;


    }
    @Test
    public void  testPoint1()
    {
        Point p1 = new Point(5,2);
        Point p2 = new Point(4,3);

        Assert.assertEquals(p1.distance(p2), 1.4142135623730951);
    }

    @Test
    public void  testPoint2()
    {
        Point p1 = new Point(5,2);
        Point p2 = new Point(4,3);

        Assert.assertEquals(p1.distance(p2), 1.414);
    }
}
