package com.coursework.kohonen.util.kohonen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LearnDataReader {

    private static final int DATA_LENGTH = 82000;
    private static final String FILE_PATH = "C:\\Users\\lolip\\Desktop\\HS Projects\\kohonen\\src\\main\\resources\\vk_pers4.csv";
    private static int TOTAL_DATA_LENGTH = 0;

    private final DataNormalizer dataNormalizer;

    public LearnDataReader(DataNormalizer dataNormalizer) {
        this.dataNormalizer = dataNormalizer;
    }

    public List<SampleNode> readData() {

        List<SampleNode> data = new ArrayList<>(DATA_LENGTH);
        File file = new File(FILE_PATH);

        Scanner scanner;
        try {
            scanner = new Scanner(file);
            scanner.nextLine(); // skipping csv header
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Can't find the file!");
        }

        while (scanner.hasNextLine()) {
            String[] strElements = Arrays.copyOf(scanner.nextLine().split(";"), 5);
            double[] elements = Arrays.stream(strElements)
                .mapToDouble(Double::parseDouble)
                .toArray();
            dataNormalizer.analyze(elements);
            SampleNode sampleNode = new SampleNode(elements);
            data.add(sampleNode);
        }

        TOTAL_DATA_LENGTH = data.size();

        return data;
    }

    public static int getTotalDataLength() {
        return TOTAL_DATA_LENGTH;
    }
}
