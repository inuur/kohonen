package com.coursework.kohonen.util.newkohonen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class KohonenMap {

    private static double MAX0 = Double.MIN_VALUE;
    private static double MAX1 = Double.MIN_VALUE;
    private static double MAX2 = Double.MIN_VALUE;
    private static double MAX3 = Double.MIN_VALUE;
    private static double MAX4 = Double.MIN_VALUE;

    private static double MIN0 = Double.MAX_VALUE;
    private static double MIN1 = Double.MAX_VALUE;
    private static double MIN2 = Double.MAX_VALUE;
    private static double MIN3 = Double.MAX_VALUE;
    private static double MIN4 = Double.MAX_VALUE;

    private static final int MAP_SIZE = 25;

    private static final double SIGMA = 10;
    private static final double ALPHA = 10;
    private static final double B = 10;
    private static final double R = 10;
    private static int N = 0;

    private static final Node[] nodes = new Node[MAP_SIZE * MAP_SIZE];
    private static final double[][] k = new double[81812][5];


    public KohonenMap() {
        initWeights();
    }

    private void initWeights() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                nodes[i * MAP_SIZE + j] = new Node(i, j);
            }
        }
    }

    private double sigma(int t) {
        return SIGMA * Math.exp(-B * t);
    }

    private double h(int t, double ro) {
        return Math.exp(-ro / (2 * sigma(t)));
    }

    private double alpha(int t) {
        return ALPHA * Math.exp(-R * t);
    }

    private void study() {
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < N; j++) {

            }
        }
    }

    private Node getClosestNode(double[] vector) {
        double currentMinDistance = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < nodes.length; i++) {
            double distance = getDistance(vector, nodes[i].weights);
            if (currentMinDistance > distance) {
                currentMinDistance = distance;
                index = i;
            }
        }
        return nodes[index];
    }

    private double getDistance(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(result);
    }


    public void readCSV() {
        File file = new File("C:\\Users\\lolip\\Desktop\\kohonen\\src\\main\\resources\\vk_pers4.csv");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
            int i = 0;
            String line = br.readLine();
            while ((line = br.readLine()) != null && i < 81812) {
                String[] elements = line.split(";");
                try {
                    k[i][4] = Double.parseDouble(elements[0]);
                    k[i][0] = Double.parseDouble(elements[1]);
                    k[i][1] = Double.parseDouble(elements[2]);
                    k[i][2] = Double.parseDouble(elements[3]);
                    k[i][3] = Double.parseDouble(elements[4]);
                    i++;
                    N = i;
                    analyze(k[i - 1]);
                } catch (NumberFormatException e) {
                    System.out.println("ошибка входных данных в строке " + i + " : " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        normalize();
    }

    private void analyze(double[] k) {
        MAX0 = Math.max(k[0], MAX0);
        MAX1 = Math.max(k[1], MAX1);
        MAX2 = Math.max(k[2], MAX2);
        MAX3 = Math.max(k[3], MAX3);
        MAX4 = Math.max(k[4], MAX4);

        MIN0 = Math.min(k[0], MIN0);
        MIN1 = Math.min(k[1], MIN1);
        MIN2 = Math.min(k[2], MIN2);
        MIN3 = Math.min(k[3], MIN3);
        MIN4 = Math.min(k[4], MIN4);
    }

    private void normalize() {
        for (int i = 0; i < N; i++) {
            k[i][0] = (k[i][0] - MIN0) / (MAX0 - MIN0);
            k[i][1] = (k[i][1] - MIN1) / (MAX1 - MIN1);
            k[i][2] = (k[i][2] - MIN2) / (MAX2 - MIN2);
            k[i][3] = (k[i][3] - MIN3) / (MAX3 - MIN3);
            k[i][4] = (k[i][4] - MIN4) / (MAX4 - MIN4);
        }
    }

    public static void main(String[] args) {
        KohonenMap map = new KohonenMap();
        map.readCSV();
    }
}
