import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.ArrayList;
import java.util.List;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;


public class Main {
    // storing results in these ArrayLists
    // these will be written to a csv when benchmarking with python
    private static ArrayList<Long> naiveTimeStamps = new ArrayList<>();
    private static ArrayList<Long> recTimeStamps = new ArrayList<>();
    private static ArrayList<Long> dynamicTimeStamps = new ArrayList<>();
    private static ArrayList<Long> divideTimeStamps = new ArrayList<>();
    private static ArrayList<Long> optimalTimeStamps = new ArrayList<>();
    public static void main(String[] args) throws ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newFor("MSS").build().defaultHelp(true).description("Calculate maximum scoring subsequence for given input vector --v");
        parser.addArgument("--v")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("input vector");
        // adding switch for benchmarking
        parser.addArgument("--b").action(storeTrue());


        Namespace ns = parser.parseArgs(args);

        boolean benchmark = ns.getBoolean("b");

        // if not --b then calculate mss for given vector --v
        // in the else case we can adjust the input based on loops and generate data
        // more easily
        if (!benchmark) {
            // get vec
            java.util.List<Integer> vec = ns.getList("v");


            benchmarkCode("naive", vec);
            benchmarkCode("recursive", vec);
            benchmarkCode("dynamic", vec);
            // TODO: Jan
            // benchmarkCode("divide", vec);
            benchmarkCode("optimal", vec);

        }

    }

    public static void printRes(int[] res, long time) {
        System.out.println("[" + res[0] + "," + res[1] +"] mit score " + res[2]);
        System.out.println(time + " µs\n");
    }

    public static void benchmarkCode(String type, List<Integer> vec) {
        long startTime = 0;
        long endTime = 0;
        long elapsedTimeMicros = 0;

        switch (type) {
            case ("optimal"):
                startTime = System.nanoTime();
                int[] resOptimal = SMSS_Problem.optimal(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Optimal:");
                printRes(resOptimal, elapsedTimeMicros);
                optimalTimeStamps.add(elapsedTimeMicros);
                break;

            case("naive"):
                startTime = System.nanoTime();
                int[] resNai = SMSS_Problem.naive(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Naive:");
                printRes(resNai, elapsedTimeMicros);
                naiveTimeStamps.add(elapsedTimeMicros);
                break;

            case ("recursive"):
                startTime = System.nanoTime();
                int[] resRec = SMSS_Problem.rec(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Recursive:");
                printRes(resRec, elapsedTimeMicros);
                recTimeStamps.add(elapsedTimeMicros);
                break;

            case ("dynamic"):
                startTime = System.nanoTime();
                int[] resDyn = SMSS_Problem.dynamic(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Dynamic Programming:");
                printRes(resDyn, elapsedTimeMicros);
                dynamicTimeStamps.add(elapsedTimeMicros);
                break;

            case("divide"):
                startTime = System.nanoTime();
                int[] resDiv = SMSS_Problem.divide_and_conquer(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Divide and Conquer:");
                printRes(resDiv, elapsedTimeMicros);
                divideTimeStamps.add(elapsedTimeMicros);
                break;
       }
    }
}
