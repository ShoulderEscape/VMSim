import java.util.ArrayList;

public class FIFO {
    private static final int frames = main.getFrames();
    private static ArrayList<Integer> pos;

    static void run() {
        int passes = 0;
        int misses = 0;
        int hits = 0;
        int replacements = 0;
        ArrayList<Integer> inCPU = new ArrayList<>();
        pos= new ArrayList<>(main.getPos());
        ArrayList<Integer> timeinCPU = new ArrayList<>();

        while (passes != pos.size()) {
            int currenthits = hits;
            int[] tot =main.checkvalue(passes,hits, inCPU);
            hits=tot[0];
            int n=tot[1];
            if (inCPU.size() < frames) {
                if (currenthits == hits) {
                    n=inCPU.size();
                    main.write("miss",passes,n);
                    inCPU.add(pos.get(inCPU.size()));
                    timeinCPU.add(0);
                    misses++;
                } else{
                    timeinCPU.set(n,0);
                    main.write("hit",passes,n);
                }
            } else {
                if (currenthits == hits) {
                    int maxpos = main.setter(timeinCPU, inCPU, pos,passes,misses,replacements);
                    timeinCPU.set(maxpos, 0);
                    misses++;
                    replacements++;

                    main.write("miss",passes,maxpos);
                } else{
                    timeinCPU.set(n,0);
                    main.write("hit",passes,n);
                }
            }

            //main.TestPrint(timeinCPU, inCPU);
            passes++;
        }
        main.EndWrite("FIFO", hits, misses, replacements);
    }
}



