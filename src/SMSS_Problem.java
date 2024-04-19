public class SMSS_Problem {

    public static int Optimal(int[] sequence) {
        int max = 0;
        int l = 1;
        int r = 0;
        int rmax = 0;
        int rstart = 1;
        int n = sequence.length;

        for (int i = 1; i < n; i++) {
            if (rmax > 0) {
                rmax = rmax + sequence[i];
            } else {
                rmax = sequence[i];
                rstart = i;
            }
            if (rmax > max) {
                max = rmax;
                l = rstart;
                r = i;
            }
        }
        return max;
    }
}
