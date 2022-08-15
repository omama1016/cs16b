package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private MinPQ<SearchNode> fringe;
    private Stack<WorldState> solution;
    private int minMoves;
    private int totalNumInMinPQ;

    private class SearchNode implements Comparable{

        private WorldState worldState;
        private int numMoves;
        private SearchNode parent;
        private double hValue;

        public SearchNode(WorldState inital, double hValue) {
            this.worldState = inital;
            this.numMoves = 0;
            this.parent = null;
            this.hValue = hValue;
        }

        @Override
        public int compareTo(Object o) {
            SearchNode other = (SearchNode) o;
            if (this.numMoves + this.hValue
                         < other.numMoves + other.hValue){
                return -1;
            } else if (this.numMoves + this.hValue
                        == other.numMoves + other.hValue) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public Solver(WorldState initial) {
        solution = new Stack<>();
        fringe = new MinPQ<>();
        SearchNode initNode = new SearchNode(initial, initial.estimatedDistanceToGoal());
        fringe.insert(initNode);
        totalNumInMinPQ++;
        while (!fringe.isEmpty()) {
            SearchNode node = fringe.delMin();
            if (node.worldState.isGoal()) {
                minMoves = node.numMoves;
                SearchNode p = node;
                solution.push(p.worldState);
                while (p.parent != null) {
                    p = p.parent;
                    solution.push(p.worldState);
                }
                return;
            }
            for (WorldState state : node.worldState.neighbors()) {
                if (node.parent == null) {
                    SearchNode childNode = new SearchNode(state, state.estimatedDistanceToGoal());
                    childNode.numMoves += node.numMoves + 1;
                    childNode.parent = node;
                    fringe.insert(childNode);
                    totalNumInMinPQ++;
                }
                else {
                    if (!state.equals(node.parent.worldState)) {
                        SearchNode childNode = new SearchNode(state, state.estimatedDistanceToGoal());
                        childNode.numMoves += node.numMoves + 1;
                        childNode.parent = node;
                        fringe.insert(childNode);
                        totalNumInMinPQ++;
                    }
                }
            }
        }
    }
    public int moves() {
        return minMoves;
    }
    public Iterable<WorldState> solution() {
        return solution;
    }
}
