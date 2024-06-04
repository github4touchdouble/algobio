package GRN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GRN {

    private HashMap<String, ArrayList<String>> tfToGenes = new HashMap<>();
    private HashMap<String, ArrayList<Variable>> geneToTfs = new HashMap<>();
    private HashMap<String, Integer> tfToId = new HashMap<>();

    public GRN (String pathToEdgeList, String pathToActiveGenes) throws IOException {
        initNetwork(pathToEdgeList, pathToActiveGenes);
    }

    public void initNetwork(String pathToEdgeList, String pathToActiveGenes) throws IOException {

        String line;

        // init clauses (by default set to false
        BufferedReader br = new BufferedReader(new FileReader(pathToEdgeList));

        int tfId = 1;
        while ((line = br.readLine()) != null) {
            if (line.charAt(0) != '#') {
                String[] values = line.split("\t");
                String tf = values[0];
                String gene = values[1];

                if (!geneToTfs.containsKey(gene)) {
                    geneToTfs.put(gene, new ArrayList<>());
                    geneToTfs.get(gene).add(new Variable(tf, true));
                } else {
                    geneToTfs.get(gene).add(new Variable(tf, true));
                }

                if(!tfToGenes.containsKey(tf)) {
                    tfToGenes.put(tf, new ArrayList<>());
                    tfToGenes.get(tf).add(gene);
                } else {
                    tfToGenes.get(tf).add(gene);
                }

                if(!tfToId.containsKey(tf)) {
                    tfToId.put(tf, tfId);
                    tfId++;
                }

            }
        }
        br.close();

        // adjust active genes in network
        br = new BufferedReader(new FileReader(pathToActiveGenes));
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\t");
            String activeGene = values[0];

            ArrayList<Variable> tfsOfGene = geneToTfs.get(activeGene);
            if (tfsOfGene != null) {
                for(Variable tf : tfsOfGene) {
                    tf.setNegated(false);
                }
            }
        }
        br.close();
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       // format header
       sb.append("p cnf ").append(this.geneToTfs.size()).append(" ").append(tfToGenes.size()).append("\n");

       for (String gene: geneToTfs.keySet()) {
           for (Variable tf : geneToTfs.get(gene)) {
               String neg = tf.isNegated() ? "-" : "";
               sb.append(neg).append(tfToId.get(tf.getName())).append(" ");
           }
           sb.append("0\n");
       }

       return sb.toString();
    }
}
