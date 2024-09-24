import java.util.List;

class PeriodicUniformBSpline extends BSpline {
    PeriodicUniformBSpline(List<Point> points, int k, double h) {
        super(points, k, h);
        int n = points.size();
        for (int i = 0; i < knotVector.length; i++) {
            knotVector[i] = i % (n + k);
        }
    }
}