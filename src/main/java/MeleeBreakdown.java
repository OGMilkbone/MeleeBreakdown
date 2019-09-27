import com.devsmart.ubjson.*;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MeleeBreakdown {

    public static void main(String[] args) {
        File slippiFile = null;
        String fs = System.getProperty("file.separator");
        String slippiFolder = System.getProperty("user.home") + fs + "AppData" + fs + "Roaming" + fs + "SmashLadder Dolphin Launcher" + fs + "dolphin_downloads" + fs + "Project Slippi (r18)20" + fs + "FM-v5.9-Slippi-r18-Win" + fs + "Slippi" + fs;
        File slippiDir = new File(slippiFolder);
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if (slippiDir.isDirectory()) {
            fileChooser.setCurrentDirectory(new File(slippiFolder));
        }
        fileChooser.setDialogTitle("Select a Slippi Replay File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Slippi Replay Files", "slp");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getSelectedFile().getPath());
            slippiFile = fileChooser.getSelectedFile();
        }

        doEverything();
        readGame(slippiFile);
    }

    public static void doEverything() {
        return;
    }

    public static void readGame(File file) {
        Scanner scanner;
        UBObject meta;
        UBReader reader;
        UBArray arr;
        UBValue rawr;
        Metadata metadata = null;
        SlippiFile replaydata;


        try {
            reader = new UBReader(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)));
            UBObject obj = reader.read().asObject();
            for (Map.Entry<String, UBValue> value : obj.entrySet()) {
                if (value.getValue().isObject()) {
                    meta = value.getValue().asObject();
                    int i = 0;
                    int j = 0;
//                    System.out.println(meta.get("startAt"));
//                    System.out.println(meta.get("lastFrame"));
//                    System.out.println(meta.get("players").asObject().keySet());
                    for (String key : meta.get("players").asObject().keySet()) {
                        for (String key2 : meta.get("players").asObject().get(key).asObject().keySet()) {
                            for (String key3 : meta.get("players").asObject().get(key).asObject().get(key2).asObject().keySet()) {
                                i++;
                            }
                        }
                    }

                    Player[] players = new Player[i];
                    for (String key : meta.get("players").asObject().keySet()) {
                        for (String key2 : meta.get("players").asObject().get(key).asObject().keySet()) {
                            for (String key3 : meta.get("players").asObject().get(key).asObject().get(key2).asObject().keySet()) {

                                Player player;
                                System.out.println("this far. This is ");
                                System.out.println(meta.get("players").asObject().get(key));
                                player = new Player(Integer.parseInt(key3), meta.get("players").asObject().get(key).asObject().get(key2).asObject().get(key3).asInt());
                                players[j] = player;
                                j++;

//                                System.out.println("key3 : " + key3);
//                                System.out.println("value3 : " + meta.get("players").asObject().get(key).asObject().get(key2).asObject().get(key3));
                            }
                            System.out.println("fixed");
//                            System.out.println("key : " + key2);
//                            System.out.println("value : " + meta.get("players").asObject().get(key).asObject().get(key2));
                        }
                        //new Player(key, meta.get("players").asObject().get(key).asInt());
//                        System.out.println("key : " + key);
                        //System.out.println("value : " + meta.get("players").asObject().get(key));
                    }
                    System.out.println(meta.get("playedOn"));
                    System.out.println(meta.get("consoleNick"));
                    metadata = new Metadata(meta.get("startAt").asString(), meta.get("lastFrame").asInt(), players,
                            meta.get("playedOn").asString());

                    arr = obj.get("raw").asArray();
                    replaydata = new SlippiFile(arr, metadata);
                    System.out.println("Got here too");
                    continue;
                }
                System.out.println(value.getValue().getType());
                //arr = value.getValue().asArray();
                arr = obj.get("raw").asArray();
                //meta = obj.get("metadata").asObject()
                System.out.println(value.getValue().asArray().get(417));
                System.out.println(value.getValue().asArray().get(417).getType());


            }
//            byte[] raww;
//            raww = obj.get("raw").asByteArray();
            arr = obj.get("raw").asArray();

            //System.out.println(slippiFile);
            rawr = obj.get("metadata");
            System.out.println(rawr);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Crash");
            metadata = null;
            reader = null;
            arr = null;
        }
//        return;
//    }
//
//}

        int gameStartIndex = 14;
        int gameEndIndex = 432;
        byte[] gameData = new byte[418];

        int placeholder = gameStartIndex;
        int ticker = 0;
        while (ticker < 418){
            gameData[ticker] = arr.get(ticker).asByte();
            ticker++;
            placeholder++;
        }

        System.out.println("gameData output");
        for (int z = 0; z < gameData.length; z++)
        {
            System.out.print(gameData[z]);
            System.out.print(" ");
        }
        System.out.println();


        int playerInfoOffset = 36;
        int playerInfoIndex = 115;
        int frameStartIndex = 433;
        int playerID = 0;
        int numberOfCharacters = 0;

        while(playerID <= 3){
            int x = playerInfoIndex + playerInfoOffset * playerID;
            if (arr.get(x + 1).asInt() <= 1) {
                System.out.print("player using port ");
                System.out.print(playerID);
                System.out.print(" is playing ");

                int character = arr.get(x).asInt();
                String characterString;

                switch (character) {
                    case 0:
                        characterString = "Captain Falcon";
                        break;
                    case 1:
                        characterString = "Donkey Kong";
                        break;
                    case 2:
                        characterString = "Fox";
                        break;
                    case 3:
                        characterString = "Mr. Game and Watch";
                        break;
                    case 4:
                        characterString = "Kirby";
                        break;
                    case 5:
                        characterString = "Bowser";
                        break;
                    case 6:
                        characterString = "Link";
                        break;
                    case 7:
                        characterString = "Luigi";
                        break;
                    case 8:
                        characterString = "Mario";
                        break;
                    case 9:
                        characterString = "Marth";
                        break;
                    case 10:
                        characterString = "Mewtwo";
                        break;
                    case 11:
                        characterString = "Ness";
                        break;
                    case 12:
                        characterString = "Peach";
                        break;
                    case 13:
                        characterString = "Pikachu";
                        break;
                    case 14:
                        characterString = "Ice Climbers";
                        numberOfCharacters++;
                        break;
                    case 15:
                        characterString = "Jigglypuff";
                        break;
                    case 16:
                        characterString = "Samus";
                        break;
                    case 17:
                        characterString = "Yoshi";
                        break;
                    case 18:
                        characterString = "Zelda";
                        break;
                    case 19:
                        characterString = "Sheik";
                        break;
                    case 20:
                        characterString = "Falco";
                        break;
                    case 21:
                        characterString = "Young Link";
                        break;
                    case 22:
                        characterString = "Dr. Mario";
                        break;
                    case 23:
                        characterString = "Roy";
                        break;
                    case 24:
                        characterString = "Pichu";
                        break;
                    case 25:
                        characterString = "Ganondorf";
                        break;
                    case 26:
                        characterString = "None";
                        break;
                    default:
                        characterString = "Noone";
                }
                System.out.println(characterString);
                numberOfCharacters++;
            }
            playerID++;
        }


        int frameNumber = 200;

        int thisFrameStartIndex = 0;
        int postFrameStartIndex = 0;
        int nextFrameStartIndex = 0;

        HashMap<String, int[]> preframeHashmap = new HashMap<String, int[]>();
        HashMap<String, int[]> postframeHashmap = new HashMap<String, int[]>();

        int i = 150;
        thisFrameStartIndex = frameStartIndex + numberOfCharacters * 116 * (i - 1);
//        System.out.print("The index for the start of frame ");
//        System.out.print(i);
//        System.out.print(" is ");
//        System.out.println(thisFrameStartIndex);
        while(i <= frameNumber){

            if (i == 0){ i++;}                        //I don't want to call anything frame zero that doesn't make sense

            if (i <= 1){
                thisFrameStartIndex = 433; //this is always going to be the index of the start of frame data
            }

//            System.out.print("Frame Number ");
//            System.out.println(i);

            if (arr.get(thisFrameStartIndex).asInt() == 55) {
                int index = thisFrameStartIndex;
                int[] preframeData = new int[64];
                int[] postframeData = new int[52];
                int x = 0;
                while (x < numberOfCharacters){
//                    System.out.print(thisFrameStartIndex + x * 64);
//                    System.out.print(" ");
//                    System.out.println(arr.get(thisFrameStartIndex + x * 64));
                    int counter = 0;
                    while (counter < 64){
                        preframeData[counter] = arr.get(index).asInt();
                        counter++;
                        index++;
                    }
                    preframeHashmap.put("f" + i + "p" + preframeData[5], preframeData );
//                    System.out.println("preframeData output");
//                    if (preframeData[21] == 191){
//                        System.out.println("The character using port" + preframeData[5] +"is facing left");
//                    }
//                    else{System.out.println("The character using port" + preframeData[5] +"is facing right");}
//                    for (int z = 21; z <= 23; z++)
//                    {
//                        System.out.print(preframeData[z]);
//                        System.out.print(" ");
//                    }
//                    System.out.println();
                    x++;
                }
                postFrameStartIndex = thisFrameStartIndex + x * 64;
//                System.out.println("Post Frame Start!");
                index = postFrameStartIndex;
                x = 0;
                while (x < numberOfCharacters){
//                    System.out.print(postFrameStartIndex + x * 52);
//                    System.out.print(" ");
//                    System.out.println(arr.get(postFrameStartIndex + x * 52));
                    int counter = 0;
                    while (counter < 52){
                        postframeData[counter] = arr.get(index).asInt();
                        counter++;
                        index++;
                    }
                    postframeHashmap.put("f" + i + "p" + postframeData[5], postframeData);
//                    System.out.println("postframeData output");
//                    for (int z=0; z<postframeData.length; z++)
//                    {
//                        System.out.print(postframeData[z]);
//                        System.out.print(" ");
//                    }
//                    System.out.println();

                    //System.out.println(postframeData);
                    x++;
                }
                nextFrameStartIndex = postFrameStartIndex + x * 52;
//                System.out.print("The index of the start of the next frame is: ");
//                System.out.println(nextFrameStartIndex);
//                System.out.print("The value in at that index is: ");
//                System.out.println(arr.get(nextFrameStartIndex));
                thisFrameStartIndex = nextFrameStartIndex;
                i++;
            }
            else {

            }
        }
        return;
        //System.out.println(reader.toString());
        //return "FU";
        //return Game();
    }


}
//
//
//// C:\Users\Owner\AppData\Roaming\SmashLadder Dolphin Launcher\dolphin_downloads\Project Slippi (r18)20\FM-v5.9-Slippi-r18-Win\Slippi
////public static Graph readGraph(String fileName){
////        Scanner scanner;
////        if (fileName.equals("System.in")) {
////            scanner = new Scanner(System.in);
////        } else {
////            try {
////                scanner = new Scanner(new File(fileName));
////            } catch (Exception e){
////                System.out.println("Could not find the file");
////                return Graph();
////            }
////        }
////        while (scanner.hasNextLine()) {
////            String line = scanner.nextLine();
////            String[] graphLine = line.split(",");
////
////        }
////        return Graph();
////    }