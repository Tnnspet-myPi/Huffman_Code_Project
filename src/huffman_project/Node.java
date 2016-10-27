package huffman_project;

public class Node
{
  char    symbol;
  Integer frequency;
  Node    left_node;
  Node    right_node;
  String  code;
  
  public Node()
  {
    frequency = 0;
  }
  
  public Node(char symbol, Integer frequency)
  {
    this.symbol    = symbol;
    this.frequency = frequency;
  }
  
  public Node(char symbol, Integer frequency, Node left, Node right)
  {
    this.symbol     = symbol;
    this.frequency  = frequency;
    this.left_node  = left;
    this.right_node = right;
  }
  
  public int compare(Node node1, Node node2)
  {
    return node1.frequency - node2.frequency;
  }
}
