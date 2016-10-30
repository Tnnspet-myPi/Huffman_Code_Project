package huffman_project;

//Imports needed for the class
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/*---------------------------------------------------------*/
/* Class Name: Process_Input                               */
/*                                                         */
/* Description: This class holds the main static           */
/*              function for reading in the file and       */
/*              calling the Huffman encoding algorithm.    */
/*                                                         */
/*---------------------------------------------------------*/
public class Process_Input 
{
  // Global variable constant
  private static final String INFILE_NAME   = "infile.dat";
  private static final String OUTFILE_NAME  = "outfile.dat";
  
  /*---------------------------------------------------------*/
  /* Function Name: main                                     */
  /*                                                         */
  /* Description: Main function that reads in a file text    */
  /*              and then runs it through the huffman       */
  /*              encoder.                                   */
  /*                                                         */
  /*---------------------------------------------------------*/
  public static void main(String[] args) throws IOException 
  {
    // local variable
    String      input   = new String(Files.readAllBytes(Paths.get(INFILE_NAME)));
    PrintWriter writer  = new PrintWriter(OUTFILE_NAME, "UTF-8");
    Huffman     decoder = new Huffman();
    String      out     = "";

    // Huffman encode algorithm
    out = decoder.Compress(input);   
    
    // Print to outfile
    writer.println(out);
    writer.close();
  } /* End main function */
} /* End Class Process_Input */
