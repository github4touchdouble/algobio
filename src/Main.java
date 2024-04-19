import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


public class Main {
    public static void main(String[] args) throws ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newFor("MSS").build().defaultHelp(true).description("Predict Secondary Structure"); parser.addArgument("--v")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("input vector");

        Namespace ns = parser.parseArgs(args);

        // get vec
        java.util.List<Integer> vec = ns.getList("v");

        int[] resOptimal = SMSS_Problem.optimal(vec);
        printRes(resOptimal);

        int[] resNaive = SMSS_Problem.naive(vec);
        printRes(resOptimal);

        int[] resRec = SMSS_Problem.rec(vec);
        printRes(resRec);

        int[] resDynamic = SMSS_Problem.dynamic(vec);
        printRes(resDynamic);
    }

    public static void printRes(int[] res) {
        System.out.println("[" + res[0] + "," + res[1] +"] mit score " + res[2]);
    }
}
