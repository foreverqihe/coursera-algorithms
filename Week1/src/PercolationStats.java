import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private int n;
    private double[] openSites;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        openSites = doTest();
    }

    public double mean() {
        return StdStats.mean(openSites);
    }

    private double[] doTest() {
        double[] openRatio = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            openRatio[t] = ((double) p.numberOfOpenSites()) / (n * n);
        }
        return openRatio;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        if (trials == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(openSites);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        // test client (described below)
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[0]));
        // mean = 0.5929934999999997
        // stddev = 0.00876990421552567
        // 95% confidence interval = [0.5912745987737567, 0.5947124012262428]
        System.out.printf("mean            = %f", stats.mean());
        System.out.println();
        System.out.printf("stddev          = %f", stats.stddev());
        System.out.println();
        System.out.printf("confidence interval = [%f, %f]", stats.confidenceLo(), stats.confidenceHi());
    }
}
