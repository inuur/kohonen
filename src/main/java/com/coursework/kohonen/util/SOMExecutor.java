package com.coursework.kohonen.util;

public class SOMExecutor extends Thread {

    private final int start;
    private final int end;
    private final int t;

    static final double SIGMA0 = 10;
    static final double B = 0.001;
    static final double ALPHA0 = 0.3;
    static final double R = 0.01;
    static final int N = 82000;

    public SOMExecutor(int start, int end, int t) {
        this.start = start;
        this.end = end;
        this.t = t;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            int index = vectorMinDistance(SomHH.k[i], SomHH.nodes);//W*
            for (int j = 0; j < SomHH.nodes.length; j++) {
                double rho = rho(SomHH.nodes[j], SomHH.nodes[index]);
                SomHH.nodes[j].w[0] = SomHH.nodes[j].w[0] + alpha(t) * h(t, rho)
                    * (SomHH.k[i][0] - SomHH.nodes[j].w[0]);

                SomHH.nodes[j].w[1] = SomHH.nodes[j].w[1] + alpha(t) * h(t, rho)
                    * (SomHH.k[i][1] - SomHH.nodes[j].w[1]);

                SomHH.nodes[j].w[2] = SomHH.nodes[j].w[2] + alpha(t) * h(t, rho)
                    * (SomHH.k[i][2] - SomHH.nodes[j].w[2]);
            }
        }
    }

    public static double alpha(int t) {
        return ALPHA0 * Math.exp(-(double) t / (N / Math.log(SIGMA0)));
    }

    public static double sigma(int t) {
        return SIGMA0 * Math.exp(-(double) t / (N / Math.log(SIGMA0)));
    }

    public static double h(int t, double rho) {
        return Math.exp(-Math.pow(rho, 2) / (2 * Math.pow(sigma(t), 2)));
    }

    public static double rho(Node node1, Node node2) {
        return Math.sqrt((node1.x - node2.x) * (node1.x - node2.x) +
            (node1.y - node2.y) * (node1.y - node2.y));
    }

    public static double distance(double[] a, double b[], int dim) {
        if (a == b) return 0;
        double d = 0;
        for (int i = 0; i < dim; i++) {
            d += (a[i] - b[i]) * (a[i] - b[i]);
        }
        //System.out.println(" dst = " + d + " dim = " + dim);

        return d;
    }

    public static int vectorMinDistance(double[] a, Node[] v) {
        int index = 0;
        double dst = distance(a, v[0].w, 3);

        for (int i = 1; i < v.length; i++) {
            double d = distance(a, v[i].w, 3);
            if (d < dst) {
                dst = d;
                index = i;
            }
        }

        return index;
    }
}
