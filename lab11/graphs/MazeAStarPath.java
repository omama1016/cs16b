package lab11.graphs;
import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    MinPQ<Integer> queue;
    Map<Integer, Integer> nodePriMapping;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        queue = new MinPQ<Integer>();
        nodePriMapping = new HashMap<>();
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(s) - maze.toX(t)) + Math.abs(maze.toY(s) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        nodePriMapping.put(distTo[s], s);
        queue.insert(distTo[s]);
        while (!queue.isEmpty()) {
            int v = nodePriMapping.get(queue.delMin());
            marked[v] = true;
            announce();

            if (v == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + h(w);
                    nodePriMapping.put(distTo[w], w);
                    queue.insert(distTo[w]);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

