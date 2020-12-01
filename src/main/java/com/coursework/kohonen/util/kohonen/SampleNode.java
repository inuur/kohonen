package com.coursework.kohonen.util.kohonen;

import java.util.List;

public class SampleNode extends BaseNode {

    int kmeans;

    public SampleNode(double[] weights) {
        this.weights[0] = weights[1];
        this.weights[1] = weights[2];
        this.weights[2] = weights[3];
        kmeans = (int) weights[0];
    }

    public MapNode getClosestNode(List<MapNode> nodes) {
        MapNode currentClosestNode = null;
        double currentMinDistance = Double.MAX_VALUE;
        for (MapNode node : nodes) {
            double distance = distanceTo(node);
            if (distance < currentMinDistance) {
                currentClosestNode = node;
                currentMinDistance = distance;
            }
        }
        return currentClosestNode;
    }
}
