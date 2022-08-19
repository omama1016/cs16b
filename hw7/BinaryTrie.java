import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {

    // alphabet size of extended ASCII
    private static final int R = 256;
    private Node root;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        root = builtTire(frequencyTable);
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node p = root;
        Match match = null;
        BitSequence subSeq = new BitSequence();
        for (int i = 0; i < querySequence.length(); i++) {
            if (querySequence.bitAt(i) == 0) {
                p = p.left;
                subSeq = subSeq.appended(0);
            }
            else {
                p = p.right;
                subSeq = subSeq.appended(1);
            }
            if (p.isLeaf()) {
                match = new Match(subSeq, p.ch);
                break;
            }
        }
        return match;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookup = new HashMap<>();
        buildLookupHelp(root, "", lookup);
        return lookup;
    }

    private void buildLookupHelp(Node p, String str, Map<Character, BitSequence> lookup) {
        if (p.isLeaf()) {
            lookup.put(p.ch, new BitSequence(str));
        } else {
            buildLookupHelp(p.left, str + "0", lookup);
            buildLookupHelp(p.right, str + "1", lookup);
        }
    }

    // Huffman trie node
    private class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    private Node builtTire(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        int[] freq = new int[R];
        for (Map.Entry<Character, Integer> entry :frequencyTable.entrySet()) {
            freq[entry.getKey()] = entry.getValue();
        }

        for (char c = 0; c < R; c++)
            if (freq[c] > 0)
                pq.insert(new Node(c, freq[c], null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();

    }

}


