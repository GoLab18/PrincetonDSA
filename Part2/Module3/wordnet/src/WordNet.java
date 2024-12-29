import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final SAP sap;
    private final Map<Integer, String> idSynset;
    private final Map<String, Set<Integer>> nounSynsets;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Input files can't be null");

        idSynset = new HashMap<>();
        nounSynsets = new HashMap<>();

        In sIn = new In(synsets);

        while (sIn.hasNextLine()) {
            String[] s = sIn.readLine().split(",");

            int id = Integer.parseInt(s[0]);
            String synset = s[1];

            idSynset.put(id, synset);
            for (String noun : synset.split(" ")) {
                nounSynsets.putIfAbsent(noun, new HashSet<>());
                nounSynsets.get(noun).add(id);
            }
        }

        Digraph g = new Digraph(idSynset.size());

        In hIn = new In(hypernyms);

        while (hIn.hasNextLine()) {
            String[] h = hIn.readLine().split(",");

            int sId = Integer.parseInt(h[0]);

            for (int i = 1; i < h.length; i++) {
                int hId = Integer.parseInt(h[i]);
                g.addEdge(sId, hId);
            }
        }

        checkIfRootedDAG(g);

        sap = new SAP(g);
    }

    public Iterable<String> nouns() {
        return nounSynsets.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Parameter word can't be null");

        return nounSynsets.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        validateNouns(nounA, nounB);

        return sap.length(nounSynsets.get(nounA), nounSynsets.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        validateNouns(nounA, nounB);

        int aId = sap.ancestor(nounSynsets.get(nounA), nounSynsets.get(nounB));

        return aId == -1 ? null : idSynset.get(aId);
    }

    private void validateNouns(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Noun parameters can't be null");
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Arguments must be valid nouns");
    }

    private void checkIfRootedDAG(Digraph G) {
        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle()) throw new IllegalArgumentException("Input is a cyclic graph");

        int roots = 0;

        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) roots++;
            if (roots > 1) throw new IllegalArgumentException("Input is not a rooted DAG");
        }

        if (roots != 1) throw new IllegalArgumentException("Input is not a rooted DAG");
    }

    // Test
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);

        System.out.println("Nouns -> " + wn.nouns());
        System.out.println("Is 'table' a noun? -> " + wn.isNoun("table"));
        System.out.println("'cat' and 'dog' distance -> " + wn.distance("cat", "dog"));
        System.out.println("SAP for 'cat' and 'dog' -> " + wn.sap("cat", "dog"));
    }
}
