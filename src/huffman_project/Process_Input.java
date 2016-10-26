package huffman_project;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Process_Input 
{
  private static final String INFILE_NAME  = "infile.dat";
  //private static final String OUTFILE_NAME = "outfile.dat"; 
  
  public static void main(String[] args) throws IOException 
  {
    //local variable
    String  input   = new String(Files.readAllBytes(Paths.get(INFILE_NAME)));
    Huffman decoder = new Huffman();
      
    decoder.Compress(input);   
  }
}

