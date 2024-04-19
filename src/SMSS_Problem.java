import java.util.List;

public class SMSS_Problem {

    public static int optimal(List<Integer> sequence) {
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
        return max;
    }

    public static int divide_and_conquer (List<Integer> sequence) {
        return 0;
    }
}
