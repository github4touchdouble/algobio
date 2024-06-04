package GRN;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GRN {

    private final HashMap<String, ArrayList<String>> tfToGenes = new HashMap<>(); // this maps all tfs to all genes they influence,
                                                                                  // from this we can create the clauses
    private final HashMap<String, ArrayList<Variable>> geneToTfs = new HashMap<>(); // this maps genes to all the tfs they are influenced by
    private final HashMap<String, Integer> tfToId = new HashMap<>(); // we use this the give each variable (tf) to a fix id
    private final HashMap<String, String> idToTf = new HashMap<>(); // we also need this to map back to the tf names from the sap solver output
    private final HashMap<String, Boolean> tfStates = new HashMap<>(); // here we will store the actual values for each tf

    public GRN (String pathToEdgeList, String pathToActiveGenes) throws IOException {
        initNetwork(pathToEdgeList, pathToActiveGenes);
    }

    public void initNetwork(String pathToEdgeList, String pathToActiveGenes) throws IOException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(pathToEdgeList));

        int tfId = 1; // this it the uniq id for each TF
        // go through network file and fill up all HashMaps
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
                    idToTf.put(String.valueOf(tfId), tf);
                    tfId++;
                }

                // init all vars with false for now
                if(!tfStates.containsKey(tf)) {
                    tfStates.put(tf, false);
                }
            }
        }
        br.close();

        // adjust active genes in network
        br = new BufferedReader(new FileReader(pathToActiveGenes));
        String activeGene;
        while ((activeGene = br.readLine()) != null) {

            // go through all tfs (variables) of gene x (clause) and dont them (they are negated by default)
            ArrayList<Variable> tfsOfGene = geneToTfs.get(activeGene);
            if (tfsOfGene != null) {
                for(Variable tf : tfsOfGene) {
                    tf.setNegated(false);
                }
            }
        }
        br.close();
    }

    public String toSAP() {
       StringBuilder sb = new StringBuilder();
       // format header
       // tfToGenes.size() == Variables, since our tfs are variables
       // geneToTfs.size() == Clauses, since our genes represent clauses
       sb.append("p cnf ").append(this.tfToGenes.size()).append(" ").append(geneToTfs.size()).append("\n");

       for (String gene: geneToTfs.keySet()) {
           for (Variable tf : geneToTfs.get(gene)) {
               String neg = tf.isNegated() ? "-" : ""; // get prefix of variable (- if negated, '' if not)
               sb.append(neg).append(tfToId.get(tf.getName())).append(" ");
           }
           sb.append("0\n"); // end line wth 0
       }
       return sb.toString();
    }

    public void assignVariableValues(String pathToSolved) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathToSolved));

        String line;

        // read the last line of akmaxsap output and update values in global hasmap
        // we need to map the id of the variable -> nameOfTf -> value
        while((line = br.readLine()) != null){
            if (line.startsWith("v")) {
                String[] variableAssignments = line.split(" ");
                for(String var: variableAssignments) {
                    if (!var.equals("v")) {
                        String key = var.replace("-",""); // remove - for key
                        if(!var.startsWith("-")){
                            // update value to true
                            tfStates.replace(idToTf.get(key), false, true);
                        }
                    }
                }
            }
        }
    }

    public void getActiveTfs(){
        // print active tfs
        int active = 0;
        for(String tf: tfStates.keySet()) {
            if (tfStates.get(tf)) {
                System.out.println(tf);
                active++;
            }
        }
        System.out.println(active + " Active TFs in GRN");
    }
}
