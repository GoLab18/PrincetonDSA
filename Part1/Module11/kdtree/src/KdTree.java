import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class KdTree {
    private Node root;

    private static class Node {
        private Point2D point;
        private Node leftChild, rightChild;
        private final RectHV rect;
        private int size; // Children nodes count in the subtree

        public Node(Point2D point, RectHV rect, int size) {
            this.point = point;
            this.rect = rect;
            this.size = size;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node curr) {
        return curr == null ? 0 : curr.size;
    }

    // Inserts into the tree, running time is log(n)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (root == null) root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), 1);
        else root = insert(root, p, root.rect, true);
    }

    private Node insert(Node curr, Point2D p, RectHV rect, boolean isXCompared) {
        if (curr == null) return new Node(p, rect, 1);

        int cmp;

        if (isXCompared) {
            double fixedX = curr.point.x();
            cmp = Double.compare(p.x(), fixedX);

            if (curr.leftChild == null && cmp <  0)
                rect = new RectHV(rect.xmin(), rect.ymin(), fixedX, rect.ymax());
            else if (curr.rightChild == null && cmp >= 0)
                rect = new RectHV(fixedX, rect.ymin(), rect.xmax(), rect.ymax());
            else if (cmp <  0) rect = curr.leftChild.rect;
            else rect = curr.rightChild.rect;
        } else {
            double fixedY = curr.point.y();
            cmp = Double.compare(p.y(), fixedY);

            if (curr.leftChild == null && cmp <  0)
                rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), fixedY);
            else if (curr.rightChild == null && cmp >= 0)
                rect = new RectHV(rect.xmin(), fixedY, rect.xmax(), rect.ymax());
            else if (cmp <  0) rect = curr.leftChild.rect;
            else rect = curr.rightChild.rect;
        }

        if (cmp == 0 && curr.point.equals(p)) curr.point = p;
        else if (cmp < 0) curr.leftChild = insert(curr.leftChild, p, rect, !isXCompared);
        else curr.rightChild = insert(curr.rightChild, p, rect, !isXCompared);

        curr.size = 1 + size(curr.leftChild) + size(curr.rightChild);

        return curr;
    }

    // Returns if the given point can be found, runs in log(n) time
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node currNode = root;
        int cmp;
        boolean isXCompared = true; // Initially true because we start by dividing with respect to x

        while (currNode != null) {
            if (isXCompared) cmp = Double.compare(p.x(), currNode.point.x());
            else cmp = Double.compare(p.y(), currNode.point.y());

            if (cmp == 0 && currNode.point.equals(p)) return true;
            else if (cmp < 0) currNode = currNode.leftChild;
            else currNode = currNode.rightChild;

            isXCompared = !isXCompared;
        }

        return false;
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node curr, boolean isXCompared) {
        if (curr == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        StdDraw.point(curr.point.x(), curr.point.y());
        StdDraw.setPenRadius();

        if (isXCompared) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(curr.point.x(), curr.rect.ymin(), curr.point.x(), curr.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(curr.rect.xmin(), curr.point.y(), curr.rect.xmax(), curr.point.y());
        }

        draw(curr.leftChild, !isXCompared);
        draw(curr.rightChild, !isXCompared);
    }

    // Returns points contained inside the given rectangle in R + log(n) time,
    // where R = number of intersections
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        recRange(pointsInRange, root, rect);

        return pointsInRange;
    }

    // Recursively adds points contained in the given rectangle
    private void recRange(ArrayList<Point2D> arr, Node curr, RectHV givenRect) {
        if (curr == null || !curr.rect.intersects(givenRect)) return;

        recRange(arr, curr.leftChild, givenRect);
        if (givenRect.contains(curr.point)) arr.add(curr.point);

        recRange(arr, curr.rightChild, givenRect);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return root == null ? null : nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node curr, Point2D queryPoint, Point2D closestPoint, boolean isXCompared) {
        if (curr == null) return closestPoint;

        double closestDistance = queryPoint.distanceSquaredTo(closestPoint);
        double currDistance = queryPoint.distanceSquaredTo(curr.point);
        if (currDistance < closestDistance) closestPoint = curr.point;

        Node primaryChild, secondaryChild;

        // Determining traversal order based on splitting axis
        if ((isXCompared && queryPoint.x() < curr.point.x()) || (!isXCompared && queryPoint.y() < curr.point.y())) {
            primaryChild = curr.leftChild;
            secondaryChild = curr.rightChild;
        } else {
            primaryChild = curr.rightChild;
            secondaryChild = curr.leftChild;
        }

        if (primaryChild != null) {
            closestPoint = nearest(primaryChild, queryPoint, closestPoint, !isXCompared);
            closestDistance = queryPoint.distanceSquaredTo(closestPoint);
        }

        if (secondaryChild != null && secondaryChild.rect.distanceSquaredTo(queryPoint) < closestDistance) {
            closestPoint = nearest(secondaryChild, queryPoint, closestPoint, !isXCompared);
        }

        return closestPoint;
    }
}