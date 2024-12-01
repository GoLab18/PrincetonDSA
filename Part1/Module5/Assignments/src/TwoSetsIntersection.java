import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class TwoSetsIntersection {
    public static int countCommonPoints(Point2D[] a, Point2D[] b) {
        HashSet<Point2D> set = new HashSet<>();

        // Adding array a into the HashSet
        Collections.addAll(set, a);

        // Counting points in array b that are also in the set
        int count = 0;
        for (Point2D point : b) {
            if (set.contains(point)) {
                count++;
            }
        }

        return count;
    }

    // Class representing a 2D point
    static class Point2D {
        private final int x;
        private final int y;

        // Constructor
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Same reference -> true

            // Checking if null and if they don't only inherit from the same class
            if (o == null || getClass() != o.getClass()) return false;

            Point2D p = (Point2D) o;

            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // Test
    public static void main(String[] args) {
        Point2D[] a = {new Point2D(1, 2), new Point2D(3, 4), new Point2D(5, 6)};
        Point2D[] b = {new Point2D(3, 4), new Point2D(7, 8), new Point2D(5, 6)};

        System.out.println("Common points num: " + countCommonPoints(a, b));
    }
}
