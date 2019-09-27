import java.util.ArrayList;

public class Frame {
    private ArrayList<PreFrame> preFrames;
    private ArrayList<PostFrame> postFrames;
}



//Preframe
//37|ff ffff 87|00| 006f 518c 7a|01 42|c2 7000
//00|41 2000 00|3f 8000 00|00 0000 00|00 0000
//00|00 0000 00|00 0000 00|00 0000 00|00 0000
//00|00 00|3b ea0e a1|3c ea0e a1|ff |0000 0000
//Postframe Splits
//38|ff ffff 87|00 |00|02| 0142 |c270 0000 |4120
//0000 |3f80 0000 |0000 0000 |4270 0000 |00|00
//|06|04 |bf80 0000 |00|00 |00|00 |40|00 0000 02|01
//|ffff |01|00