package com.coursework.kohonen.util.kohonen;

public class Util {

    private static final double SIGMA0 = 60
        ;
    private static final double ALPHA0 = 0.3;
    private static final double B = 0.1;
    private static final double R = 0.1;

    public static double alpha(int t) {
        double B = 1000;
        return ALPHA0 * Math.exp(-(double) t / B);
    }

    public static double h(int t, double rho) {
        return Math.exp(-Math.pow(rho, 2) / (2 * Math.pow(sigma(t), 2)));
    }

    public static double sigma(int t) {
        double B = 1000;
        return SIGMA0 * Math.exp(-(double) t / B);
    }
}
