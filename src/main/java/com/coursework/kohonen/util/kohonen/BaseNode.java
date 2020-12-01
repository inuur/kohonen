package com.coursework.kohonen.util.kohonen;

import java.io.Serializable;

public class BaseNode implements Serializable {

    public double[] weights;

    public BaseNode() {
        this.weights = new double[3];
    }

    public double distanceTo(BaseNode node) {
        double distance = 0;
        for (int i = 0; i < 3; i++) {
            distance += Math.pow(weights[i] - node.weights[i], 2);
        }
        return distance;
    }
}
