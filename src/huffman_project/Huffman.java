package huffman_project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

public class Huffman
{
  Double overall_count = 0.0;
  
  public Huffman()
  {
    
  }
  
  public void Compress(String sentence) throws FileNotFoundException, UnsupportedEncodingException
  {
    String out = "";
    PrintWriter writer = new PrintWriter("outfile.dat", "UTF-8");
    
    Map<Character, Integer> char_freq = Get_Char_Freq(sentence);
    Queue<Node> node_queue = Create_Node_Queue(char_freq);
    out += Print_Priority_Node_Queue(node_queue);
    Node root_node = Build_Tree(node_queue);
    Map<Character, Node> map = new HashMap<Character, Node>();
    Generate_Node_Codes(root_node, map, "");
    out += Print_Node_Codes_And_Count(map);
    
    writer.println(out);
    writer.close();
    System.out.println(out);
  }
  
  public Map<Character, Integer> Get_Char_Freq(String sentence)
  {
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    int freq = 0;
    
    for(Character ch : sentence.toCharArray())
    {
      if(Character.isAlphabetic(ch) || Character.isDigit(ch))
      {
        if(!map.containsKey(ch))
        {
          map.put(ch, 1);
        }
        else
        {
          freq = map.get(ch);
          map.put(ch, ++freq);
        }
        overall_count++;
      }
    }
    
    return map;
  }

  public Queue<Node> Create_Node_Queue(Map<Character, Integer> map)
  {
    Queue<Node> node_queue = new PriorityQueue<Node>(20, new NodeComparatorLessThan());
    
    for (Entry<Character, Integer> entry : map.entrySet())
    {
      node_queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
    }
    
    return node_queue;
  }

  public String Print_Priority_Node_Queue(Queue<Node> node_queue)
  {
    NumberFormat formatter = new DecimalFormat("#0.000");
    Queue<Node> copy = new PriorityQueue<Node>(20, new NodeComparatorGreaterThan());
    Node print_node;
    String return_string = "";

    copy.addAll(node_queue);
    
    while(!copy.isEmpty())
    {
      print_node = copy.poll();
      //return_string += print_node.symbol + ", " + formatter.format((print_node.frequency/overall_count)*100.0) + "%\n";
      return_string += print_node.symbol + ", " + (print_node.frequency) + "\n";
    }
    return return_string;
  }
  
  public Node Build_Tree(Queue<Node> node_queue)
  {
    Node node_left;
    Node node_right;
    
    while(node_queue.size() > 1)
    {
      node_left  = node_queue.poll();
      node_right = node_queue.poll();
      
      node_queue.add(new Node('\0', node_left.frequency + node_right.frequency, node_left, node_right));
    }
    
    return node_queue.poll();
  }

  public void Generate_Node_Codes(Node nodes, Map<Character, Node> map, String code_word)
  {
    if (nodes.left_node == null && nodes.right_node == null)
    {
      nodes.code = code_word;
      map.put(nodes.symbol, nodes);
      return;
    }
    
    Generate_Node_Codes(nodes.left_node,  map, code_word + "0");
    Generate_Node_Codes(nodes.right_node, map, code_word + "1");
  }

  public String Print_Node_Codes_And_Count(Map<Character, Node> map)
  {
    Integer count = 0;
    String return_string = "";
    
    for(Map.Entry<Character, Node> entry : map.entrySet())
    {
      return_string += entry.getKey() + " = " + entry.getValue().code + "\n";
      count += (entry.getValue().frequency*entry.getValue().code.length());
    }
    return_string += count;
    return return_string;
  }
}

class NodeComparatorLessThan implements Comparator<Node>
{
  @Override
  public int compare(Node node_left, Node node_right)
  {
    return node_left.frequency - node_right.frequency;
  }
}

class NodeComparatorGreaterThan implements Comparator<Node>
{
  @Override
  public int compare(Node node_left, Node node_right)
  {
    return node_right.frequency - node_left.frequency;
  }
}