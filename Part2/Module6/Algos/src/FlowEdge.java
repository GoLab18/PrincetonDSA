public class FlowEdge {
    private final int v, w;
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex != w && vertex != v) throw new IllegalArgumentException("Illegal endpoint");
        return (vertex == v) ? w : v;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex != w && vertex != v) throw new IllegalArgumentException("Illegal endpoint");
        return (vertex == v) ? flow : capacity - flow;
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex != w && vertex != v) throw new IllegalArgumentException("Illegal endpoint");
        flow = (vertex == v) ? flow - delta : flow + delta;
    }
}
