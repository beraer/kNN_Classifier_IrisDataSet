package ModernApproach;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3){
            System.out.println("Usage: java ModernApproach/KnnPredictor <k> <../data/train-set> <../data/test-set>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        String trainFile = args[1];
        String testFile = args[2];

        List<Vector> trainingData = LoadCsvFile.loadFile(trainFile);
        List<Vector> testingData = LoadCsvFile.loadFile(testFile);

        double accuracy = KnnPredictor.calculateAccuracy(trainingData, testingData, k);
        System.out.println("Accuracy: " + accuracy);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a vector to classify:");
        while (true) {
            String line = sc.nextLine().trim();
            if(line.equals("quit")){break;}
            String[] tokens = line.split(",");
            double[] attributes = new double[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                attributes[i] = Double.parseDouble(tokens[i]);
            }

            String predictedLabel = KnnPredictor.predict(trainingData, attributes, k);
            System.out.println("Predicted label: " + predictedLabel);
            System.out.println("Enter another vector to classify (or 'quit' to exit):");
        }
        sc.close();
    }
}
