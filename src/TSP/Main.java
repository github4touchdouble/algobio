package TSP;
import GRAPH.City;
import GRAPH.CityGraph;
import GRAPH.Graph;
import GRAPH.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Graph g = new CityGraph(false,false);
        for (List<String> record : records) {
            g.add_vertex(new Vertex<>(new City(record.get(1), Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3))), Integer.parseInt(record.get(0))));
        }
        g.compute_edges();

        records.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("cities.250.mst.edgelist"))) {
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
        Graph m = new Graph();
        for (List<String> record : records) {
            m.add_vertex(new Vertex<>(Integer.parseInt(record.get(0)), Integer.parseInt(record.get(0)));
            System.out.println("Edge from " + record.get(0) + " to " + record.get(1) + " with weight " + record.get(2));
        }



    }
}
