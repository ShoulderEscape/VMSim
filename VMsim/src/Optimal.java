import javax.swing.text.Style;
import java.util.ArrayList;

public class Optimal {
    private static ArrayList<Integer> tempref = main.getPos();
    private static Integer[] ref, rCPU, inRAM, r;
    private static int hit, miss , frames;
    private static boolean isFull;
    //private static int[] timeCPU, inCPU, r;

    static void run() {
        ref = tempref.toArray(new Integer[tempref.size()]); // array of all pages
        isFull = false;
        frames = main.getFrames();         // amount of frames in RAM
        inRAM = new Integer[frames];   // value of page in frames

        int x=0, pageFault=0, pageHit=0, pageRep=0;

        for(int i = 0; i<ref.length;i++){
            if(duplicateInRAM(ref[i], x)){ //check if ref is already in cpu
                //ref[i]=null;
                pageHit++;
            }else if(isFull) {          //check which frame to replace
                int replaceFrame = predict(i);
                inRAM[replaceFrame] = ref[i];
                //ref[i]=null;
                pageFault++;
                pageRep++;

            }else{                      //adds to empty RAM frame
                inRAM[x++]=ref[i];
                if(x == frames){isFull = true;}
                pageFault++;
            }
        }

        //print everything
        System.out.println("Algorithm:\t\t\t Optimal");
        System.out.println("Frames:\t\t\t\t "+ frames);
        System.out.println("Memory accesses:\t "+ tempref.size());
        System.out.println("Page hits:\t\t\t "+pageHit);
        System.out.println("Page fault: \t\t "+pageFault);
        System.out.println("Page replacements: \t "+pageRep);
    }
    ///////////////////////////////////////////                System.out.print(inRAM[s]+", ");
    private static int predict(int currPos){              //gives the place in frames with the number not used for the longest time in the future
        int framePlace=0, longestInFuture=0;
        for(int j = 0; j< inRAM.length; j++) {
            for (int i = currPos; i <= ref.length; i++) {
                if(i==ref.length) {          //if no duplicate exist then THAT frame place in RAM gets replaces
                    return j;
                }
                if (inRAM[j] == (int)ref[i]) {      //is there a duplicate in ref?
                    if(longestInFuture<i){     //then set the current place in ref as the "not used for the longest duration of the future"
                        longestInFuture = i;

                        framePlace= j;
                        break;
                    }
                }
            }
        }
        // System.out.println("Returning: "+framePlace);
        return framePlace;
    }
    //////////////////////////////////////////////////
    private static boolean duplicateInRAM(Integer target, int x) {
        for(int i = 0; i< x; i++){
            if(inRAM[i]==(int)target){
                return true;
            }
        }
        return false;
    }
}