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
        return rec_divide_and_conquer(sequence, 1, sequence.size());

    }
    // todo: not finished
    private static int[] rec_divide_and_conquer(List<Integer> sequence, int i, int j) {
        if (i == j) {
            if (sequence.get(i) > 0) {
                return new int[]{i, j, sequence.get(i)};
            } else {
                return new int[]{i, i-1, 0};
            }
        }else {
            int m = (i + j) / 2;
            int[] s1 = rec_divide_and_conquer(sequence, i, m);
            int[] s2 = rec_divide_and_conquer(sequence, m + 1, j);
        }
        return new int[]{0, 0, 0};
    }


}

