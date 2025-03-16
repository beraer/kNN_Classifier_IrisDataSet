package PrimitiveApproach;

import java.util.List;

public class KnnClassifier {
    public static double euclideanDistance(double[] a, double[] b){
        double sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }


    public static String classify(List<Vector> trainingSet, double[] features, int k){
        int n = trainingSet.size();
        double[] dist = new double[n];
        String[] labels = new String[n];

        for(int i = 0; i < n; i++){
            Vector vct = trainingSet.get(i);
            dist[i] = euclideanDistance(features, vct.features);
            labels[i] = vct.label;
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

    public static double calculateAccuracy(List<Vector> testSet, List<Vector> trainingSet, int k) {
        int correct = 0;
        for (Vector vct : testSet) {
            String predictedLabel = classify(trainingSet, vct.features, k);
            if (predictedLabel.equals(vct.label)) {
                correct++;
            }
        }
        return (double) correct / testSet.size();
    }
}
