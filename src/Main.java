import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static class DataPoint{
        double[] features;
        String label;

        public DataPoint(double[] features, String label){
            this.features = features;
            this.label = label;
        }
    }

    private static List<DataPoint> loadDataFile(String filename) throws IOException {
        List<DataPoint> dataPoints = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            double[] features = new double[tokens.length - 1];
            for(int i = 0; i < features.length; i++){
                features[i] = Double.parseDouble(tokens[i]);
            }
            String label = tokens[tokens.length - 1];
            dataPoints.add(new DataPoint(features, label));
        }
        return dataPoints;
    }

    private static double euclideanDistance(double[] a, double[] b){
        int sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }


    private static String classify(List<DataPoint> trainingSet, double[] features, int k){
        int n = trainingSet.size();
        double[] dist = new double[n];
        String[] labels = new String[n];

        for(int i = 0; i < n; i++){
            DataPoint dp = trainingSet.get(i);
            dist[i] = euclideanDistance(features, dp.features);
            labels[i] = dp.label;
        }

        for(int i = 0; i < k; i++){
            int minIndex = i;
            for(int j = i + 1; j < n; j++){
                if(dist[j] < dist[minIndex]){
                    minIndex = j;
                }
            }
            double tempDist = dist[i];
            dist[i] = dist[minIndex];
            dist[minIndex] = tempDist;

            String tempLabel = labels[i];
            labels[i] = labels[minIndex];
            labels[minIndex] = tempLabel;
        }

        String[] uniqueLabels = new String[k];
        int[] votes = new int[k];
        int uniqueCount = 0;

        for (int i = 0; i < k; i++) {
            String currentLabel = labels[i];
            boolean found = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueLabels[j].equals(currentLabel)) {
                    votes[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                uniqueLabels[uniqueCount] = currentLabel;
                votes[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        int maxVotes = 0;
        String predictedLabel = "";
        for (int i = 0; i < uniqueCount; i++) {
            if (votes[i] > maxVotes) {
                maxVotes = votes[i];
                predictedLabel = uniqueLabels[i];
            }
        }

        return predictedLabel;
    }

    private static double calculateAccuracy(List<DataPoint> testSet, List<DataPoint> trainingSet, int k) {
        int correct = 0;
        for (DataPoint dp : testSet) {
            String predictedLabel = classify(trainingSet, dp.features, k);
            if (predictedLabel.equals(dp.label)) {
                correct++;
            }
        }
        return (double) correct / testSet.size();
    }

    
}