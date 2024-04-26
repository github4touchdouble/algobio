import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
    // storing results in these ArrayLists
    // these will be written to a csv when benchmarking with python
    static HashMap<String, ArrayList<Long>> timeStamps = new HashMap<>();
    static final ArrayList<String> DEFAULT_TYPES = new ArrayList<>();
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
        else if (algs != null) { // benchmark only algorithms in algs
            Random random = new Random(42L); // seed for reproducibility
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value

            for (int i = 0; i < 1000; i++) {
                // TODO: add if statement for slow algs and pass -1
                // TODO: Maybe increase size more radical
                vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                for(String algorithm : algs) {
                    benchmarkCode(algorithm, vec);
                }
            }
            writeCsv(path, algs);
        }
        else // benchmark all approaches in this case
        {
            Random random = new Random(42L); // seed for reproducibility
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value
            for (int i = 0; i < 1000; i++) {
                    vec.add(random.nextInt(max - min) + min); // add one rand number to vec
                // TODO: add if statement for slow algs and pass -1
                // TODO: Maybe increase size more radical
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
                timeStamps.get(type).add(elapsedTimeMicros);
                break;

            case("naive"):
                startTime = System.nanoTime();
                int[] resNai = SMSS_Problem.naive(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Naive:");
                printRes(resNai, elapsedTimeMicros);
                timeStamps.get(type).add(elapsedTimeMicros);
                break;

            case ("recursive"):
                startTime = System.nanoTime();
                int[] resRec = SMSS_Problem.rec(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Recursive:");
                printRes(resRec, elapsedTimeMicros);
                timeStamps.get(type).add(elapsedTimeMicros);
                break;

            case ("dynamic"):
                startTime = System.nanoTime();
                int[] resDyn = SMSS_Problem.dynamic(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Dynamic Programming:");
                printRes(resDyn, elapsedTimeMicros);
                timeStamps.get(type).add(elapsedTimeMicros);
                break;

            case("divide"):
                startTime = System.nanoTime();
                int[] resDiv = SMSS_Problem.divide_and_conquer(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("Divide and Conquer:");
                printRes(resDiv, elapsedTimeMicros);
                timeStamps.get(type).add(elapsedTimeMicros);
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
                timeStamps.get(type).add(elapsedTimeMicros);
       }
    }

    public static void writeCsv(String path, List<String> types) throws IOException {
        BufferedWriter buff = new BufferedWriter(new FileWriter(path));
        StringBuilder sb = new StringBuilder();

        // create column names and save size
        for(String algType : types) {
           sb.append(algType + ";");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n");

        int totalSize = timeStamps.get(types.get(0)).size(); // get size of first list (they are all the same length)

        int index = 0;
        for (int i = 0; i < totalSize; i++) {
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
