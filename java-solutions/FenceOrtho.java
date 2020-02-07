package kattis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

//Fence Orthogonality
public class FenceOrtho {
	private static class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}

		public Point sub(Point other) {
			return new Point(other.x - x, other.y - y);
		}
		
		public Point minus(Point other) {
			return new Point(this.x - other.x, this.y - other.y);
		}

		public Point plus(Point p) {
			return new Point(x + p.x, y + p.y);
		}

		public Point rotate(Point center, double angle) {
			// The point around origo
			Point ps = center.sub(this);

			// Clockwise rotation
			angle = -angle;

			// Rotation converted to radians
			// angle = -Math.toRadians(angle);

			double xx = ps.x * Math.cos(angle) - ps.y * Math.sin(angle);
			double yy = ps.x * Math.sin(angle) + ps.y * Math.cos(angle);

			Point psRotated = new Point(xx, yy);

			Point rotated = psRotated.plus(center);

			return rotated;
		}

		public double getLength() {
			return Math.sqrt(x * x + y * y);
		}

		private double getDist(Point other) {
			double xx = Math.pow(x - other.x, 2);
			double yy = Math.pow(y - other.y, 2);
			return Math.sqrt(xx + yy);
		}

		private boolean equals(Point other) {
			return (this.x == other.x && this.y == other.y);
		}
	}

	public static double OFFS = 0.000005;
	static int c = 0;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int N = io.getInt();
			if (N == -1)
				break;
			ArrayList<Point> points = new ArrayList<>(N);
			for (int i = 0; i < N; i++)
				points.add(new Point(io.getDouble(), io.getDouble()));

			ArrayList<Point> corners = getOuterPoints(points);

			// System.out.println("\nNEW UNDER");
			// for (Point p : corners)
			// System.out.println(p.x + " " + p.y);

			if (corners.size() >= 4)
				perimeterFour(corners);
			else if (corners.size() == 3)
				perimeterThree(corners);
			c++;
		}
		io.close();
	}

	private static void perimeterThree(ArrayList<Point> corners) {
		// Rule of sine, with a to b as base line
		double perimeter = Double.MAX_VALUE;
		for (int i = 0; i < 3; i++) {
			// Corners
			Point A = corners.get(i);
			Point B = corners.get((i + 1) % 3);
			Point C = corners.get((i + 2) % 3);

			double edgeAB = A.getDist(B);

			double angleA = angleBetween(A.sub(C), A.sub(B));
			double angleB = angleBetween(B.sub(A), B.sub(C));
			if (angleA > Math.PI / 2 || angleB > Math.PI / 2)
				continue;
			double edgeAC = A.getDist(C);
			// (a/Sin(A)) = (b/Sin(B)), a is height, Sin(B)=Sin(pi/2)=1
			double height = Math.sin(angleA) * edgeAC;

			if ((height * 2) + (edgeAB * 2) < perimeter)
				perimeter = (height * 2) + (edgeAB * 2);
		}
		System.out.println(perimeter);
	}

	private static void perimeterFour(ArrayList<Point> corners) {
		int[] refIndex = getExtremes(corners);
//		System.out.println(corners);

		Point[] vectors = new Point[4];
		vectors[0] = (new Point(0, 1));
		vectors[1] = (new Point(1, 0));
		vectors[2] = (new Point(0, -1));
		vectors[3] = (new Point(-1, 0));

		int[] vectorIsIn = new int[4];
		for (int i = 0; i < 4; i++) {
			vectorIsIn[i] = refIndex[i];

		}
//
//		for (int ii : refIndex)
//			System.out.println(ii);
		double perimeter = Double.MAX_VALUE;

		for (int q = 0; q < corners.size(); q++) {
//			System.out.println("Base: "  + q + " " + (q+1) % corners.size());
			//vectorIsIn[0] = (q+1) % corners.size();
//			System.out.println(vectors[0]);
//			System.out.println(nextPoint(corners, q+1));
//			System.out.println(corners.get(q).sub(nextPoint(corners, q+1)));
			double angleToRotate = angleBetween(vectors[0],
					corners.get(q).sub(nextPoint(corners, q)));
//			System.out.println("Rotating with angle " + Math.toDegrees(angleToRotate)+" ...");

			for (int i = 0; i < 4; i++) {
				double angle = angleToRotate;
				int pIndex = vectorIsIn[i];

				double canRotateThisMuch;
				double willRotateThisMuch;

				boolean flush;
//				System.out.println("Rotating vector " + i);
				while (Math.abs(angle) >= OFFS) {

					flush = false;

					canRotateThisMuch = angleBetween(vectors[i], corners.get(pIndex).sub(nextPoint(corners, pIndex)));
					willRotateThisMuch = Math.min(canRotateThisMuch, angle);
//					System.out.println("needs to rotate: " + Math.toDegrees(willRotateThisMuch) + "°");
					//if (q == 2 && i == 2)
						//System.out.println(i + ": " + willRotateThisMuch );
					
					vectors[i] = (vectors[i]).rotate(new Point(0, 0), willRotateThisMuch);

					angle -= willRotateThisMuch;
					//if (q == 2 && i == 2)
					if (almostEqual(willRotateThisMuch, canRotateThisMuch))
						flush = true;

					if (flush) {
						pIndex = nextPointIndex(corners, pIndex);
//						System.out.println("Changed index to " + pIndex);
					}
				}
//				System.out.println();
				vectorIsIn[i] = pIndex;

			}
//			System.out.println();
			// Find height
			Point A = corners.get(q);
			Point B = corners.get(nextPointIndex(corners, q));
			Point C = corners.get(vectorIsIn[2]);
			
//			if (c ==0) {
//				System.out.println("Done for this shit -> base: " + q + ", " + nextPointIndex(corners, q) + " antipodal: " + vectorIsIn[2]);
//			}

//			System.out.println("a " + q + " b " + nextPointIndex(corners, q) + " c " + vectorIsIn[2]);

			double bToC = B.getDist(C);
			double angleABC = angleBetween(B.sub(A), B.sub(C)); 
			double height = (bToC * Math.sin(angleABC));

			// Find width
			Point upL = B;
			Point lowL = A;
			double lengthNormal = upL.getDist(lowL);

			Point uppermost = corners.get(vectorIsIn[1]);
			Point lowermost = corners.get(vectorIsIn[3]);

			// Outer angle between upper upL and uppermost
			double angleOuterUpper = angleBetween(vectors[0], upL.sub(uppermost));

			double lengthUp;
			if (!(uppermost.equals(upL) || angleOuterUpper == (Math.PI / 2))) {
				// Length over normal
				lengthUp = Math.sin(Math.PI - (Math.PI / 2) - angleOuterUpper) * upL.getDist(uppermost);
			} else
				lengthUp = 0.0;

			double angleOuterLow = Math.PI - angleBetween(lowL.sub(upL), lowL.sub(lowermost));
			double lengthDown;
			if (!(lowermost.equals(lowL) || angleOuterLow == (Math.PI / 2))) {
				lengthDown = Math.sin(Math.PI - (Math.PI / 2) - angleOuterLow) * lowL.getDist(lowermost);
			} else
				lengthDown = 0.0;

			double width = lengthNormal + lengthUp + lengthDown;

//			System.out.println("q " + q + " height " + height + " width " + width);

			double p = (width * 2) + (height * 2);
			if (p < perimeter)
				perimeter = p;

		}
		System.out.println(perimeter);

		// if (corners.size() < 25) {
		// System.out.println(corners.size()+1);
		// for (Point p : corners)
		// System.out.println(p.x + " " + p.y);
		// }

	}

	private static boolean almostEqual(double a, double b) {
		return Math.max(a, b) - Math.min(a, b) < OFFS;
	}

	private static int[] getExtremes(ArrayList<Point> corners) {
		ArrayList<Point> wnes = new ArrayList<Point>(4);
		int[] wnesIndex = new int[4];
		// west
		wnes.add(new Point(Integer.MAX_VALUE, 0));
		// north
		wnes.add(new Point(0, Integer.MIN_VALUE));
		// east
		wnes.add(new Point(Integer.MIN_VALUE, 0));
		// south
		wnes.add(new Point(0, Integer.MAX_VALUE));

		for (int i = 0; i < corners.size(); i++) {
			if (wnes.get(0).x > corners.get(i).x) {
				wnes.set(0, corners.get(i));
				wnesIndex[0] = i;
			}
			if (wnes.get(1).y < corners.get(i).y) {
				wnes.set(1, corners.get(i));
				wnesIndex[1] = i;
			}
			if (wnes.get(2).x < corners.get(i).x) {
				wnes.set(2, corners.get(i));
				wnesIndex[2] = i;
			}
			if (wnes.get(3).y > corners.get(i).y) {
				wnes.set(3, corners.get(i));
				wnesIndex[3] = i;
			}
		}
		return wnesIndex;
	}

	private static double angleBetween(Point a, Point b) {
		return Math.acos((a.x * b.x + a.y * b.y) / (a.getLength() * b.getLength()));
	}

	private static Point nextPoint(ArrayList<Point> list, int here) {
		if (here + 1 >= list.size())
			return list.get(0);
		else
			return list.get(here + 1);
	}

	private static int lastPoint(ArrayList<Point> list, int here) {
		if (here - 1 < 0)
			return list.size() - 1;
		else
			return here - 1;
	}

	private static int nextPointIndex(ArrayList<Point> list, int here) {
		if (here + 1 >= list.size())
			return 0;
		else
			return (here + 1);
	}

	private static ArrayList<Point> getOuterPoints(ArrayList<Point> points) {
		points.sort((Point a, Point b) -> (Double.compare(a.x, b.x)));
		Stack<Point> stack = new Stack<Point>();

		ArrayList<Point> outerPoints = new ArrayList<Point>();

		ArrayList<Point> upper = new ArrayList<>();
		ArrayList<Point> lower = new ArrayList<>();

		stack.push(points.get(0));
		stack.push(points.get(1));
		Point a, b, c;
		for (int i = 2; i < points.size(); i++) {
			stack.push(points.get(i));
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

		while (!stack.isEmpty())
			upper.add(stack.pop());

		Collections.reverse(upper);

		stack = new Stack<Point>();

		// Calculate lower area
		stack.push(points.get(0));
		stack.push(points.get(1));
		a = null;
		b = null;
		c = null;
		for (int i = 2; i < points.size(); i++) {
			stack.push(points.get(i));
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
		stack.pop();
		while (!stack.isEmpty())
			lower.add(stack.pop());
		lower.remove(lower.size() - 1);

		for (Point p : upper)
			outerPoints.add(p);
		for (Point p : lower)
			outerPoints.add(p);

		// for (Point p : outerPoints)
		// System.out.println(p.x + " " + p.y);
		// for (Point p : outerPoints)
		// System.out.println(p.x + " " + p.y);
		return outerPoints;

	}

	private static boolean leftTurn(Point a, Point b, Point c) {
		return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) >= 0);
	}

	private static boolean leftTurnBack(Point c, Point b, Point a) {
		return ((-b.x + a.x) * (-c.y + a.y) - (-b.y + a.y) * (-c.x + a.x) >= 0);
	}
}
