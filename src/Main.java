import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;


public class Main {
    // storing results in these ArrayLists
    // these will be written to a csv when benchmarking with python
    static HashMap<String, ArrayList<Long>> timeStamps = new HashMap<>();
    static ArrayList<Integer> inSize = new ArrayList<>();
    static final ArrayList<String> DEFAULT_TYPES = new ArrayList<>();
    static boolean seconds;
    static int stepSize;
    public static void main(String[] args) throws ArgumentParserException, IOException {
        ArgumentParser parser = ArgumentParsers.newFor("MSS").build().defaultHelp(true).description("Calculate maximum scoring subsequence for given input vector --v");
        parser.addArgument("--v")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("input vector");
        parser.addArgument("--a")
                .metavar("N")
                .type(String.class)
                .nargs("+")
                .help("algorithm type");
        parser.addArgument("--p").
                setDefault("times.csv").
                help("specify csv path");
        parser.addArgument("--f").
                type(Integer.class).
                setDefault("1").
                help("when benchmarking, increase n with this constant for each iteration");
        parser.addArgument("--s").
                action(storeTrue()).
                help("convert micro seconds to seconds");

        DEFAULT_TYPES.add("naive");
        DEFAULT_TYPES.add("recursive");
        DEFAULT_TYPES.add("dynamic");
        DEFAULT_TYPES.add("divide");
        DEFAULT_TYPES.add("optimal");
        DEFAULT_TYPES.add("smss");

        // get vec
        Namespace ns = parser.parseArgs(args);
        java.util.List<Integer> vec = ns.getList("v");
        java.util.List<String> algs = ns.getList("a");
        String path = ns.get("p");
        seconds = ns.getBoolean("s"); // set global
        stepSize = ns.get("f");

        // init all HashMaps for storing time stamps
        timeStamps.put("naive", new ArrayList<>());
        timeStamps.put("recursive", new ArrayList<>());
        timeStamps.put("divide", new ArrayList<>());
        timeStamps.put("dynamic", new ArrayList<>());
        timeStamps.put("optimal", new ArrayList<>());
        timeStamps.put("smss", new ArrayList<>());


        // if not null then calc all approaches for the given vec
        if (vec != null) {
            if (algs != null) { // if specific algs are given, only calculate for these algs
                for(String algorithm : algs) {
                    benchmarkCode(algorithm, vec);
                }
            }
            else { // run all algorithms
                for(String algorithm : DEFAULT_TYPES) {
                    benchmarkCode(algorithm, vec);
                }
            }
        }
        // benchmarking case
        else if (algs != null) { // benchmark only algorithms in algs
            Random random = new Random(42L); // seed for reproducibility
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value

            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < stepSize; j++) { // increase n based on global stepSize
                    vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                }

                inSize.add(vec.size()); // add size of vec

                for(String algorithm : algs) {
                    benchmarkCode(algorithm, vec);
                }
            }
            writeCsv(path, algs);
        }
        else // benchmark all approaches in this case
        {
            Random random = new Random(42L); // seed for reproducibility vec = new ArrayList<>(); // reset vec
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value
            for (int i = 0; i < 500; i++) {

                for (int j = 0; j < stepSize; j++) {
                    vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                }

                inSize.add(vec.size()); // add size of vec

                for(String algorithm : DEFAULT_TYPES) {
                    benchmarkCode(algorithm, vec);
                }
            }
            writeCsv(path, DEFAULT_TYPES);
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
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
                break;

            case("naive"):
                startTime = System.nanoTime();
                int[] resNai = SMSS_Problem.naive(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Naive:");
                printRes(resNai, elapsedTimeMicros);
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
                break;

            case ("recursive"):
                startTime = System.nanoTime();
                int[] resRec = SMSS_Problem.rec(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Recursive:");
                printRes(resRec, elapsedTimeMicros);
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
                break;

            case ("dynamic"):
                startTime = System.nanoTime();
                int[] resDyn = SMSS_Problem.dynamic(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Dynamic Programming:");
                printRes(resDyn, elapsedTimeMicros);
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
                break;

            case("divide"):
                startTime = System.nanoTime();
                int[] resDiv = SMSS_Problem.divide_and_conquer(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Divide and Conquer:");
                printRes(resDiv, elapsedTimeMicros);
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
                break;

            case("smss"):
                startTime = System.nanoTime();
                ArrayList<int[]> resAll = SMSS_Problem.allMSS_1_3b(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("SMSS:");

                for(int[] res: resAll) {
                    printRes(res, elapsedTimeMicros);
                }
                if (seconds) {
                    timeStamps.get(type).add((long) elapsedTimeMicros / 1000000L); // append time
                } else {
                    timeStamps.get(type).add(elapsedTimeMicros); // append time
                }
       }
    }

    public static void writeCsv(String path, List<String> types) throws IOException {
        BufferedWriter buff = new BufferedWriter(new FileWriter(path));
        StringBuilder sb = new StringBuilder();

        // create column names and save size
        sb.append("n;"); // input size
        for(String algType : types) {
           sb.append(algType + ";");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");

        int totalSize = timeStamps.get(types.get(0)).size(); // get size of first list (they are all the same length)

        int index = 0;
        for (int i = 0; i < totalSize; i++) {
            sb.append(inSize.get(index) + ";"); // append n
            // append time vals for given n
            for (String algType : types) {
                sb.append(timeStamps.get(algType).get(index) + ";");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n");
            index++;
        }

        String out = sb.toString();
        buff.write(out);
        buff.close();
    }
}
