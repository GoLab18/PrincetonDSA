import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;
    public Outcast(WordNet wordnet) {
        this.wn = wordnet;
    }

    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException("Nouns array can't be null");

        String oc = null;
        int biggestDist = Integer.MIN_VALUE;

        for (String n1 : nouns) {
            int distSum = 0;

            for (String n2 : nouns) distSum += wn.distance(n1, n2);

            if (biggestDist < distSum) {
                biggestDist = distSum;
                oc = n1;
            }
        }
        return oc;
    }

    // Test
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
