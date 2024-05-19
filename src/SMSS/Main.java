package SMSS;

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
        // get vals to list
        java.util.List<Integer> vec = ns.getList("v");

        SMSS_Problem.Optimal(vec);
    }
}
