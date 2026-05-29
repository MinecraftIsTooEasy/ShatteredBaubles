package vbonedra.shattered_baubles.util;

public class MathAdditional {
    public static float minAbs(double a, double b) { return minAbs((float) a, (float) b); }
    public static float minAbs(float a, float b) {
        if (Math.abs(a) > Math.abs(b)) return b;
        return a;
    }
}
