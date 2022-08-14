package lab11.graphs;

import edu.princeton.cs.algs4.StdDraw;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                dfs(i);
            }
        }

    }

    // Helper methods go here
    public void dfs(int v) {
        marked[v] = true;
        for (int w : maze.adj(v)) {
            if (marked[w] && edgeTo[w] != v) {
                StdDraw.setPenColor(StdDraw.BLUE);
                int vX = maze.toX(v);
                int vY = maze.toY(v);
                int wX = maze.toX(w);
                int wY = maze.toY(w);
                int pX = maze.toX(edgeTo[w]);
                int pY = maze.toY(edgeTo[w]);
                StdDraw.line(vX + 0.5, vY + 0.5, wX + 0.5, wY + 0.5);
                StdDraw.line(wX + 0.5, wY + 0.5, pX + 0.5, pY + 0.5);
                StdDraw.line(pX + 0.5, pY + 0.5, vX + 0.5, vY + 0.5);
                announce();
                return;
            }
            else {
                edgeTo[w] = v;
                //distTo[w] = distTo[v] + 1;
                dfs(w);
            }
        }
    }
}

