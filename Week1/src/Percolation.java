import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] sites;
    private WeightedQuickUnionUF qu;
    private int n;
    private int open = 0;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be equal to or bigger than 1");
        }
        sites = new boolean[n * n];
        qu = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = n * n;
        virtualBottom = virtualTop + 1;

        for (int i = 0; i < n * n; i++) {
            sites[i] = false;
        }
        this.n = n;

    }

    public void open(int row, int col) {
        checkParameters(row, col);
        // open site (row, col) if it is not open already
        int r = row - 1;
        int c = col - 1;
        int sitesIndex = r * n + c;
        if (!sites[sitesIndex]) {
            sites[sitesIndex] = true;
            open++;
        } else {
            return;
        }
        if (r == 0) {
            // connect to virtual top
            qu.union(sitesIndex, virtualTop);
        }
        if (r == n - 1) {
            qu.union(sitesIndex, virtualBottom);
        }

        int[][] validNeighbours = getValidNeighbours(r, c);
        for (int j = 0; j < validNeighbours.length; j++) {
            int[] neighbour = validNeighbours[j];
            if (neighbour == null) {
                continue;
            }
            int sitesNumber = neighbour[0] * n + neighbour[1];
            if (sites[sitesNumber]) {
                qu.union(sitesIndex, sitesNumber);
            }
        }
    }

    private int[][] getValidNeighbours(int r, int c) {
        int[][] neighbours = new int[][] { { r - 1, c }, { r + 1, c }, { r, c - 1 }, { r, c + 1 } };
        for (int i = 0; i < neighbours.length; i++) {
            int[] neighbour = neighbours[i];
            if (neighbour[0] < 0 || neighbour[0] >= n || neighbour[1] < 0 || neighbour[1] >= n) {
                neighbours[i] = null;
            }
        }
        return neighbours;
    }

    public boolean isOpen(int row, int col) {
        checkParameters(row, col);
        int r = row - 1;
        int c = col - 1;
        int sitesIndex = r * n + c;
        return sites[sitesIndex];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        checkParameters(row, col);
        int r = row - 1;
        int c = col - 1;
        int siteIndex = r * n + c;
        
        return qu.connected(virtualTop, siteIndex);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return open;
    }

    public boolean percolates() {
        // does the system percolate?
        return qu.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        // test client (optional)

    }

    private void checkParameters(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
    }
}
