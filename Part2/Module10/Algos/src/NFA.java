import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;

// Based on Thompson Construction Algorithm
public class NFA {
    private char[] re; // match transitions
    private Digraph G; // epsilon transitions
    private int M; // States num

    public NFA(String regex) {
        re = regex.toCharArray();
        M = re.length;
        G = buildEpsilonTransitionDigraph();
    }

    private Digraph buildEpsilonTransitionDigraph() {
        Digraph G = new Digraph(M+1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            int lp = i;

            if (re[i] == '(' || re[i] == '|') ops.push(i);

            else if (re[i] == ')') {
                int or = ops.pop();

                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                }

                else lp = or;
            }

            if (i < M-1 && re[i+1] == '*') {
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }

            // + operator implementation for assignment 3
            if (i < M-1 && re[i+1] == '+') {
                G.addEdge(i+1, lp);
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')') G.addEdge(i, i+1);
        }

        return G;
    }

    public boolean recognizes(String txt) {
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);

        for (int v = 0; v < G.V(); v++) if (dfs.marked(v)) pc.add(v);

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<>();

            for (int v : pc) if (v < M && (re[v] == txt.charAt(i) || re[v] == '.')) match.add(v+1);

            pc = new Bag<>();
            dfs = new DirectedDFS(G, match);

            for (int v = 0; v < G.V(); v++) if (dfs.marked(v)) pc.add(v);
        }

        for (int v : pc) if (v == M) return true;

        return false;
    }
}
