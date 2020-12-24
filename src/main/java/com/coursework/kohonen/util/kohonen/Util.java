package com.coursework.kohonen.util.kohonen;

public class Util {

    private static final double SIGMA0 = 500;
    private static final double ALPHA0 = 0.2;
    private static final double B = 0.001;
    private static final double R = 0.001;

    public static double alpha(int t) {
        return ALPHA0 * Math.exp(-(double) t * R);
    }

    public static double h(int t, double rho) {
        return Math.exp(-Math.pow(rho, 2) / (2 * Math.pow(sigma(t), 1)));
    }

    public static double sigma(int t) {
        return SIGMA0 * Math.exp(-(double) t * B);
    }
}
