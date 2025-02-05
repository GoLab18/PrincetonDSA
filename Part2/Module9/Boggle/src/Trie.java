public class Trie {
    private Node root = new Node();

    private static class Node {
        private int score;
        private final Node[] next = new Node[26]; // Space for every capital letter in the english alphabet
    }

    public boolean contains(char[] wordCharArr, int len) {
        if (wordCharArr == null) throw new IllegalArgumentException("Word received is null");
        return get(wordCharArr, len) != 0;
    }

    public int get(char[] wordCharArr, int len) {
        if (wordCharArr == null) throw new IllegalArgumentException("Word received is null");
        if (wordCharArr.length < len) throw new IllegalArgumentException("Length param is outside of char array's bounds");

        Node x = root;
        for (int d = 0; d < len; d++) {
            if (x == null) return 0;

            x = x.next[wordCharArr[d]-'A'];
        }

        return (x != null) ? x.score : 0;
    }

    public void put(String word, int score) {
        if (word == null) throw new IllegalArgumentException("Word received is null");

        if (root == null) root = new Node();

        Node x = root;
        for (int d = 0; d < word.length(); d++) {
            char c = word.charAt(d);

            if (x.next[c-'A'] == null) x.next[c-'A'] = new Node();

            x = x.next[c-'A'];
        }

        x.score = score;
    }

    public boolean hasPrefix(char[] wordCharArr, int len) {
        Node x = root;
        for (int d = 0; d < len; d++) {
            if (x == null) return false;

            x = x.next[wordCharArr[d]-'A'];
        }

        return x != null;
    }
}
