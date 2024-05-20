package GRAPH;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ArgumentParser parser = ArgumentParsers.newFor("GRAPH").build().defaultHelp(true).description("Solve given graph problem --task");
        parser.addArgument("--task")
                .metavar("N")
                .type(Integer.class)
                .help("Task number to solve")
                .required(true);
        parser.addArgument("--path")
                .help("specify csv path")
                .required(false);

        try {
            Namespace ns = parser.parseArgs(args);
            int task = ns.getInt("task");
            String path = ns.getString("path");

            switch (task) {

                case 1:
                    if (path == null) {
                        throw new ArgumentParserException("--path is required for task 1", parser);
                    }
                    task1(path);
                    break;

                case 2:
                    if (path == null) {
                        throw new ArgumentParserException("--path is required for task 2", parser);
                    }

                    task2(path);
                    break;

               case 3:
                    if (path == null) {
                        throw new ArgumentParserException("--path is required for task 2", parser);
                    }

                    task3(path);
                    break;

                default:
                    System.out.println("Invalid task number");
                    break;
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }

    }

    public static Graph task1(String tsv_path) {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(tsv_path))) {
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
        Graph g = new CityGraph(false, false);
        long add_vertex_time = 0;
        for (List<String> record : records) {
            long s = System.currentTimeMillis();
            g.add_vertex(new Vertex<>(new City(record.get(1), Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3))), Integer.parseInt(record.get(0))));
            long e = System.currentTimeMillis();
            add_vertex_time += e - s;
        }
        long s = System.currentTimeMillis();
        g.compute_edges();
        long e = System.currentTimeMillis();

        System.out.println("Added " + g.get_vertex_count() + " cities into the City Graph in " + add_vertex_time + "ms");
        System.out.println("Generated " + g.get_edge_count() + " City Graph edges in " + (e - s) + "ms");

        return g;
    }

    public static void task2(String tsv_path) throws IOException {
        Graph g = task1(tsv_path);  // init City Graph

        // compute start id O(n) and map ids to vertices
        // this is only to get the start vertex correct
        int minID = Integer.MAX_VALUE;
        HashMap<Integer, Vertex> idToVertex = new HashMap<>();
        for(Vertex v : g.adj_list.keySet()) {
            if (v.label < minID) {
                minID = v.label;
            }
            idToVertex.put(v.label, v);
        }

        Vertex startVertex = idToVertex.get(minID);
        StringBuilder sb = new StringBuilder();
        int[] stepCounter = {0}; // due to recursion we pass an int array along, this can also be used for cycle detection
        sb.append("ID (von)\tID (nach)\tDistanz\n");
        long s = System.currentTimeMillis();
        g.depthFirstSearch(startVertex, sb, stepCounter);
        long e = System.currentTimeMillis();
        long dfs = e - s;
        System.out.println("traversed graph in " + stepCounter[0] + " in " + dfs + " ms");

        writeCsv("cities.250.bfs.tsv", sb.toString());
    }

    public static void task3(String tsv_path) throws IOException {
        Graph g = task1(tsv_path);  // init City Graph
        Set<Edge> mstEdges = KruskalAlgorithm.kruskal(g);
        KruskalAlgorithm.writeMSTToFile(mstEdges, "cities.250.mst.edgelist");
    }

    public static void writeCsv(String path, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(content);
        bw.close();
    }
}
