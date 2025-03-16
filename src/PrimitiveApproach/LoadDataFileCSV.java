package PrimitiveApproach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFileCSV {
    public static List<Vector> loadDataFile(String filename) throws IOException {
        List<Vector> Vectors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            double[] features = new double[tokens.length - 1];
            for(int i = 0; i < features.length; i++){
                features[i] = Double.parseDouble(tokens[i]);
            }
            String label = tokens[tokens.length - 1];
            Vectors.add(new Vector(features, label));
        }
        return Vectors;
    }
}
