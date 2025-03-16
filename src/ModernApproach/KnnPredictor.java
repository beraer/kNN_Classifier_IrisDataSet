package ModernApproach;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KnnPredictor {
    public double euclideanDistance(double[] v1, double[] v2) {
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return  Math.sqrt(sum);
    }

    public String predict(List<Vector> trainingSet, double[] testVectorAttributes, int k){

        List<Map.Entry<Double, String>> distanceLabelPairs = trainingSet.stream()
                .map(vec -> Map.entry(euclideanDistance(testVectorAttributes, vec.attributes), vec.label))
                .collect(Collectors.toList());

        List<String> nearestLabels = distanceLabelPairs.stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .limit(k)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        Map<String, Long> labelCount = nearestLabels.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return labelCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public double calculateAccuracy(List<Vector> trainingSet, List<Vector> testSet, int k){
        int correct = 0;
        for (Vector vec : testSet){
            String label = predict(trainingSet, vec.attributes, k);
            if (label.equals(vec.label)) {
                correct++;
            }
        }
        return correct / (double) testSet.size();
    }
}
