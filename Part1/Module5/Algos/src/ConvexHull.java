import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class ConvexHull {
    public static class Point2D {
        private final double x, y;

        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public static final Comparator<Point2D> Y_ORDER = new Comparator<Point2D>() {
            @Override
            public int compare(Point2D p1, Point2D p2) {
                int yComparison = Double.compare(p1.y, p2.y); // Comparing by y-coordinate
                if (yComparison != 0) {
                    return yComparison;
                }
                // If y-coordinates are equal, comparing by x-coordinate in descending order
                return Double.compare(p2.x, p1.x);
            }
        };

        public static class BY_POLAR_ORDER implements Comparator<Point2D> {
            private final Point2D referencePoint;

            public BY_POLAR_ORDER(Point2D rp) {
                this.referencePoint = rp;
            }

            @Override
            public int compare(Point2D p1, Point2D p2) {
                int orientation = ccw(referencePoint, p1, p2);

                if (orientation == 0) {
                    // Points are collinear; sorting by distance to reference point
                    double dist1 = distanceSquared(referencePoint, p1);
                    double dist2 = distanceSquared(referencePoint, p2);

                    return Double.compare(dist1, dist2);
                }

                return -orientation; // Counter-clockwise (-1), Clockwise (1)
            }

            private double distanceSquared(Point2D a, Point2D b) {
                double dx = b.x - a.x;
                double dy = b.y - a.y;

                return dx * dx + dy * dy;
            }
        }

    }

    // Returns a stack of edge points that define the convex hull of the given set of points
    public static Stack<Point2D> grahamScan(Point2D[] p) {
        Stack<Point2D> hull = new Stack<Point2D>();

        // p[0] is now the point with the lowest y-coordinate
        Arrays.sort(p, Point2D.Y_ORDER);
        System.out.print(p[0].x);
        System.out.println(p[0].y);
        System.out.print(p[1].x);
        System.out.println(p[1].y);
        System.out.println(p.length);

        // Sorting by polar angle with respect to p[0]
        Arrays.sort(p, new Point2D.BY_POLAR_ORDER(p[0]));
        System.out.print(p[0].x);
        System.out.println(p[0].y);
        System.out.print(p[1].x);
        System.out.println(p[1].y);
        System.out.println(p.length);

        // Definitely on hull
        hull.push(p[0]);
        hull.push(p[1]);

        int n = p.length;

        for (int i = 2; i < n; i++) {
            Point2D top = hull.pop();

            while (ConvexHull.ccw(hull.peek(), top, p[i]) <= 0) {
                top = hull.pop();
            }

            hull.push(top);
            hull.push(p[i]);
        }

        return hull;
    }

    // a, b, c are processed with in order assumption.
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

        // Clockwise
        if (area < 0) return -1;

        // Counter-clockwise
        if (area > 0) return 1;

        // Collinear
        return 0;
    }

    // Test
    public static void main(String[] args) {
        ConvexHull.Point2D[] points = {
                new ConvexHull.Point2D(0, 0),
                new ConvexHull.Point2D(2, 2),
                new ConvexHull.Point2D(4, 4),
                new ConvexHull.Point2D(6, 0),
                new ConvexHull.Point2D(4, -4),
                new ConvexHull.Point2D(2, -2),
                new ConvexHull.Point2D(2, 0),
                new ConvexHull.Point2D(0, 2),
                new ConvexHull.Point2D(4, 2),
                new ConvexHull.Point2D(4, -2)
        };

        Stack<ConvexHull.Point2D> hull = ConvexHull.grahamScan(points);

        System.out.println("Points on the convex hull:");
        for (ConvexHull.Point2D p : hull) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }

        // Should be:
        //
        // Points on the convex hull:
        // (4.0, -4.0)
        // (6.0, 0.0)
        // (4.0, 4.0)
        // (0.0, 2.0)
        // (0.0, 0.0)
    }
}
