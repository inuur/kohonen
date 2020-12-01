package com.coursework.kohonen.util.kohonen;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KohonenMap {

    private static final int CELL_COUNT = 25;
    private static final int MAP_TOTAL_NODES = CELL_COUNT * CELL_COUNT;
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static final List<MapNode> mapNodes = new ArrayList<>(MAP_TOTAL_NODES);
    private static final List<SampleNode> sampleNodes = new ArrayList<>();

    private static final DataNormalizer dataNormalizer = new DataNormalizer();
    private static final LearnDataReader dataReader = new LearnDataReader(dataNormalizer);

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

    public void study(int totalStage, boolean deserializeData) {

        if (deserializeData) {
            try {
                FileInputStream fileInputStream = new FileInputStream("trained_data");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                mapNodes.clear();
                mapNodes.addAll((ArrayList<MapNode>) objectInputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }

        for (int stage = 1; stage < totalStage; stage++) {

            List<Thread> threads = new ArrayList<>();
            int range = LearnDataReader.getTotalDataLength() / AVAILABLE_PROCESSORS;

            for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
                threads.add(
                    new LearnThread(sampleNodes, mapNodes, i * range, Math.min(i * range + range, LearnDataReader.getTotalDataLength()), stage)
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

            System.out.println(stage);
            currentIteration = stage;
        }

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
}
