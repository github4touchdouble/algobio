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
    static Integer stepSize;
    static Integer size;
    public static void main(String[] args) throws ArgumentParserException, IOException {
        ArgumentParser parser = ArgumentParsers.newFor("MSS").build().defaultHelp(true).description("Calculate maximum scoring subsequence for given input vector --v");
        parser.addArgument("--vec")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("input vector");
        parser.addArgument("--algorithms")
                .metavar("N")
                .type(String.class)
                .nargs("+")
                .help("algorithm type");
        parser.addArgument("--path").
                setDefault("times").
                help("specify csv path");
        parser.addArgument("--size").
                type(Integer.class).
                setDefault(0).
                help("run given algorithms on a fixed size input (good for testing)");
        parser.addArgument("--step").
                type(Integer.class).
                setDefault(1).
                help("when benchmarking, increase n with this constant for each iteration");

        DEFAULT_TYPES.add("naive");
        DEFAULT_TYPES.add("recursive");
        DEFAULT_TYPES.add("dynamic");
        DEFAULT_TYPES.add("divide");
        DEFAULT_TYPES.add("optimal");
        DEFAULT_TYPES.add("2_a");
        DEFAULT_TYPES.add("2_b");
        DEFAULT_TYPES.add("2_c");
        DEFAULT_TYPES.add("2_c_1");

        // get vec
        Namespace ns = parser.parseArgs(args);
        java.util.List<Integer> vec = ns.getList("vec"); // input vec
        java.util.List<String> algorithms = ns.getList("algorithms"); // algorithm (alg) types
        String path = ns.get("path"); // out path, default = times.csv
        stepSize = ns.getInt("step"); // set global
        size = ns.getInt("size"); // set global

        // init all HashMaps for storing time stamps
        for (String type : DEFAULT_TYPES) {
            timeStamps.put(type, new ArrayList<>());
        }

        // if not null then calc all approaches for the given vec
        if (vec != null) {
            int n = vec.size();
            if (algorithms != null) { // if specific algorithms are given, only exec these
                for(String algorithm : algorithms) {
                    benchmarkCode(algorithm, vec);
                }
            }
            else { // run all algorithms if none are given
                for(String algorithm : DEFAULT_TYPES) {
                    benchmarkCode(algorithm, vec);
                }
            }
        }
        // benchmarking case
        else if (algorithms != null) { // for specific algorithms
            Random random = new Random(42L); // seed for reproducibility
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value


                for (int i = 0; i < size; i++) {
                    vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                }

                for (int i = 0; i < 200; i++) {

                    for (int j = 0; j < stepSize; j++) { // increase n based on global stepSize
                        vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                    }

                    inSize.add(vec.size()); // add size of vec

                    for(String algorithm : algorithms) {
                        benchmarkCode(algorithm, vec);
                    }
                }

                // for (int i = 0; i < size; i++) {
                //     vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                // }
                // inSize.add(vec.size()); // add size of vec

                // for(String algorithm : algorithms) {
                //     benchmarkCode(algorithm, vec);
                // }

                writeCsv(path, algorithms);
        }

        else // benchmark all approaches in this case
        {
            Random random = new Random(42L); // seed for reproducibility vec = new ArrayList<>(); // reset vec
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value
            for (int i = 0; i < 5; i++) {

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

    public static void printRes(int[] res, long time, int n) {
        System.out.println("// \t[" + res[0] + "," + res[1] +"] mit score " + res[2]);
        System.out.println("// \t" + time + " µs");
        System.out.println("// \tfor input size " + n);
    }
    public static void printRes(ArrayList<int[]> results, long time, int n) {
        for (int[] res : results) {
            System.out.println("// \t[" + res[0] + "," + res[1] +"] mit score " + res[2]);
        }
        System.out.println("// \t" + time + " µs");
        System.out.println("// \tfor input size " + n);
    }

    public static void benchmarkCode(String type, List<Integer> vec) {
        long startTime = 0;
        long endTime = 0;
        long elapsedTimeMicros = 0;
        ArrayList<int[]> resAll;
        int n = vec.size();

        switch (type) {
            case ("optimal"):
                startTime = System.nanoTime();
                int[] resOptimal = SMSS_Problem.optimal(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// Optimal:");
                printRes(resOptimal, elapsedTimeMicros, n);

                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case("naive"):
                startTime = System.nanoTime();
                int[] resNai = SMSS_Problem.naive(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// Naive:");
                System.out.println("//");
                printRes(resNai, elapsedTimeMicros, n);

                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case ("recursive"):
                startTime = System.nanoTime();
                int[] resRec = SMSS_Problem.rec(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// Recursive:");
                System.out.println("//");
                printRes(resRec, elapsedTimeMicros, n);

                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case ("dynamic"):
                startTime = System.nanoTime();
                int[] resDyn = SMSS_Problem.dynamic(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// Dynamic Programming:");
                System.out.println("//");
                printRes(resDyn, elapsedTimeMicros, n);
                timeStamps.get(type).add(elapsedTimeMicros); // append time

                break;

            case("divide"):
                startTime = System.nanoTime();
                int[] resDiv = SMSS_Problem.divide_and_conquer(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// Divide and Conquer:");
                System.out.println("//");
                printRes(resDiv, elapsedTimeMicros, n);

                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case("2_a"):
                startTime = System.nanoTime();
                resAll = SMSS_Problem.MSS_2a(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// 2_a (MSS):");
                System.out.println("//");
                printRes(resAll, elapsedTimeMicros, n);
                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case("2_b"):
                startTime = System.nanoTime();
                resAll = SMSS_Problem.MSS_2b(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// 2_b (SMSS):");
                System.out.println("//");
                printRes(resAll, elapsedTimeMicros, n);

                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

            case("2_c"):
                startTime = System.nanoTime();
                resAll = SMSS_Problem.MSS_2c(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// 2_c (All SMSS):");
                System.out.println("//");
                printRes(resAll, elapsedTimeMicros, n);
                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;

           case("2_c_1"):
                startTime = System.nanoTime();
                resAll = SMSS_Problem.MSS_2c_1(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("// 2_c_1 (All SMSS & Optimized space usage):");
                System.out.println("//");
                printRes(resAll, elapsedTimeMicros, n);
                timeStamps.get(type).add(elapsedTimeMicros); // append time
                break;
       }
       System.out.println();
    }

    public static void writeCsv(String path, List<String> types) throws IOException {
        BufferedWriter buff = new BufferedWriter(new FileWriter(path + ".csv"));
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
