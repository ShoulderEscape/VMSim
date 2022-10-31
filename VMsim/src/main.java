import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static final int frames = 4; //Use to change -n <number of frames>
    private static final String file = "file.dat"; //Use to change -f <trace files>
    private static ArrayList<Integer> pos;
    private static ArrayList<Integer> data;

    public static ArrayList<Integer> getPos() {
        return pos;
    }

    public static ArrayList<Integer> getData() {
        return data;
    }

    public static int getFrames() {
        return frames;
    }
    public static void main(String [] args) throws FileNotFoundException {
        PrintWriter writer = null;
        data = new ArrayList<>();
        pos=new ArrayList<>();
        File reader = new File("file.dat");
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNext()) {
            //System.out.println(scanner.next());
            data.add(scanner.nextInt());
        }
        for(Integer value : data){
            pos.add((int) Math.ceil((double)value/255));
        }
        System.out.println(pos);
        while(true){
            System.out.println("Which method do you want to use?");
            line();
            System.out.println("1: First In First Out");
            System.out.println("2: Least recently used");
            System.out.println("3: Optimal");
            System.out.println("4: Quit");
            line();
            Scanner sc= new Scanner(System.in);
            int choice= sc.nextInt();
            String C;
            switch (choice) {
                case 1 -> FIFO.run();
                case 2 -> LRU.run();
                case 3 -> Optimal.run();
                case 4 -> {
                    System.out.println("Quitting program");
                    return;
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        }

    }
    public static int[] checkvalue(int passes, int hits, ArrayList<Integer> inCPU) {
        //System.out.println("passes: "+passes+" and "+(passes-255));
        int n=0;
        for (int i = 0; i < inCPU.size(); i++) {
            //System.out.println("value: "+value+" and "+(value-255));
            if (pos.get(passes).equals(inCPU.get(i))) {
                hits++;
                n=i;
                break;
            }
        }
        //line();
        int[] tot = new int[2];
        tot[0]=hits;
        tot[1]=n;
        return tot;
    }
    public static int setter(ArrayList<Integer> timeinCPU, ArrayList<Integer> inCPU, ArrayList<Integer> list, int passes, int misses, int replacements){
        int max = 0;
        int maxpos = 0;
        for (int i = 0; i < timeinCPU.size(); i++) {
            if (timeinCPU.get(i) > max) {
                max = timeinCPU.get(i);
                maxpos = i;
            }
        }
        inCPU.set(maxpos, list.get(passes));
        return maxpos;
    }
    public static void line(){
        System.out.println("\n-------------------------------------------------------------------\n");
    }
    public static void EndWrite(String name, int hits, int faults, int replacements){
        line();
        System.out.println("Algoritm:\t\t\t"+name);
        System.out.println("Frames:\t\t\t\t"+frames);
        System.out.println("Memory accesses:\t"+data.size());
        System.out.println("Page hits:\t\t\t"+hits);
        System.out.println("Page fauls:\t\t\t"+faults);
        System.out.println("Page replacements:\t"+replacements);
        line();
    }
    public static void write(String type, int address, int page) {
        address=data.get(address);
        switch (type) {
            case "miss" -> {
                System.out.println("Address "+String.format("%04X",address)+" not in physical memory");
                System.out.println("Page #"+page+" paged in");
            }
            case "hit" -> System.out.println("Address "+String.format("%04X",address)+" is on page "+page+" which is already in physical memory");
            default -> System.out.println("Invalid input into main.write");
        }
    }
}



