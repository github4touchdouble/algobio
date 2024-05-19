package GRAPH;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

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
                    task1();
                    break;

                default:
                    System.out.println("Invalid task number");
                    break;
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }

    }
    public static void task1() {
        Graph g = new CityGraph();
        g.add_vertex(new Vertex<>(new City("Goslar (Niedersachsen)", 51.54, 10.26), 0));
        g.add_vertex(new Vertex<>(new City("Dettelbach (Bayern)", 49.48, 10.11), 1));
        g.add_vertex(new Vertex<>(new City("Hiroshima (Japan)", 34.23, 132.27), 2));
        g.compute_edges();

        System.out.println(g);
    }
}
