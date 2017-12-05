import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BruteCollinearPoints {

    private Point[] points;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null || Arrays.stream(points).anyMatch(p -> null == p)) {
            throw new IllegalArgumentException();
        }

        Set<Point> result = Arrays.stream(points).collect(Collectors.toSet());
        if (result.size() != 4) {
            throw new IllegalArgumentException();
        }

        this.points = points;
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments().length;
    }

    private List<Point[]> getCombination() {

        int[] input = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            input[i] = i;
        }
        int k = 4; // sequence length

        List<int[]> subsets = new ArrayList<>();

        int[] s = new int[k]; // here we'll keep indices
                              // pointing to elements in input array

        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++)
                ;
            subsets.add(getSubset(input, s));
            for (;;) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--)
                    ;
                if (i < 0) {
                    break;
                }
                s[i]++; // increment this item
                for (++i; i < k; i++) { // fill up remaining items
                    s[i] = s[i - 1] + 1;
                }
                subsets.add(getSubset(input, s));
            }
        }

        return subsets.stream()
                .map(sub -> new Point[] { points[sub[0]], points[sub[1]], points[sub[2]], points[sub[3]] })
                .collect(Collectors.toList());
    }

    // generate actual subset by index sequence
    private int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }

    public LineSegment[] segments() {
        List<LineSegment> segments = new ArrayList<>();
        for (Iterator<Point[]> it = getCombination().iterator(); it.hasNext();) {
            Point[] localPoints = it.next();
            Arrays.sort(localPoints);
            double s0 = localPoints[0].slopeTo(localPoints[1]);
            double s1 = localPoints[0].slopeTo(localPoints[2]);
            double s2 = localPoints[0].slopeTo(localPoints[3]);
            double threshold = 0.0001;
            if (Math.abs(s0 - s1) < threshold && Math.abs(s0 - s2) < threshold && Math.abs(s1 - s2) < threshold) {
                segments.add(new LineSegment(localPoints[0], localPoints[3]));
            }
        }
        return segments.toArray(new LineSegment[segments.size()]);
    }
}