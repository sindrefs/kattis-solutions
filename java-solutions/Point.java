package kattis;

//Point class
public class Point {
    private double x;
    private double y;
    private int index;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }
}
