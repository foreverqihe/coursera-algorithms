import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FastCollinearPoints {
    
    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        this.points = points;
        if (points == null || Arrays.stream(points).anyMatch(p -> null == p)) {
            throw new IllegalArgumentException();
        }

    }

    public int numberOfSegments() {
        // the number of line segments
    }

    public LineSegment[] segments() {
        // the line segments
        Arrays.sort(points, Point);
    }
}
