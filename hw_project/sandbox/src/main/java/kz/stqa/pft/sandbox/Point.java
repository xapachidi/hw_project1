package kz.stqa.pft.sandbox;
/**
 * Created by Xeniya on 25.05.2016.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public  double distance(Point p2){

        double dist = Math.sqrt(Math.pow(this.x - p2.x, 2) + Math.pow(this.y - p2.y, 2));
        return dist;
    }
}
