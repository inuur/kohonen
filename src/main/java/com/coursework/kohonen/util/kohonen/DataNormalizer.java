package com.coursework.kohonen.util.kohonen;

import java.util.Arrays;
import java.util.List;

public class DataNormalizer {

    private static final int DATASET_LENGTH = 5;

    private double[] max = new double[DATASET_LENGTH];
    private double[] min = new double[DATASET_LENGTH];

    public DataNormalizer() {
        max = Arrays.stream(max).map(element -> Double.MIN_VALUE).toArray();
        min = Arrays.stream(min).map(element -> Double.MAX_VALUE).toArray();
    }

    public void analyze(double[] dataset) {
        if (dataset.length != DATASET_LENGTH) {
            throw new IllegalArgumentException("Wrong dataset length!");
        }

        for (int i = 0; i < DATASET_LENGTH; i++) {
            max[i] = Math.max(dataset[i], max[i]);
            min[i] = Math.min(dataset[i], min[i]);
        }
    }

    public List<SampleNode> normalizeDataSet(List<SampleNode> nodes) {
        for (SampleNode node : nodes) {
            for (int i = 0; i < node.weights.length; i++) {
                node.weights[i] = (node.weights[i] - min[i]) / (max[i] - min[i]);
            }
        }
        return nodes;
    }
}
