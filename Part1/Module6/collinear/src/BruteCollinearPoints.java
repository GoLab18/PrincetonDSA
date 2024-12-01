import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);

        Point[] finalPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(finalPoints);

        LineSegment[] tempSegments = new LineSegment[points.length * points.length];
        int countOfSegments = 0;

        // Let the madness begin :X
        for (int i = 0; i < finalPoints.length; i++) {
            for (int j = i + 1; j < finalPoints.length; j++) {
                for (int k = j + 1; k < finalPoints.length; k++) {
                    for (int m = k + 1; m < finalPoints.length; m++) {
                        if (areCollinear(finalPoints[i], finalPoints[j], finalPoints[k], finalPoints[m])) {
                            tempSegments[countOfSegments++] = new LineSegment(finalPoints[i], finalPoints[m]);
                        }
                    }
                }
            }
        }

        // Final segments array, without redundancies
        segments = Arrays.copyOf(tempSegments, countOfSegments);
    }

    private boolean areCollinear(Point p, Point q, Point r, Point s) {
        double slope1 = p.slopeTo(q);
        double slope2 = p.slopeTo(r);
        double slope3 = p.slopeTo(s);

        return Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0;
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array given as a constructor param is null");
        }

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("One of the given point is null");
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException("Two of the given points can't be equal");
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    // Test TODO delete before sending
    public static void main(String[] args) {
        Point[] points1 = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        };

        BruteCollinearPoints bcp1 = new BruteCollinearPoints(points1);
        System.out.println("Number of line segments: " + bcp1.numberOfSegments()); // Expected: 1
        for (LineSegment seg : bcp1.segments()) {
            System.out.println(seg);
        }

        Point[] points2 = {
                new Point(1, 1),
                new Point(2, 3),
                new Point(3, 4),
                new Point(4, 6)
        };

        BruteCollinearPoints bcp2 = new BruteCollinearPoints(points2);
        System.out.println("Number of line segments: " + bcp2.numberOfSegments()); // Expected: 0

        Point[] points3 = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 2),
                new Point(5, 1)
        };

        BruteCollinearPoints bcp3 = new BruteCollinearPoints(points3);
        System.out.println("Number of line segments: " + bcp3.numberOfSegments()); // Expected: 0
        for (LineSegment seg : bcp3.segments()) {
            System.out.println(seg);
        }
    }
}