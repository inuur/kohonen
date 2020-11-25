package com.coursework.kohonen.util;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SomHH {

    static final int n = 82000;
    static int N = 0;
    static final int m = 4;
    private static String fname = "vk_pers4.csv"; //
    static double k[][] = new double[n][m + 2];
    static final int WEIGHT = 20;//pixels
    static final int CELL_COUNT = 25;
    static final int CLASSCOUNT = CELL_COUNT * CELL_COUNT;
    public static Node[] nodes = new Node[CLASSCOUNT];

    public static int currentIteration = 0;

    //trainig parametrs
    static final double SIGMA0 = 40;
    static final double B = 0.1;
    static final double ALPHA0 = 1;
    static final double R = 0.1;
    static final int STEP_COUNT = 1000;

    private JPanel panel;

    public SomHH() {
        initNode();
    }

    public static void main(String[] args) {
        SomHH knet = new SomHH();
        knet.readCSV();
        knet.main1();
    }

    public void main1() {

        System.out.print("start ... ");

        System.out.println(N + " elements ");

        for (int t = 1; t < STEP_COUNT; t++) {
            System.out.println("Step " + t);
            currentIteration = t;

            int NUMBER_OF_THREADS = 6;
            SOMExecutor[] executors = new SOMExecutor[NUMBER_OF_THREADS];

            int div = N / 20;
            for (int i = 0; i < executors.length; i++) {
                int start = i * div;
                int end = Math.min(start + div, N);
                executors[i] = new SOMExecutor(start, end, t);
                executors[i].start();
            }

            for (SOMExecutor executor : executors) {
                try {
                    executor.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("end ... ");
    }

    public static double alpha(int t) {
        return ALPHA0 * Math.exp(-R * t);
    }

    public static double sigma(int t) {
        return SIGMA0 * Math.exp(-B * t);
    }

    public static double h(int t, double rho) {
        return Math.exp(-rho / (2 * sigma(t)));
    }

    public static double rho(Node node1, Node node2) {
        return Math.sqrt((node1.x - node2.x) * (node1.x - node2.x) +
            (node1.y - node2.y) * (node1.y - node2.y));
    }

    public static void initNode() {
        int nodeNum = 0;
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++, nodeNum++) {
                nodes[nodeNum] = new Node();
                nodes[nodeNum].x = j * WEIGHT;
                nodes[nodeNum].y = i * WEIGHT;
                nodes[nodeNum].w[0] = i;
                nodes[nodeNum].w[1] = j;
                nodes[nodeNum].w[2] = i / 2 + j / 2;
            }
        }
        System.out.println("Init complete");
    }

    public static void readCSV() {
        System.out.println(System.getProperty("user.dir"));
        File file = null;

        file = new File("C:\\Users\\lolip\\Desktop\\HS Projects\\kohonen\\src\\main\\resources\\vk_pers4.csv");
        System.out.println(file.exists());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
            String line = "";
            int i = 0;

            line = br.readLine();
            while ((line = br.readLine()) != null && i < n) {

                String[] elements = line.split(";");
                try {
                    k[i][4] = Double.parseDouble(elements[0]);
                    k[i][0] = Double.parseDouble(elements[1]);
                    k[i][1] = Double.parseDouble(elements[2]);
                    k[i][2] = Double.parseDouble(elements[3]);
                    k[i][3] = Double.parseDouble(elements[4]);
                    i++;
                } catch (NumberFormatException e) {
                    System.out.println("ошибка входных данных в строке " + i + " : " + line);
                }
                //System.out.println(k[i][0]+";"+k[i][1]+";"+k[i][2]+";"+k[i][3]+";"+k[i][4]);
            }
            N = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
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