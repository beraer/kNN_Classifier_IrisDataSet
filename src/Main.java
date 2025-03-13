import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {

    }
}