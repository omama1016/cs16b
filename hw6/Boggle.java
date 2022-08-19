
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Boggle {
    
    // File path of dictionary file
//    static String dictPath = "trivial_words.txt";
    static String dictPath = "words.txt";
    private static String[][] board;
    private static boolean[][] visited;

    private static class Node implements Comparable<Node>{
        int row;
        int col;
        String s;

        public Node(int row, int col, String c) {
            this.row = row;
            this.col = col;
            this.s = c;
        }

        @Override
        public int compareTo(Node other) {
            if (this.s.length() > other.s.length()) {
                return -1;
            } else if (this.s.length() == other.s.length()) {
                return this.s.compareTo(other.s);
            } else {
                return 1;
            }
        }

    }
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {

        // YOUR CODE HERE
        if (k <= 0) throw new IllegalArgumentException();
        ArrayList<String> result = new ArrayList<>();
        HashSet<String> set = new HashSet();
        MinPQ<Node> queue = new MinPQ<>();
        Trie trie = new Trie(dictPath);
        board = readData(boardFilePath);
        visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].equals("qu")) continue;
                solveHelp(board[i][j], i, j, queue, trie);
            }
        }

        int count = 0;
        while (count < k) {
            if (!queue.isEmpty()) {
                String r = queue.delMin().s;
                if (!set.contains(r)) {
                    result.add(r);
                    set.add(r);
                    count++;
                }
            } else {
                break;
            }

        }
        return result;
    }

    private static void solveHelp(String cur, int row, int col, MinPQ<Node> res, Trie trie) {

        if (cur == null) {
            return;
        }

        // prune
        if (!trie.prefix(cur)) {
            return;
        }

        visited[row][col] = true;
        if (cur.length() >= 3) {
            if (trie.search(cur)) {
                res.insert(new Node(row, col, cur));
            }
        }

        for (Node neighbor : notVisitedNeighbors(row, col)) {
            solveHelp(cur + neighbor.s, neighbor.row, neighbor.col, res, trie);
        }

        visited[row][col] = false;
    }

    private static boolean inBounds(int r, int c){
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
            return false;
        }
        return true;
    }

    private static List<Node> notVisitedNeighbors(int r, int c) {
        List<Node> temp = new ArrayList<>();
        // topLeft
        if (inBounds(r - 1, c - 1) && !visited[r - 1][c - 1])
            temp.add(new Node(r-1, c-1, board[r-1][c-1]));
        // topRight
        if (inBounds(r - 1, c + 1) && !visited[r - 1][c + 1])
            temp.add(new Node(r-1, c+1, board[r-1][c+1]));
        // top
        if (inBounds(r - 1, c) && !visited[r - 1][c])
            temp.add(new Node(r-1, c, board[r-1][c]));
        // left
        if (inBounds(r, c - 1) && !visited[r][c - 1])
            temp.add(new Node(r, c-1, board[r][c-1]));
        // right
        if (inBounds(r, c + 1) && !visited[r][c + 1])
            temp.add(new Node(r, c+1, board[r][c+1]));
        // down
        if (inBounds(r + 1, c) && !visited[r + 1][c])
            temp.add(new Node(r+1, c, board[r+1][c]));
        // downLeft
        if (inBounds(r + 1, c - 1) && !visited[r + 1][c - 1])
            temp.add(new Node(r+1, c-1, board[r+1][c-1]));
        // downRight
        if (inBounds(r + 1, c + 1) && !visited[r + 1][c + 1])
            temp.add(new Node(r+1, c+1, board[r+1][c+1]));
        return temp;
    }

    private static String[][] readData(String filename){
        In in = new In(filename);
        if (!in.exists()) throw new IllegalArgumentException();
        ArrayList<ArrayList<Character>> temp = new ArrayList<>();
        while (!in.isEmpty()) {
            String s = in.readLine();
            ArrayList<Character> cs = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                cs.add(s.charAt(i));
            }
            temp.add(cs);
            // not rectangle
            if (cs.size() != temp.get(0).size()) {
                throw new IllegalArgumentException();
            }
        }
        String[][] board = new String[temp.size()][temp.get(0).size()];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = String.valueOf(temp.get(i).get(j));
            }

        }
        return board;
    }

//    public static void main(String[] args) {
//        List<String> res = Boggle.solve(7, "exampleBoard.txt");
//        for (String s : res) {
//            System.out.println(s);
//        }
//    }
}
