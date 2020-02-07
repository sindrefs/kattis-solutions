package kattis;

import kattis.Kattio;

import java.util.ArrayList;
import java.util.Stack;

//Board Wrapping
public class Wrapping {
	private static class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Point sub(Point p) {
			return new Point(x - p.x, y - p.y);
		}

		public Point plus(Point p) {
			return new Point(x + p.x, y + p.y);
		}

		public Point rotate(Point center, double angle) {
			// The point aroud origo
			Point ps = this.sub(center);

			// Rotation converted to radians
			angle = -Math.toRadians(angle);

			double xx = ps.x * Math.cos(angle) - ps.y * Math.sin(angle);
			double yy = ps.x * Math.sin(angle) + ps.y * Math.cos(angle);

			Point psRotated = new Point(xx, yy);

			Point rotated = psRotated.plus(center);

			return rotated;
		}

	}

	public static void main(String[] args) {
		kattis.Kattio io = new kattis.Kattio(System.in);
		int N = io.getInt();
		for (int test = 0; test < N; test++) {
			int n = io.getInt();
			ArrayList<Point> allPoints = new ArrayList<>(n * 4);
			double boardArea = 0.0;
			for (int board = 0; board < n; board++) {
				double x = io.getDouble();
				double y = io.getDouble();
				double w = io.getDouble();
				double h = io.getDouble();
				double v = io.getDouble();

				Point center = new Point(x, y);

				Point[] points = new Point[4];
				points[0] = new Point(x - (w / 2), y + (h / 2));
				points[1] = new Point(x + (w / 2), y + (h / 2));
				points[2] = new Point(x - (w / 2), y - (h / 2));
				points[3] = new Point(x + (w / 2), y - (h / 2));

				Point[] actualPoints = new Point[4];
				for (int i = 0; i < 4; i++) {
					Point rotated = points[i].rotate(center, v);
					actualPoints[i] = rotated;
					allPoints.add(rotated);
				}

				boardArea += (h * w);

			}
			// System.out.println("Boardarea " + boardArea);
			allPoints.sort((Point a, Point b) -> (Double.compare(b.x, a.x)));
			Stack<Point> stack = new Stack<Point>();

			stack.push(allPoints.get(0));
			stack.push(allPoints.get(1));
			Point a, b, c;
			for (int i = 2; i < allPoints.size(); i++) {
				stack.push(allPoints.get(i));
				while (stack.size() > 2) {
					a = stack.pop();
					b = stack.pop();
					c = stack.pop();
					if (leftTurn(a, b, c)) {
						stack.push(c);
						stack.push(a);
					} else {
						stack.push(c);
						stack.push(b);
						stack.push(a);
						break;
					}

				}
			}

			// Calculate upper area

			ArrayList<Point> upper = new ArrayList<>(stack.size());
			while (!stack.isEmpty())
				upper.add(stack.pop());

			stack = new Stack<Point>();

			// Calculate lower area
			stack.push(allPoints.get(0));
			stack.push(allPoints.get(1));
			a = null;
			b = null;
			c = null;
			for (int i = 2; i < allPoints.size(); i++) {
				stack.push(allPoints.get(i));
				while (stack.size() > 2) {
					a = stack.pop();
					b = stack.pop();
					c = stack.pop();
					if (leftTurnBack(a, b, c)) {
						stack.push(c);
						stack.push(a);
					} else {
						stack.push(c);
						stack.push(b);
						stack.push(a);
						break;
					}

				}
			}

			ArrayList<Point> lower = new ArrayList<>(stack.size());
			while (!stack.isEmpty())
				lower.add(stack.pop());

			double upperArea = 0.0;
			double lowerArea = 0.0;

			for (int i = 1; i < upper.size(); i++)
				upperArea += (upper.get(i).x - upper.get(i - 1).x) * ((upper.get(i).y + upper.get(i - 1).y) / 2);
			for (int i = 1; i < lower.size(); i++)
				lowerArea += (lower.get(i).x - lower.get(i - 1).x) * ((lower.get(i).y + lower.get(i - 1).y) / 2);
			// System.out.println("upperArea " + upperArea);
			// System.out.println("lowerArea " + lowerArea);
			double polygonArea = upperArea - lowerArea;
			double percent = (boardArea / polygonArea) * 100.0;
			System.out.println(Math.round(percent * 10.0) / 10.0 + " %");
		}
	}

	private static boolean leftTurn(Point a, Point b, Point c) {
		return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) >= 0);
	}

	private static boolean leftTurnBack(Point c, Point b, Point a) {
		return ((-b.x + a.x) * (-c.y + a.y) - (-b.y + a.y) * (-c.x + a.x) >= 0);
	}

}
