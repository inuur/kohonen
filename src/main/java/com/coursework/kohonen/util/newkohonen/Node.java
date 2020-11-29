package com.coursework.kohonen.util.newkohonen;

import java.util.Random;

public class Node {

    private static final Random random = new Random();

    private int x;
    private int y;
    public double[] weights;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.weights = new double[3];
        initWeights();
    }

    private void initWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
