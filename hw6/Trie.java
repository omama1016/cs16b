import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class Trie {

    private Node root;

    private class Node {
        char c;
        boolean isLeaf;
        HashMap<Character, Node> children;

        public Node (char c) {
            this.c = c;
            children = new HashMap<>();
        }
    }

    public boolean prefix(String s){
        Node p = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!p.children.containsKey(c)) {
                return false;
            }
            p = p.children.get(c);
        }
        return true;
    }

    public void insert(String s) {
        Node p = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!p.children.containsKey(c)) {
                Node newNode = new Node(c);
                p.children.put(c, newNode);
            }
            p = p.children.get(c);
            if (i == s.length() - 1) p.isLeaf = true;
        }
    }

    public boolean search(String s) {
        Node p = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!p.children.containsKey(c)) {
                return false;
            }
            p = p.children.get(c);
            if (i == s.length() - 1 && p.isLeaf) {
                return true;
            }
        }
        return false;
    }

    public Trie(){
        root = new Node('-');
    }

    public Trie(String filename) {
        In in = new In(filename);
        root = new Node('-');
        while (!in.isEmpty()) {
            String s = in.readString();
            insert(s);
        }
    }

//    public static void main(String[] args) {
//        Trie trie = new Trie();
//        trie.insert("sam");
//        trie.insert("string");
//        System.out.println(trie.search("string"));
//        System.out.println(trie.search("str"));
//        System.out.println(trie.search("sam"));
//        System.out.println(trie.search("hello"));
//
//        Trie t2 = new Trie("words.txt");
//        In in = new In("words.txt");
//        while (!in.isEmpty()) {
//            if (!t2.search(in.readString())) {
//                System.out.println("wrong ......!!!!");
//                break;
//            }
//        }
//    }
}
