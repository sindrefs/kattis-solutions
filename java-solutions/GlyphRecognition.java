package kattis;

import java.util.ArrayList;

//Glyph Recognition
public class GlyphRecognition {
	private static class Point {
		private double x;
		private double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
	}

	private static ArrayList<Point> points;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		int N = io.getInt();
		points = new ArrayList<Point>(N);

		for (int i = 0; i < N; i++)
			points.add(new Point((double) io.getInt(), (double) io.getInt()));

		int figure = 0;
		double ratio = 0.0;

		for (int angles = 3; angles < 9; angles++) {
			double maxArea = 0.0;
			double minArea = Double.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				double area = getArea(points.get(i), angles);

				if (area > maxArea)
					maxArea = area;
				if (area < minArea)
					minArea = area;
			}
			double r = minArea / maxArea;
			// System.out
			// .println("figure " + angles + " maxarea " + maxArea + " minarea "
			// + minArea + " ratio " + r);

			if (r > ratio) {
				ratio = r;
				figure = angles;
			}

		}
		System.out.println(figure + " " + ratio);

		// System.out.println("Areal " + getArea(new Point(1.0, 0.0), 4));

	}

	private static double getArea(Point p, double angles) {
		double innerAngle = (2.0 * Math.PI) / (double) angles;
		double outerAngles = (Math.PI - innerAngle) / 2;
		double oldAngle = angleToPoint(p);
		double newAngle = oldAngle;

		while (newAngle < 0)
			newAngle += innerAngle;
		while (newAngle > innerAngle)
			newAngle -= innerAngle;

		// Using law of sine
		// ..
		double distToPoint = distToPoint(p);
		// Angle between line from origo to point to outer edge of
		// (triangle)shape
		double pointToOuterEdge = Math.PI - newAngle - outerAngles;
		// Edgde from origo to intersection between x-axis and rightmost edge in
		// trianlge
		double lowerEgde = (Math.sin(pointToOuterEdge) * distToPoint) / Math.sin(outerAngles);
		double upperEdge = (Math.sin(outerAngles) * lowerEgde) / (Math.sin(outerAngles));

		// Area of triangle, where the two variables are the height
		double area = 0.5 * lowerEgde * upperEdge * Math.sin(innerAngle);
		return area;
	}

	private static double angleToPoint(Point p) {
		return Math.atan2(p.getY(), p.getX());
	}

	private static double distToPoint(Point p) {
		return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
	}

}
