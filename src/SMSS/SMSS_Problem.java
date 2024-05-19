package SMSS;
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

    public static int[] divide_and_conquer(List<Integer> sequence) {
        int[] res = new int[]{0, 0, 0};
        rec_divide_and_conquer(sequence, 0, sequence.size() - 1, res);
        return res;
    }

    private static int[] rec_divide_and_conquer(List<Integer> sequence, int i, int j, int[] res) {
        if (i == j) {
            if (sequence.get(i) > 0) {
                res[0] = i;
                res[1] = i;
                res[2] = sequence.get(i);
                return res;
            } else {
                res[0] = i;
                res[1] = i - 1;
                res[2] = 0;
                return res;
            }

        } else {
            int m = (i + j) / 2;
            int mxls = 0;
            int crls = 0;
            int mxli = m;
            for (int k = m; k >= i; k--) {
                crls += sequence.get(k);
                if (crls > mxls) {
                    mxls = crls;
                    mxli = k;
                }
            }

            int mxrs = 0;
            int crrs = 0;
            int mxri = m + 1;
            for (int k = m + 1; k <= j; k++) {
                crrs += sequence.get(k);
                if (crrs > mxrs) {
                    mxrs = crrs;
                    mxri = k;
                }
            }

            int mxs = mxls + mxrs;
            int ls = rec_divide_and_conquer(sequence, i, m, res)[2];
            int rs = rec_divide_and_conquer(sequence, m + 1, j, res)[2];
            if (ls >= rs && ls >= mxs) {
                res[0] = i;
                res[1] = m;
                res[2] = ls;
                return res;
            } else if (rs >= ls && rs >= mxs) {
                res[0] = m + 1;
                res[1] = j;
                res[2] = rs;
                return res;
            } else {
                res[0] = mxli;
                res[1] = mxri;
                res[2] = mxs;
                return res;
            }

        }
    }

    /*
    public static void main(String[] args) {
        int[] test = new int[]{-5, 2, 4, -4, 5};
        //int[] optimal = optimal(new ArrayList<>(List.of(-5, 2, 4, -4, 5)));
        int[] optimal = divide_and_conquer(new ArrayList<>(List.of(-5, 2, 4, -4, 5)));

        //int[] optimal = maxSubsequence(test);
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

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                S[i][j] = S[i][j - 1] + sequence.get(j);

                if (S[i][j] >= res[2]) {
                    res[0] = i + 1; // l
                    res[1] = j; // r
                    res[2] = S[i][j]; // update max score
                }
            }
        }

        return res;
    }

    // Aufgabe 2a
    public static ArrayList<int[]> MSS_2a(List<Integer> sequence) {
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
        for (int[] segment : scores.get(scores.size() - 1)) {
            if (segment[0] > lastL && segment[1] > lastR) {
                lastL = segment[0];
                lastR = segment[1];
                finalRes.add(segment);
            }
        }
        return finalRes;
    }

    // 2_b
    public static ArrayList<int[]> MSS_2b(List<Integer> sequence) {
        ArrayList<int[]> bin = MSS_2a(sequence);
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


    // 2_c
    public static ArrayList<int[]> MSS_2c(List<Integer> sequence) {
        ArrayList<ArrayList<int[]>> scores = new ArrayList<>();
        scores.add(new ArrayList<>()); // init first

        // init
        // [l, r, max]
        int[] res = new int[]{0, 0, sequence.get(0)};
        int n = sequence.size();

        int[][] S = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == 0 && j == 0) {
                    S[i][j] = sequence.get(j);
                } else {
                    S[i][j] = S[i][j - 1] + sequence.get(j);
                }
                if (S[i][j] == res[2]) { // append
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = S[i][j]; // update max score
                    scores.get(scores.size() - 1).add(new int[]{i, j, S[i][j]});
                } else if (S[i][j] >= res[2]) { // create new
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = S[i][j]; // update max score
                    scores.add(new ArrayList<>());
                    scores.get(scores.size() - 1).add(new int[]{i, j, S[i][j]});
                }
            }
        }
        return scores.get(scores.size() - 1);
    }

    public static ArrayList<int[]> MSS_2c_1(List<Integer> sequence) {
        ArrayList<ArrayList<int[]>> scores = new ArrayList<>();
        scores.add(new ArrayList<>()); // init first

        // init
        // [l, r, max]
        int[] res = new int[]{0, 0, sequence.get(0)};
        int n = sequence.size();

        ArrayList<int[]> Snew = new ArrayList<>();
        for (int i = sequence.size(); i > 0; i--) {
            Snew.add(new int[i]);
        }


        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == 0 && j == 0) {
                    Snew.get(i)[j] = sequence.get(j);
                } else {
                    if (j > i) {
                        Snew.get(i)[j - i] = Snew.get(i)[j - 1 - i] + sequence.get(j);
                    } else {
                        Snew.get(i)[j - i] = sequence.get(j);
                    }
                }

                if (Snew.get(i)[j - i] == res[2]) { // append
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = Snew.get(i)[j - i]; // update max score
                    scores.get(scores.size() - 1).add(new int[]{i, j, Snew.get(i)[j - i]});
                } else if (Snew.get(i)[j - i] >= res[2]) { // create new
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = Snew.get(i)[j - i]; // update max score
                    scores.add(new ArrayList<>());
                    scores.get(scores.size() - 1).add(new int[]{i, j, Snew.get(i)[j - i]});
                }
            }
        }
        return scores.get(scores.size() - 1);
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
