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
        int maxscore = 0;
        int[] res = new int[3];
        int l = 1;
        int r = 0;

        for (int i = 0; i <= sequence.size() - 1; i++) {
            for (int j = i; j <= sequence.size() - 1; j++) {

                int s = 0;
                for (int k = 0; k <= j; k++) {
                    s = s + sequence.get(k);
                }

                if (s >= maxscore) {
                    maxscore = s;
                    l = i;
                    r = j;
                    res[0] = maxscore;
                    res[1] = l;
                    res[2] = r;
                }
            }
        }
        return  res;
    }
}
