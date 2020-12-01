package com.coursework.kohonen.util.kohonen;

import java.io.Serializable;
import java.util.Random;

public class MapNode extends BaseNode implements Serializable {

    private static final Random random = new Random(1);

    public int x;
    public int y;

    public int[] kmeansCounter = new int[6];

    public int numOfSamples = 0;

    public MapNode(int x, int y) {
        this.x = x * 25;
        this.y = y * 25;
        weights[0] = random.nextInt(20);
        weights[1] = random.nextInt(20);
        weights[2] = random.nextInt(20);
    }

    public double rho(MapNode node) {
        return Math.sqrt(Math.pow(x - node.x, 2) + Math.pow(y - node.y, 2));
    }

    public void incrementNumOfSamples() {
        numOfSamples++;
    }

    public int getNumOfSamples() {
        return numOfSamples;
    }

    public void incrementKmeansCounter(int kmeans) {
        kmeansCounter[kmeans]++;
    }
}
