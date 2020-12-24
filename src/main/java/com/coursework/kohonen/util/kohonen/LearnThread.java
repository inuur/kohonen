package com.coursework.kohonen.util.kohonen;

import java.util.List;

import static com.coursework.kohonen.util.kohonen.Util.alpha;
import static com.coursework.kohonen.util.kohonen.Util.h;

public class LearnThread extends Thread {

    private final List<SampleNode> sampleNodes;
    private final List<MapNode> mapNodes;
    private final int start;
    private final int end;
    private final int stage;

    public LearnThread(List<SampleNode> sampleNodes, List<MapNode> mapNodes, int start, int end, int stage) {
        this.sampleNodes = sampleNodes;
        this.mapNodes = mapNodes;
        this.start = start;
        this.end = end;
        this.stage = stage;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            MapNode closestNode = sampleNodes.get(i).getClosestNode(mapNodes);
            for (MapNode mapNode : mapNodes) {
                double rho = mapNode.rho(closestNode);
                double coefficient = alpha(stage) * h(stage, rho);
                synchronized (mapNode) {
                    mapNode.weights[0] += coefficient * (sampleNodes.get(i).weights[0] - mapNode.weights[0]);
                    mapNode.weights[1] += coefficient * (sampleNodes.get(i).weights[1] - mapNode.weights[1]);
                    mapNode.weights[2] += coefficient * (sampleNodes.get(i).weights[2] - mapNode.weights[2]);
                }
            }
        }
    }
}


