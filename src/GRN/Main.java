package GRN;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        GRN grn = new GRN("/home/malte/projects/AlgoBioI/src/GRN/regulatory-network.edgelist_test.tsv", "/home/malte/projects/AlgoBioI/src/GRN/active-genes_test.txt");
        // GRN grn = new GRN("/home/malte/projects/AlgoBioI/src/GRN/regulatory-network.edgelist.tsv", "/home/malte/projects/AlgoBioI/src/GRN/active-genes.txt");
        System.out.println(grn.toString());

        wirteSAP("sap.dimacs.txt", grn.toString());
    }


    public static void wirteSAP(String path, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(content);
        bw.close();
    }
}
