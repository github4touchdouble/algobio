package TSP;
import GRAPH.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static TSP.Approx.twoApproximationTSP;

public class Main {
    public static void main(String[] args) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("cities.250.tsv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                records.add(Arrays.asList(values));
            }
            if (records.size() == 0) {
                throw new Exception("No records found in the file");
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        EdgeCityGraph mst = new EdgeCityGraph();
        boolean seen = false;
        Vertex start = null;
        for (List<String> record : records) {
            Vertex bin = new Vertex<>(new City(record.get(1), Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3))), Integer.parseInt(record.get(0)));
            mst.add_vertex(bin);
            if (seen == false) {
                start = bin;
                seen = true;
            }
        }

        mst.compute();
        List<Edge> tspTour = twoApproximationTSP(mst, start);

        for (Edge edge : tspTour) {
            System.out.println("Edge: " + edge.from.label + " -> " + edge.to.label + " Weight: " + edge.weight);
        }



    }
}
