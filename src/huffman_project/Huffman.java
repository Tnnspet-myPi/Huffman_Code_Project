package huffman_project;

import java.util.*;
import java.util.Map.Entry;

public class Huffman
{

  public Huffman()
  {
    
  }
  
  public void Compress(String sentence)
  {
    Map<Character, Integer> char_freq = Get_Char_Freq(sentence);
    Queue<Node> node_queue = Create_Node_Queue(char_freq);
    Print_Priority_Node_Queue(node_queue);
    Node root_node = Build_Tree(node_queue);
    Map<Character, String> map = new HashMap<Character, String>();
    Generate_Node_Codes(root_node, map, "");
    
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
      }
    }
    
    return map;
  }

  public Queue<Node> Create_Node_Queue(Map<Character, Integer> map)
  {
    Queue<Node> node_queue = new PriorityQueue<Node>(20, new NodeComparator());
    
    for (Entry<Character, Integer> entry : map.entrySet())
    {
      node_queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
    }
    
    return node_queue;
  }

  public void Print_Priority_Node_Queue(Queue<Node> node_queue)
  {
    Queue<Node> copy = new PriorityQueue<Node>(20, new NodeComparator());
    Node print_node;
    
    copy.addAll(node_queue);
    
    while(!copy.isEmpty())
    {
      print_node = copy.poll();
      System.out.println(print_node.symbol + ", " + print_node.frequency);
    }
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

  public void Generate_Node_Codes(Node nodes, Map<Character, String> map, String s)
  {
    if (nodes.left_node == null && nodes.right_node == null)
    {
      map.put(nodes.symbol, s);
      System.out.println(nodes.symbol + " = " + s);
      return;
    }
    
    Generate_Node_Codes(nodes.left_node,  map, s+"0");
    Generate_Node_Codes(nodes.right_node, map, s+"1");
  }
  
}

class NodeComparator implements Comparator<Node>
{
  @Override
  public int compare(Node node_left, Node node_right)
  {
    return node_left.frequency - node_right.frequency;
  }
  
}