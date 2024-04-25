import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;



public class Main {
    // storing results in these ArrayLists
    // these will be written to a csv when benchmarking with python
    static ArrayList<Long> naiveTimeStamps = new ArrayList<>();
    static ArrayList<Long> recTimeStamps = new ArrayList<>();
    static ArrayList<Long> dynamicTimeStamps = new ArrayList<>();
    static ArrayList<Long> divideTimeStamps = new ArrayList<>();
    static ArrayList<Long> optimalTimeStamps = new ArrayList<>();
    static ArrayList<Long> allTimeStamps = new ArrayList<>();
    public static void main(String[] args) throws ArgumentParserException, IOException {
        ArgumentParser parser = ArgumentParsers.newFor("MSS").build().defaultHelp(true).description("Calculate maximum scoring subsequence for given input vector --v");
        parser.addArgument("--v")
                .metavar("N")
                .type(Integer.class)
                .nargs("+")
                .help("input vector");

        // get vec
        Namespace ns = parser.parseArgs(args);
        java.util.List<Integer> vec = ns.getList("v");

        // if not null then calc all approaches for the given vec
        if (vec != null) {
            benchmarkCode("naive", vec);
            benchmarkCode("recursive", vec);
            benchmarkCode("dynamic", vec);
            // TODO: Jan
            // benchmarkCode("divide", vec);
            benchmarkCode("optimal", vec);
            benchmarkCode("all", vec);

        }
        else // benchmark all approaches in this case
        {
            Random random = new Random(42L); // seed for reproducibility
            vec = new ArrayList<>(); // reset vec

            int min = -100; // Define the minimum value
            int max = 100;  // Define the maximum value
            for (int i = 0; i < 500; i++) {
                    vec.add(random.nextInt(max - min) + min); // add one rand number to vec

                    benchmarkCode("naive", vec);
                    benchmarkCode("recursive", vec);
                    benchmarkCode("dynamic", vec);
                    // TODO: Jan
                    // benchmarkCode("divide", vec);
                    benchmarkCode("optimal", vec);
                    benchmarkCode("all", vec);
            }

            // writeCsv("time.csv");
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

            case("all"):
                startTime = System.nanoTime();
                ArrayList<int[]> resAll = SMSS_Problem.allMSS_1_3a(vec);
                endTime = System.nanoTime();
                elapsedTimeMicros = (endTime - startTime) / 1000;
                System.out.println("All MSS:");

                for(int[] res: resAll) {
                    printRes(res, elapsedTimeMicros);
                }
                allTimeStamps.add(elapsedTimeMicros);
       }
    }

    // TODO:
    public static void writeCsv(String path) throws IOException {
        BufferedWriter buff = new BufferedWriter(new FileWriter(path));
        StringBuilder sb = new StringBuilder();

        sb.append("Naive;Recursive;Dynamic;Divide;Optimal;All\n");
        for (int i = 0; i < naiveTimeStamps.size(); i++) {
            sb.append(naiveTimeStamps.get(i) +
                    ";" + recTimeStamps.get(i) +
                    ";" + dynamicTimeStamps.get(i) +
                    //";" + divideTimeStamps.get(i) +
                    ";" + optimalTimeStamps.get(i) +
                    ";" + allTimeStamps.get(i) + "\n");
        }

        String out = sb.toString();
        buff.write(out);
        buff.close();
    }

    // get memory usage
    private static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
