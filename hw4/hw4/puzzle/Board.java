package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{

    private final int BLANK = 0;
    private int N;
    private int[][] board;
    private int[][] goal;

    public Board(int[][] tiles) {
        this.N = tiles.length;
        // goal state
        goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = i * N + j + 1;
            }
        }
        goal[N-1][N-1] = 0;
        // copy
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(tiles[i], 0, board[i], 0, N);
        }

    }
    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("OUT OF SCOPE!!!");
        }
        return board[i][j];
    }
    public int size() {
        return N;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) continue;
                if (board[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private int toX(int v){
        return (v - 1) / N;
    }
    private int toY(int v) {
        return (v - 1) % N ;
    }

    public int manhattan() {

        int dis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = 0;
                int y = 0;
                if (board[i][j] == 0) {
                    continue;
                } else {
                    x = toX(board[i][j]);
                    y = toY(board[i][j]);
                }
                dis += Math.abs(i - x) + Math.abs(j - y);
            }
        }
        return dis;
    }
    public boolean equals(Object y) {
        Board other = (Board) y;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (board[i][j] != other.board[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
        return this.toString().equals(other.toString());
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
