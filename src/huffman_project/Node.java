package huffman_project;

/*---------------------------------------------------------*/
/* Class Name: Node                                        */
/*                                                         */
/* Description: This class holds all information for a     */
/*              single huffman code as well as pointers    */
/*              for the left and right tree children.      */
/*                                                         */
/*---------------------------------------------------------*/
public class Node
{
  // Global Variables
  char    symbol;
  Integer frequency;
  Node    left_node;
  Node    right_node;
  String  code;
  
  public Node()
  {
    frequency = 0;
  } /* End Constructor Node() */
  
  public Node(char symbol, Integer frequency)
  {
    this.symbol    = symbol;
    this.frequency = frequency;
  } /* End Constructor Node(char, Integer) */
  
  public Node(char symbol, Integer frequency, Node left, Node right)
  {
    this.symbol     = symbol;
    this.frequency  = frequency;
    this.left_node  = left;
    this.right_node = right;
  } /* End Constructor Node(char, Integer, Node, Node) */
} /* End Class Node */
