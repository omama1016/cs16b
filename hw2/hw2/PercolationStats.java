package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PercolationStats {

    private double[] results;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf)  {

        if (N < 0 || T < 0) {
            throw new IllegalArgumentException("Illegal Augment!");
        }

        this.T = T;
        results = new double[T];
        Percolation percolation = pf.make(N);
        for (int i = 0; i < T; i++) {
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation.open(row, col);
            }
            results[i] = percolation.numberOfOpenSites() * 1.0 / (N * N);
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

//    public static void main(String[] args) {
//
//        for (int N = 50; N < 500; N+=50) {
//            PercolationStats percolationStats = new PercolationStats(50, 100, new PercolationFactory());
//            Stopwatch time = new Stopwatch();
//            System.out.println(percolationStats.mean());
//            double end = time.elapsedTime();
//            System.out.println(end + "------------------");
//        }
//
//    }
}
