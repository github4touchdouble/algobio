import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SMSS_Problem {
    // run config 1 args: --v -5 2 4 -4 5
    // run config 2 args: --v 5 -2 5 -2 1 -9 12 -2 24 -5 13 -12 3 -13 5
    // run test case slides: --v 5 -2 5 -2 1 -9 5 -2 4 -5 1 -2 3 -1 5 -3 2 -1 2

    public static int[] optimal(List<Integer> sequence) {
        int max = 0;
        int l = 1;
        int r = 0;
        int rmax = 0;
        int rstart = 1;
        int n = sequence.size();
        for (int i = 1; i < n; i++) {
            if (rmax > 0) {
                rmax = rmax + sequence.get(i);
            } else {
                rmax = sequence.get(i);
                rstart = i;
            }
            if (rmax > max) {
                max = rmax;
                l = rstart;
                r = i;
            }
        }
        return new int[]{l, r, max};
    }

    //todo: bugfix
    public static int[] divide_and_conquer(List<Integer> sequence) {
        return rec_divide_and_conquer(sequence, 0, sequence.size() - 1);
    }

    private static int[] rec_divide_and_conquer(List<Integer> sequence, int i, int j) {
        if (i == j) {
            if (sequence.get(i) > 0) {
                return new int[]{i, i, sequence.get(i)};
            } else {
                return new int[]{i, i - 1, 0};
            }
        } else {
            int m = (i + j - 1) / 2;
            int[] t1 = rec_divide_and_conquer(sequence, i, m);
            int[] t2 = rec_divide_and_conquer(sequence, m + 1, j);
            int[] t3 = new int[]{0, 0, 0};
            for (int k = i; k <= m; k++) {
                int sigma = sigRec(k, m, sequence);
                if (sigma > t3[0]) {
                    t3[0] = sigma;
                }
            }
            for (int k = m + 1; k <= j; k++) {
                int sigma = sigRec(m + 1, k, sequence);
                if (sigma < t3[1]) {
                    t3[1] = sigma;
                }
            }
            t3[2] = sig(t3[0], t3[1], sequence);
            System.out.println(t3[0] + " " + t3[1] + " " + t3[2]);

            if (t1[2] > t2[2] && t1[2] > t3[2]) {
                return t1;
            } else if (t2[2] > t1[2] && t2[2] > t3[2]) {
                return t2;
            } else {
                return t3;
            }
        }
    }
    /*
    public static void main(String[] args) {
        int[] test = new int[]{-5, 2, 4, -4, 5};
        //int[] optimal = optimal(new ArrayList<>(List.of(-5, 2, 4, -4, 5)));
        int[] optimal = divide_and_conquer(new ArrayList<>(List.of(-5, 2, 4, -4, 5)));
        System.out.println("Optimal: [" + optimal[0] + "," + optimal[1] + "] mit score " + optimal[2]);
    }
    */



    public static int[] naive(List<Integer> sequence) {
        // init
        // [l, r, max]
        int[] res = new int[]{1, 0, 0};
        int n = sequence.size();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                int s = sig(i, j, sequence);

                if (s >= res[2]) {
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = s; // update max score
                }
            }
        }
        return res;
    }

    public static int[] rec(List<Integer> sequence) {
        // init
        // [l, r, max]
        int[] res = new int[]{1, 0, 0};
        int n = sequence.size();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int s = sigRec(i, j, sequence);

                if (s >= res[2]) {
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = s; // update max score
                }
            }
        }

        return res;
    }

    public static int[] dynamic(List<Integer> sequence) {
        // init
        // [l, r, max]
        int[] res = new int[]{1, 0, 0};
        int n = sequence.size();

        int[][] S = new int[n][n];

        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                S[i][j] = S[i][j - 1] + sequence.get(j);

                if (S[i][j] >= res[2]) {
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = S[i][j]; // update max score
                }
            }
        }

        return res;
    }

    // Aufgabe 3a
    public static ArrayList<int[]> allMSS_1_3a(List<Integer> sequence) {
        ArrayList<ArrayList<int[]>> scores = new ArrayList<>();
        scores.add(new ArrayList<>()); // init first sub list
        int max = 0;
        int l = 1;
        int r = 0;
        int rmax = 0;
        int rstart = 1;

        int n = sequence.size();

        for (int i = 0; i < n; i++) {
            if (rmax > 0) {
                rmax = rmax + sequence.get(i);
            } else {
                rmax = sequence.get(i);
                rstart = i;
            }
            if (rmax > max) { // open new list of new max score
                max = rmax;
                l = rstart;
                r = i;
                scores.add(new ArrayList<>());
                scores.get(scores.size() - 1).add(new int[]{l, r, max});
            } else if (rmax == max) { // append to current max score
                scores.get(scores.size() - 1).add(new int[]{rstart, i, max});
            }
        }


        // deal with overlapping sequences
        int lastL = Integer.MIN_VALUE;
        int lastR = Integer.MIN_VALUE;
        ArrayList<int[]> finalRes = new ArrayList<>();

        // O(n), go through all segments of mss list
        for(int[] segment: scores.get(scores.size() - 1)) {
            if (segment[0] > lastL && segment[1] > lastR) {
                lastL = segment[0];
                lastR = segment[1];
                finalRes.add(segment);
            }
        }

        // We assume the following:
        // Segments get added in a certain order:
        // this constellation is not possible, because seg 2 would've been found before seg 1
        // 1: ---+=====+---------:
        // 2: ---+===+-----------:

        // this is the above but in reversed order,
        // in this case, seg one gets added to final res
        // but seg 2 wont be added since segment[0] (leftIndex) == lastL, thus the if condition is not met
        // these two cases are the only case we need to account for:
        // CASE 1
        // 1: ---+===+-----------:
        // 2: ---+=====+---------:

        // CASE 2
        // here the if condition above is also not met since seg 2 end index == lastR
        // 1: -----+===+---------:
        // 2: ---+=====+---------:

        // Overlaps like this are not possible
        // overlapping segments always share either their starting index
        // or end index
        // 1: -----+=+-----------:
        // 2: ---+=====+---------:

        // 1: ---+=====+---------:
        // 2: -----+=+-----------:

        return finalRes;
    }

    public static ArrayList<int[]> allMSS_1_3b(List<Integer> sequence) {
        ArrayList<int[]> bin = allMSS_1_3a(sequence);
        int min_length = 0;
        HashMap<Integer, ArrayList<int[]>> mss = new HashMap<>();
        for (int[] s : bin) {
            int length = s[1] - s[0] + 1;
            if (length < min_length || min_length == 0) {
                min_length = length;
            }
            if (mss.containsKey(length)) {
                mss.get(length).add(s);
            } else {
                mss.put(length, new ArrayList<>());
                mss.get(length).add(s);
            }
        }
        return mss.get(min_length);
    }

    public static int sig(int i, int j, List<Integer> sequence) {
        int sequenceScore = 0;
        for (int k = i; k <= j; k++) {
            sequenceScore += sequence.get(k);
        }
        return sequenceScore;
    }


    public static int sigRec(int i, int j, List<Integer> sequence) {
        if (i > j) {
            return 0;
        } else if (i == j) {
            return sequence.get(i);
        } else {
            for (int k = i; k < j; k++) {
                return sigRec(i, k, sequence) + sigRec(k + 1, j, sequence);
            }
        }
        return 0; // this should never be reached
    }
}
