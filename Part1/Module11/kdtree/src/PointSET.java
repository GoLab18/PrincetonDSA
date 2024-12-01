import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

// This is the brute-forced implementation
public class PointSET {
    private final TreeSet<Point2D> rbBST;

    public PointSET() {
        rbBST = new TreeSet<>();
    }

    public boolean isEmpty() {
        return rbBST.isEmpty();
    }

    public int size() {
        return rbBST.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        rbBST.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return rbBST.contains(p);
    }
    public void draw() {
        for (Point2D p : rbBST) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        List<Point2D> pointsContained = new ArrayList<>();

        for (Point2D p : rbBST) if (rect.contains(p)) pointsContained.add(p);

        return pointsContained;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size() == 0) return null;

        Point2D pNearest = rbBST.iterator().next();

        for (Point2D curr : rbBST) {
            if (curr.distanceSquaredTo(p) < pNearest.distanceSquaredTo(p)) pNearest = curr;
        }

        return pNearest;
    }

    // Test
    public static void main(String[] args) {
        PointSET ps = new PointSET();

        ps.insert(new Point2D(4, 1));
        ps.insert(new Point2D(8, 5));
        ps.insert(new Point2D(10, 9));

        String nr = ps.nearest(new Point2D(5, 8)).toString();
        System.out.print(nr);
    }
}