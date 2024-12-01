import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);

        Arrays.sort(points);

        int n = points.length;
        Point[] finalPoints = Arrays.copyOf(points, n);

        LineSegment[] tempSegments = new LineSegment[n * n];

        int segCount = 0;

        for (int i = 0; i < n; i++) {
            Point origin = finalPoints[i];  

            Arrays.sort(finalPoints, origin.slopeOrder());

            int collinearStart = 1;

            while (collinearStart < n) {
                // Checking slopes between origin and consecutive points
                double slope = origin.slopeTo(finalPoints[collinearStart]);

                int collinearEnd = collinearStart;

                // Identifying the range of points with the same slope
                while (collinearEnd < n && Double.compare(origin.slopeTo(finalPoints[collinearEnd]), slope) == 0) {
                    collinearEnd++;
                }

                // 4 or more points are collinear
                if (collinearEnd - collinearStart >= 3) {
                    Point minPoint = origin;
                    Point maxPoint = origin;

                    // Finding min and max
                    for (int j = collinearStart; j < collinearEnd; j++) {
                        if (finalPoints[j].compareTo(minPoint) < 0) minPoint = finalPoints[j];
                        if (finalPoints[j].compareTo(maxPoint) > 0) maxPoint = finalPoints[j];
                    }

                    // Only adding segment if origin is the smallest point
                    if (origin.compareTo(minPoint) == 0) {
                        tempSegments[segCount++] = new LineSegment(minPoint, maxPoint);
                    }
                }

                // Next group
                collinearStart = collinearEnd;
            }

            // Restoring the original order of finalPoints
            Arrays.sort(finalPoints);
        }

        // Final segments array, without redundancies
        segments = Arrays.copyOf(tempSegments, segCount);
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

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
