package ModernApproach;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadCsvFile {
    public static List<Vector> loadFile(String fileName) throws IOException {
        List<Vector> data = new ArrayList<>();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            double[] attributes = new double[tokens.length-1];
            for(int i = 0; i < tokens.length-1; i++) {
                attributes[i] = Double.parseDouble(tokens[i]);
            }
            data.add(new Vector(tokens[tokens.length-1], attributes));
        }
        return data;
    }
}
