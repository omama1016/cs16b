package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private boolean[][] sites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;
    private int numOfOpenSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("No Illegal!");
        }
        this.N = N;
        uf = new WeightedQuickUnionUF(N*N + 2);
        ufTop = new WeightedQuickUnionUF(N*N + 2);
        for (int i = 0; i < N; i++) {
            uf.union(N*N, i);
            ufTop.union(N*N, i);
            uf.union(N*N + 1, N*N-N+i);
        }
        numOfOpenSites = 0;
        // default is false, denote blocked
        sites = new boolean[N][N];

    }

    private boolean isPositionValid(int row, int col){
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return false;
        }
        return true;
    }
    private int xyTo1D(int row, int col){
        return row * N + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isPositionValid(row, col)) {
            throw new IndexOutOfBoundsException("Index is Out of Bound...");
        }
        // top row is always full
        if (!sites[row][col]) {
            sites[row][col] = true;
            numOfOpenSites++;
            if (isPositionValid(row-1, col) && sites[row-1][col]) {
                uf.union(xyTo1D(row-1, col), xyTo1D(row, col));
                ufTop.union(xyTo1D(row-1, col), xyTo1D(row, col));
            }

            if (isPositionValid(row + 1, col) && sites[row + 1][col]) {
                uf.union(xyTo1D(row, col), xyTo1D(row+1, col));
                ufTop.union(xyTo1D(row, col), xyTo1D(row+1, col));
            }

            if (isPositionValid(row, col + 1) && sites[row][col + 1]) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col+1));
                ufTop.union(xyTo1D(row, col), xyTo1D(row, col+1));
            }

            if (isPositionValid(row, col - 1) && sites[row][col - 1]) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col-1));
                ufTop.union(xyTo1D(row, col), xyTo1D(row, col-1));
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isPositionValid(row, col)) {
            throw new IndexOutOfBoundsException("Index is Out of Bound...");
        }
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isPositionValid(row, col)) {
            throw new IndexOutOfBoundsException("Index is Out of Bound...");
        }
        return isOpen(row, col) && ufTop.connected(xyTo1D(row, col), N*N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }
    // does the system percolate?
    public boolean percolates() {
        if (N == 1) {
            return sites[0][0];
        }
        return uf.connected(N*N, N*N+1);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
