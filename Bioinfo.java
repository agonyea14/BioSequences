import java.util.*;
import java.lang.*;
import java.io.*;

public class Bioinfo {

  public static final String COMMANDS[] = {"insert", };

  public static int seqSize;
  public static String inFile;
  public static ArrayList<String[]> commands;

  public static void main(String args[]){

    commands = new ArrayList<String[]>();
    seqSize = Integer.parseInt(args[0]);
    inFile = args[1];

    System.out.println("Loading File...");
    commands = readCommands(inFile);
    System.out.println("");

    System.out.println("Executing Commands...");
    for(int i = 0; i < commands.size(); i++)
      executeCommand(commands.get(i));
    System.out.println("");
  }

  /******************************************************************
  * Reads a file that contains commands for the the Bioinfo class.
  *
  * Stores the cammands into an ArrayList of String Arrays. Each
  * ArrayList item is a separate command while the String array
  * is each paramter of the command.
  *
  * If the line is empty or only contains whitespace it is ignored
  * and won't be added to the ArrayList
  *
  * @param file The file that contains the commands
  * @return An ArrayList<String[]> containg each command separated
  *         into each token parameter.
  ******************************************************************/
  public static ArrayList<String[]> readCommands(String file){

    try{
      BufferedReader bfReader =
        new BufferedReader(new FileReader(new File (file)));
      ArrayList<String[]> cmds = new ArrayList<String[]>();

      String[] lnCMD = bfReader.readLine().split("\\s+");
      cmds.add(lnCMD);

      for(String in = bfReader.readLine();
          in != null;
          in = bfReader.readLine()){
        lnCMD = in.split("\\s+");

        if(lnCMD[0].length() > 0)
          cmds.add(lnCMD);
      }
      System.out.println(cmds.size() + " commands loaded Succesful!");
      return cmds;
    }
    catch (IOException e){System.out.println("Error loading File");}
    return null;
  }

  /******************************************************************
  * Executes a single command from a string array
  *
  * If the array is empty nothing will happen.
  *
  * @param cmd The String array that contains the parameters for
  *            the command to be executed.
  ******************************************************************/
  public static void executeCommand(String[] cmd){
    if(cmd.length > 0){

      BioCommand cmdType = BioCommand.valueOf(cmd[0]);

      switch (cmdType){

        case insert:
          insertCMD(Integer.parseInt(cmd[1]), cmd[2], cmd[3]);
          break;

        case remove:
          System.out.print("Remove: ");
          System.out.println("Sequence " + cmd[1]);
          break;

        case print:
          if(cmd.length == 1){//prints all the sequences
            System.out.print("Print: ");
            System.out.println("All sequences");
          }
          else{//prints the specified sequence
            System.out.print("Print: ");
            System.out.println("Sequence " + cmd[1]);
          }
          break;

        case clip:
          if(cmd.length == 3){//Clip with only a start
            System.out.print("Clip: ");
            System.out.println("Sequence: "+cmd[1]+" at "+cmd[2]);
          }
          else if(cmd.length == 4){//clip with a start and end
            System.out.print("Clip: ");
            System.out.println(
              "Sequence: "+cmd[1]+" from "+cmd[2]+" to "+ cmd[3]);
          }
          break;

        case splice:
          System.out.print("Splice: ");
          System.out.println(
            "[" +cmd[2]+"]-"+cmd[3]+" at "+cmd[4]+" in sequence "+cmd[1]);
          break;

        case copy:
          System.out.print("Copy: ");
          System.out.println("Sequence "+cmd[1]+" to sequence: "+cmd[2]);
          break;

        case swap:
          System.out.print("Swap: ");
          System.out.println(
            "Sequence "+cmd[1]+" from "+cmd[2]+", with sequence "+cmd[3]+
            " from "+cmd[4]);
          break;

        case overlap:
          System.out.print("Overlap: ");
          System.out.println("Between sequences "+cmd[1]+" and "+cmd[2]);
          break;

        case transcribe:
          System.out.print("Transcribe: ");
          System.out.println("Sequence " + cmd[1]);
          break;

        case translate:
          System.out.print("Translate: ");
          System.out.println("Sequence " + cmd[1]);
          break;

        default:
          System.out.print("Default: ");
          System.out.println("Invalid Command");
          break;
      }
    }
  }

  public static void insertCMD(int seq, String type, String bases){
    System.out.print("Insert "+seq+" "+type+" "+bases+": ");

    String result = "";

    if(!validSeqParam(seq))
      result = result + "[seqSize] ";

    if(!validTypeParam(type))
      result = result + "[type] ";

    if(result.length() > 0)
      result = "FAILED " + result;
    else
      result = "SUCCESS";

    System.out.println(result);

  }

  public static Boolean validSeqParam(int seq){
    if(seq < 0 || seq >= seqSize)
      return false;
    else
      return true;
  }

  public static Boolean validTypeParam(String type){
    if(type.equals("DNA") || type.equals("RNA") || type.equals("AA"))
      return true;
    else
      return false;
  }



  public enum BioCommand {
    insert, remove, print, clip, splice,
    copy, swap, overlap, transcribe, translate;
  }

}
