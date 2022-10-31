import java.util.ArrayList;

public class LRU {
    private static ArrayList<Integer> tempref = main.getPos();
    private static Integer[] ref, inRAM, timeRAM;
    private static int hit, miss , frames;
    private static boolean isFull;


    static void run() {
        frames = main.getFrames();
        int x=0, pageFault=0, pageHit=0, pageRep=0;
        ref = tempref.toArray(new Integer[tempref.size()]);
        inRAM = new Integer[frames];
        timeRAM = new Integer[frames];

        System.out.println("");
        int tmp;
        for(int i = 0; i<ref.length;i++){
            tmp = duplicateInRAM(ref[i], x);

            if(tmp!=-1){                //check if ref is already in RAM
                timeRAM[tmp] = 0;    //remove this line to make this FIFO-page-replacement//////////////////////////
                main.write("miss", i, tmp);
                pageHit++;
            }else if(isFull) {          //check which frame to replace
                int spot = findOldest(x);
                inRAM[spot]=ref[i];
                timeRAM[spot]=0;
                main.write("miss", i, spot);

                pageFault++;
                pageRep++;
            }else{                      //adds to empty RAM frame
                main.write("miss", i, x);
                inRAM[x]=ref[i];
                timeRAM[x++]=0;
                if(x == frames){isFull = true;}
                pageFault++;
            }


            for(int v=0;v<timeRAM.length;v++){       //increase time for each frame in RAM
                if(timeRAM[v]!=null){
                    timeRAM[v] = timeRAM[v]+1;
                }
            }
        }




        //print everything
        main.EndWrite("LRU", pageHit, pageFault,pageRep);
        System.out.println("Algorithm:\t\t\t Least Recently Used");
        System.out.println("Frames:\t\t\t\t "+ frames);
        System.out.println("Memory accesses:\t "+ tempref.size());
        System.out.println("Page hits:\t\t\t "+pageHit);
        System.out.println("Page fault: \t\t "+pageFault);
        System.out.println("Page replacements: \t "+pageRep);
    }
    ///////////////////////////////////////////////////////////////////
    private static int findOldest(int x) {   //finds the oldest page in RAM
        int place=0, prev=timeRAM[0];
        for(int i=0;i<x;i++){
            if(prev<=timeRAM[i]){
                prev = timeRAM[i];
                place = i;
            }
        }
        return place;
    }

    ///////////////////////////////////////////////////////////////////
    private static int duplicateInRAM(Integer target, int x) {
        for(int i = 0; i< x; i++){
            if(inRAM[i]==(int)target){
                return i;
            }
        }
        return -1;
    }



}