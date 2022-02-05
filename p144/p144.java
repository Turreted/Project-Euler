import java.awt.geom.AffineTransform;

public class Solution {

    public double computeTangentSlope(double x, double y) {
        /*
        compute the tangent slope at a given point
        */
        return -4 * (x / y);
    }

    public double[] computeIntercept(double m, double bOffset, double curX) {

        // y^2 + 4x^2 = 100 and y = mx + b; compute the result as a system
        // (mx + b)^2 + 4x^2 - 100 = 0 = (m^2 + 4)x^2 + 2mbx + (b^2 - 100)

        // expand (mx + b)^2 using binomial therom stuff (a^2 + 2ab + b^2)
        double a = Math.pow(m, 2) + 4; // ADD 4
        double b = 2 * m * bOffset;
        double c = Math.pow(bOffset, 2) - 100;  // subtract 100 from constant value bc math

        // if roots are imaginary, return null
        double squarePart = Math.sqrt(Math.pow(b, 2) - (4 * a * c));
        if (Double.isNaN(squarePart)) {
            return new double[]{Double.NaN, Double.NaN};
        }

        // compute valid roots
        double firstRoot = (-b + squarePart)  / (2 * a);
        double secondRoot = (-b - squarePart) / (2 * a);

        // the next root is the one we're not currently at
        double nextRoot = (Math.abs(curX - firstRoot) > Math.abs(curX - secondRoot)) ? firstRoot : secondRoot;

        // plug into the line function to find y-value
        double yValue = m * nextRoot + bOffset;
        return new double[]{nextRoot, yValue};
    }

    public double[] computeReflect(double m, double b, double curX) {

        // compute the next line and the tangent line at that point
        double[] interceptPoint = computeIntercept(m, b, curX);
        double tanSlope = computeTangentSlope(interceptPoint[0], interceptPoint[1]);
        double tanB = interceptPoint[1] - (tanSlope * interceptPoint[0]);  // b = y - mx

        // compute the next point in the current line
        double xOffset = 0.1;
        double sampleX = interceptPoint[0] + xOffset;
        double sampleY = (m * (interceptPoint[0] + xOffset)) + b;  // y = mx +b

        // compute the line perpendicular to tangent line that intersects the point
        double perpTanSlope = -(1 / tanSlope);
        double perpB = -(perpTanSlope * sampleX) + sampleY;  // compute b of new line

        // find the point where the tangent and the perpendicular tangent intersect
        double tanInterceptX = (perpB - tanB) / (tanSlope - perpTanSlope);
        double tanInterceptY = (tanSlope * tanInterceptX) + tanB;

        // find the next point in the refelcted line using the reflected point
        double reflectedX = tanInterceptX + (tanInterceptX - sampleX);
        double reflectedY = tanInterceptY + (tanInterceptY - sampleY);

        // compute the slope of the next line using point slope
        double reflectedSlope = (interceptPoint[1] - reflectedY) / (interceptPoint[0] - reflectedX);
        double reflectedB = interceptPoint[1] - (reflectedSlope * interceptPoint[0]);

        System.out.println("y = " + reflectedSlope + "x + " + reflectedB);

        // return the slope, offset, and curx
        return new double[]{reflectedSlope, reflectedB, interceptPoint[0], interceptPoint[1]};
    }

    public int calculate(double slope, double b) {
        double interceptX = 0;
        double interceptY = 0;
        int iter = 0;

        while (!(-0.01 <= interceptX && interceptX <= 0.01 && interceptY > 0)) {
            double[] res = computeReflect(slope, b, interceptX);

            slope = res[0];
            b = res[1];
            interceptX = res[2];
            interceptY = res[3];

            iter++;
        }

        return iter-1;
    }

    public static void main(String[] args) {
        double initSlope = -14.071428571428493;
        double initOffset = 10.1;

        Solution s = new Solution();
        int res = s.calculate(initSlope, initOffset);

        System.out.println(res);
        return;
    }
}