package PrimitiveApproach;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: java PrimitiveApproach/KnnClassifier <k> <../data/train-set> <../data/test-set>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        String trainFile = args[1];
        String testFile = args[2];


        List<Vector> trainingSet = LoadDataFileCSV.loadDataFile(trainFile);
        List<Vector> testSet = LoadDataFileCSV.loadDataFile(testFile);

        double accuracy = KnnClassifier.calculateAccuracy(trainingSet, testSet, k);
        System.out.println("Accuracy: " + accuracy);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a vector to classify (comma-separated values) or 'quit' to exit:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) break;
            String[] parts = input.split(",");
            double[] features = new double[parts.length];
            try {
                for (int i = 0; i < parts.length; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter comma-separated numeric values.");
                continue;
            }
            String label = KnnClassifier.classify(trainingSet, features, k);
            System.out.println("Classified as: " + label);
            System.out.println("Enter another vector to classify (or 'quit' to exit):");
        }
        scanner.close();
    }
}