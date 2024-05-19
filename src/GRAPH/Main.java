package GRAPH;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
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
            switch (task) {

                case 1:
                    String path = ns.getString("path");
                    if (path == null) {
                        throw new ArgumentParserException("--path is required for task 1", parser);
                    }
                    task1(path);
                    break;

                default:
                    System.out.println("Invalid task number");
                    break;
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }

    }

    public static void task1(String tsv_path) {
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
        Graph g = new CityGraph();
        for (List<String> record : records) {
            System.out.println(record);
            g.add_vertex(new Vertex<>(new City(record.get(1), Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3))), Integer.parseInt(record.get(2))));
        }
        g.compute_edges();
        System.out.println(g);
    }
}
