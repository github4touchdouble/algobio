public class Optimal extends SMSS_Problem {
    public int l;
    public int r;
    public int rmax;
    public int rstart;
    public Optimal(int[] sequence) {
        super(sequence);
        this.l = 1;
        this.r = 0;
        this.rmax = 0;
        this.rstart = 1;
    }
    private void mss() {
        int sum = 0;
        for (int i = 1; i < sequence.length; i++) {
            sum += sequence[i];
            if (sum > rmax) {
                rmax = sum;
                r = i;
            } else if (sum < 0) {
                sum = 0;
                l = i + 1;
            }
        }
    }
}
