import java.util.List;

class NonPeriodicUniformBSpline extends BSpline {
    NonPeriodicUniformBSpline(List<Point> points, int k, double h) {
        super(points, k, h);
        int n = points.size();
        for (int i = 0; i < k; i++) {
            knotVector[i] = 0;
        }
        for (int i = k; i < n; i++) {
            knotVector[i] = i - k + 1;
        }
        for (int i = n; i < knotVector.length; i++) {
            knotVector[i] = n - k + 1;
        }
    }
}