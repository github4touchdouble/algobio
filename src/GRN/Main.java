package GRN;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;



import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;

public class Main {

    public static void main(String args[]) throws IOException, ArgumentParserException, InterruptedException {
        ArgumentParser parser = ArgumentParsers.newFor("GRN").build().defaultHelp(true).description("Argparser for Gene Regulatory Network and akmaxsat solver.");
        parser.addArgument("-network")
                .help("Network File")
                .required(false);
        parser.addArgument("-actives")
                .help("List of active genes in network")
                .required(false);
        parser.addArgument("-cnf")
                .help("Path to dimacs file")
                .required(false);
        parser.addArgument("-sol")
                .help("Akmaxsat solver output file")
                .required(false);
        parser.addArgument("-all")
                .help("Perform all tasks and return only active TFs")
                .action(storeTrue())
                .required(false);

        // load all args
        Namespace ns = parser.parseArgs(args);
        String pathToNetwork = ns.getString("network");
        String pathToActiveGenes = ns.getString("actives");
        String pathToDimacs = ns.getString("cnf");
        String pathToSolvedDimacs = ns.getString("sol");
        boolean all = ns.getBoolean("all");

        // exec all
        if (all) {
            String defaultSAPOutPath = "out.sap.dimacs.txt";
            String solvedSAPPath = "SOLVED.txt";
            GRN grn = new GRN(pathToNetwork, pathToActiveGenes);
            System.out.println("Writing SAP of GRN to " + defaultSAPOutPath);
            writeSAP(defaultSAPOutPath, grn.toSAP());
            execAkmaxsat(defaultSAPOutPath, "SOLVED.txt");
            grn.assignVariableValues(solvedSAPPath);
            grn.getActiveTfs();
        }
        else {
            // task 1
            if (pathToNetwork != null && pathToActiveGenes != null) {
                GRN grn = new GRN(pathToNetwork, pathToActiveGenes);

                if (pathToDimacs == null && pathToSolvedDimacs == null) {
                    System.out.println(grn.toSAP());
                }

                // task 3
                else if (pathToSolvedDimacs != null) {
                    grn.assignVariableValues(pathToSolvedDimacs);
                    grn.getActiveTfs();
                }
            }

            // task 2
            else if (pathToDimacs != null) {
                execAkmaxsat(pathToDimacs, "SOLVED.txt");
            }
        }
    }


    public static void writeSAP(String path, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(content);
        bw.close();
    }

    public static void execAkmaxsat(String pathToDimacs, String outPath) throws IOException, InterruptedException {

        String[] command =  {"./akmaxsat", pathToDimacs};
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
        Process process = pb.start();
        // Capture the output and write to SOLVED.txt
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Akmaxsat executed successfully.");
            System.out.println("Solution can be found in: " + outPath + ".");

        } else {
            System.err.println("Akmaxsat failed with exit code: " + exitCode);
        }
    }
}
