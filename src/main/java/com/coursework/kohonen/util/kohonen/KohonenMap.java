package com.coursework.kohonen.util.kohonen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.coursework.kohonen.util.kohonen.Util.alpha;
import static com.coursework.kohonen.util.kohonen.Util.h;

public class KohonenMap {

    private static final int CELL_COUNT = 25;
    private static final int MAP_TOTAL_NODES = CELL_COUNT * CELL_COUNT;


    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static final List<MapNode> mapNodes = new ArrayList<>(MAP_TOTAL_NODES);
    private static final List<SampleNode> sampleNodes = new ArrayList<>();

    private static final LearnDataReader dataReader = new LearnDataReader();

    public static int currentIteration = 0;

    public KohonenMap() {
        initWeights();
        sampleNodes.addAll(dataReader.readData());
    }

    private void initWeights() {
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                mapNodes.add(new MapNode(i, j));
            }
        }
    }

    private void deserialize() {
        try {
            FileInputStream fileInputStream = new FileInputStream("trained_data");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mapNodes.clear();
            mapNodes.addAll((ArrayList<MapNode>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("trained_data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mapNodes);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public void study(int totalStage, boolean deserializeData) {

        if (deserializeData) {
            deserialize();
            return;
        }

        for (int stage = 1; stage < totalStage; stage++) {
            long start = System.currentTimeMillis();
            for (SampleNode sampleNode : sampleNodes) {
                MapNode closestNode = sampleNode.getClosestNode(mapNodes);
                for (MapNode mapNode : mapNodes) {
                    double rho = mapNode.rho(closestNode);
                    double coefficient = alpha(stage) * h(stage, rho);
                    mapNode.weights[0] += coefficient * (sampleNode.weights[0] - mapNode.weights[0]);
                    mapNode.weights[1] += coefficient * (sampleNode.weights[1] - mapNode.weights[1]);
                    mapNode.weights[2] += coefficient * (sampleNode.weights[2] - mapNode.weights[2]);
                }
            }
            long time = System.currentTimeMillis() - start;
            System.out.println(stage + " - " + time + "ms");
            currentIteration = stage;
        }

        serialize();
    }


    public void studyMultithreading(int totalStage, boolean deserializeData) {

        if (deserializeData) {
            deserialize();
            return;
        }

        for (int stage = 1; stage < totalStage; stage++) {
            long start = System.currentTimeMillis();

            List<Thread> threads = new ArrayList<>();

            int range = LearnDataReader.getTotalDataLength() / AVAILABLE_PROCESSORS;

            for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
                threads.add(
                    new LearnThread(sampleNodes, mapNodes, i * range,
                        Math.min(i * range + range, LearnDataReader.getTotalDataLength()), stage)
                );
            }

            threads.forEach(Thread::start);
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long time = System.currentTimeMillis() - start;
            System.out.println(stage + " - " + time + "ms");

            currentIteration = stage;
        }

        serialize();

    }

    public List<MapNode> getMapNodes() {
        return mapNodes;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void handleInput() {
        for (SampleNode sampleNode : sampleNodes) {
            MapNode mapNode = sampleNode.getClosestNode(mapNodes);
            mapNode.incrementNumOfSamples();
            mapNode.incrementKmeansCounter(sampleNode.kmeans);
        }
    }

    public static void main(String[] args) {
        KohonenMap kohonenMap = new KohonenMap();
        kohonenMap.study(1000, false);
        kohonenMap.handleInput();
    }

    public void countColors() {

        int red = 0;
        int green = 0;
        int blue = 0;

        for (MapNode mapNode : mapNodes) {
            if (mapNode.weights[0] > 10 && mapNode.weights[1] < 9 && mapNode.weights[2] < 9)
                red++;
            if (mapNode.weights[0] < 9 && mapNode.weights[1] > 10 && mapNode.weights[2] < 9)
                green++;
            if (mapNode.weights[0] < 9 && mapNode.weights[1] < 9 && mapNode.weights[2] > 10)
                blue++;
        }

        System.out.println("RED: " + red);
        System.out.println("GREEN: " + green);
        System.out.println("BLUE: " + blue);

    }
}
