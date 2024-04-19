import java.util.List;

public class SMSS_Problem {

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

    public static int[] divide_and_conquer (List<Integer> sequence) {
        return new int[]{0, 0, 0};
    }

    public static int[] naive(List<Integer> sequence) {
        // init
        // [l, r, max]
        int[] res = new int[]{1,0,0};
        int n = sequence.size();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                int s = sig(0, j, sequence);

                if (s >= res[2]) {
                    res[0] = i; // l
                    res[1] = j; // r
                    res[2] = s; // update max score
                }
            }
        }
        return  res;
    }

    public static int[] rec(List<Integer> sequence) {
        // init
        // [l, r, max]
        int[] res = new int[]{1,0,0};
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

        return  res;
    }

    public static int[] dynamic(List<Integer> sequence){
        // init
        // [l, r, max]
        int[] res = new int[]{1,0,0};
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

    public static int sig(int i, int j, List<Integer> sequence) {
        int sequenceScore = 0;
        for (int k = i; k <= j ; k++) {
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
                return sigRec(i,k, sequence) + sigRec(k + 1, j, sequence);
            }
        }

        return 0; // this should never be reached
    }

}
