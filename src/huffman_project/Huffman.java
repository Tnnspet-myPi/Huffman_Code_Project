package huffman_project;

//Imports needed for the class
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map.Entry;
import java.util.*;

/*---------------------------------------------------------*/
/* Class Name: Huffman                                     */
/*                                                         */
/* Description: This class holds all functions needed      */
/*              for creating huffman codes and counting    */
/*              the number of bits.                        */
/*                                                         */
/*---------------------------------------------------------*/
public class Huffman
{
  // Global Variables
  Double overall_count = 0.0;

  /*---------------------------------------------------------*/
  /* Function Name: Huffman                                  */
  /*                                                         */
  /* Description: This is the base constructor for the class */
  /*                                                         */
  /*---------------------------------------------------------*/
  public Huffman()
  {
  } /* End Constructor Huffman */
  
  /*---------------------------------------------------------*/
  /* Function Name: Compress                                 */
  /*                                                         */
  /* Description: This is the main function in the class     */
  /*              that goes through each of the functions    */
  /*              needed to create the codes.                */
  /*                                                         */
  /*---------------------------------------------------------*/
  public String Compress(String sentence) 
  {
    // Local Variables
    Map<Character, Node>    map = new HashMap<Character, Node>();
    Map<Character, Integer> char_freq;
    Queue<Node> node_queue;
    Node        root_node;
    String      out = ""; 
    
    // Create the frequency table and place them into 
    // a priority queue. 
    char_freq  = Get_Char_Freq(sentence);
    node_queue = Create_Node_Queue(char_freq);
    
    // Print out the priority queue and then build the
    // tree based off the queue. 
    out       += Print_Priority_Node_Queue(node_queue);
    root_node  = Build_Tree(node_queue);
     
    // Traverse  the entire tree from the root node and
    // generate all codes. After capturing all codes
    // print them to the user. 
    Generate_Node_Codes(root_node, map, "");
    out       += Print_Node_Codes_And_Count(map);
    
    // Return final string to be printed
    return out;
  } /* End Compress function */
  
  /*---------------------------------------------------------*/
  /* Function Name: Get_Char_Freq                            */
  /*                                                         */
  /* Description: This counts the frequency for each         */
  /*              character and digit.                       */
  /*                                                         */
  /*---------------------------------------------------------*/
  public Map<Character, Integer> Get_Char_Freq(String sentence)
  {
    // Local variables
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    int freq = 0;
    
    // Loop through all characters in a sentence string. 
    for(Character ch : sentence.toCharArray())
    {
      // Only accept alphabetic and Digits into our mapping.
      if(Character.isAlphabetic(ch) || Character.isDigit(ch))
      {
        // If we don't already have it in the map then add it
        if(!map.containsKey(ch))
        {
          map.put(ch, 1);
        }
        // If we already added the char then increase the
        // frequency by 1.  
        else
        {
          freq = map.get(ch);
          map.put(ch, ++freq);
        }
        
        // Increase the overall counter by 1. 
        overall_count++;
      }
    } /* End for loop */
    
    // Return the char frequency mapping */
    return map;
  }/* End Get_Char_Freq function */

  /*---------------------------------------------------------*/
  /* Function Name: Create_Node_Queue                        */
  /*                                                         */
  /* Description: This takes the mapping and places it in    */
  /*              a priority queue.                          */
  /*                                                         */
  /*---------------------------------------------------------*/
  public Queue<Node> Create_Node_Queue(Map<Character, Integer> map)
  {
    // Local variable
    Queue<Node> node_queue = new PriorityQueue<Node>(20, new Node_Comparator_Less_Than());
    
    // Loop through the entire mapping
    for (Entry<Character, Integer> entry : map.entrySet())
    {
      // Add to priority queue
      node_queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
    } /* End for loop */
    
    // Return priority queue. 
    return node_queue;
  } /* End Create_Node_Queue function */

  /*---------------------------------------------------------*/
  /* Function Name: Print_Priority_Node_Queue                */
  /*                                                         */
  /* Description: This takes the priority queue and change   */
  /*              it to be ordered by greater than and       */
  /*              then print to the user.                    */
  /*                                                         */
  /*---------------------------------------------------------*/
  public String Print_Priority_Node_Queue(Queue<Node> node_queue)
  {
    // Local Variables 
    NumberFormat formatter = new DecimalFormat("#0.000");
    Queue<Node>  copy      = new PriorityQueue<Node>(20, new Node_Comparator_Greater_Than());
    String return_string   = "";
    Node   print_node;

    // Copy all nodes into new priority queue
    copy.addAll(node_queue);
    
    // Loop through all items and print them out. 
    while(!copy.isEmpty())
    {
      print_node = copy.poll();
      return_string += print_node.symbol + ", " + formatter.format((print_node.frequency/overall_count)*100.0) + "%\n";
    } /* End while loop */
    
    // Return string
    return return_string;
  } /* End Print_Priority_Node_Queue function */
  
  /*---------------------------------------------------------*/
  /* Function Name: Build_Tree                               */
  /*                                                         */
  /* Description: This takes the priority queue and builds   */
  /*              a huffman  coding tree by combing nodes    */
  /*              of lowest frequency first.                 */
  /*                                                         */
  /*---------------------------------------------------------*/
  public Node Build_Tree(Queue<Node> node_queue)
  {
    // Local Variables
    Node node_left;
    Node node_right;
    
    // Loop through priority queue and combine left and right
    while(node_queue.size() > 1)
    {
      // Poll the left and right node
      node_left  = node_queue.poll();
      node_right = node_queue.poll();
      
      // Combine the nodes into one creating a new node. 
      node_queue.add(new Node('\0', node_left.frequency + node_right.frequency, node_left, node_right));
    } /* End while loop */
    
    // Return the root node for the tree
    return node_queue.poll();
  } /* End Build_Tree function */

  /*---------------------------------------------------------*/
  /* Function Name: Generate_Node_Codes                      */
  /*                                                         */
  /* Description: Traverse the tree building code words      */
  /*              once a child node has been reached.        */
  /*                                                         */
  /*---------------------------------------------------------*/
  public void Generate_Node_Codes(Node nodes, Map<Character, Node> map, String code_word)
  {
    // If there is no left node or right node then we have
    // reached a child node in the tree
    if (nodes.left_node == null && nodes.right_node == null)
    {
      // Take the node and add the code word to the
      // has map list. 
      nodes.code = code_word;
      map.put(nodes.symbol, nodes);
      return;
    }
    
    // Recursively traverse the tree. 
    Generate_Node_Codes(nodes.left_node,  map, code_word + "0");
    Generate_Node_Codes(nodes.right_node, map, code_word + "1");
  } /* End Generate_Node_Codes function */
  
  /*---------------------------------------------------------*/
  /* Function Name: Print_Node_Codes_And_Count               */
  /*                                                         */
  /* Description: This function prints out all code words    */
  /*              in the hash map.                           */
  /*                                                         */
  /*---------------------------------------------------------*/
  public String Print_Node_Codes_And_Count(Map<Character, Node> map)
  {
    // Local Variables
    Integer count        = 0;
    String return_string = "";
    
    // Loop through all code words in the map and print
    // them to the user
    for(Map.Entry<Character, Node> entry : map.entrySet())
    {
      // Create out put string as well as count the bits
      return_string += entry.getKey() + ", " + entry.getValue().code + "\n";
      count += (entry.getValue().frequency*entry.getValue().code.length());
    } /* End for loop */
    
    // Add the count to the out put string and then
    // return the string to be printed out. 
    return_string += count;
    return return_string;
  } /* End Print_Node_Codes_And_Count function */
} /* End Huffman class */

/*---------------------------------------------------------*/
/* Class Name: Node_Comparator_Less_Than                   */
/*                                                         */
/* Description: This is a comparator override for less     */
/*              than for the priority queue.               */
/*                                                         */
/*---------------------------------------------------------*/
class Node_Comparator_Less_Than implements Comparator<Node>
{
  @Override
  public int compare(Node node_left, Node node_right)
  {
    return node_left.frequency - node_right.frequency;
  }
} /* End Node_Comparator_Less_Than Class */

/*---------------------------------------------------------*/
/* Class Name: Node_Comparator_Greater_Than                */
/*                                                         */
/* Description: This is a comparator override for          */
/*              greater than for the priority queue.       */
/*                                                         */
/*---------------------------------------------------------*/
class Node_Comparator_Greater_Than implements Comparator<Node>
{
  @Override
  public int compare(Node node_left, Node node_right)
  {
    return node_right.frequency - node_left.frequency;
  }
} /* End Node_Comparator_Greater_Than Class */
