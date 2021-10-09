import java.util.*;
import java.io.*;
public class HuffmanTree { 
   private PriorityQueue<Node> treeQ;
   private Node root;
   public HuffmanTree(int[] count) {
   
   // builds PriorityQueue of root Nodes
      int length = count.length;
      for (int i = 0; i < length; i++) { 
         if (count[i] > 0) { 
            Node node = new Node(count[i], i);
            this.treeQ.offer(node);
         }
      }
      this.treeQ.offer(new Node(1, 256));  // Pseudo EOF character
      buildTree();
      
   }   
   
   public HuffmanTree(String codeFile) { 
   
   // builds PriorityQueue of root Nodes
      Scanner codeReader = new Scanner(codeFile);
      while (codeReader.hasNextLine()) { 
         int n = Integer.parseInt(codeReader.nextLine());
         String code = codeReader.nextLine();
         Node node = new Node(-1, n);
         treeQ.offer(node);
      }
      
      buildTree();
   }
   
   private void buildTree() { 
      while (treeQ.size() > 1) { 
         Node node1 = treeQ.poll();
         Node node2 = treeQ.poll();
         this.root = new Node(node1.getWeight() + node2.getWeight(), '\0');
         this.root.left = node1;
         node1.setBit(node1.bit += Node.LEFT);
         this.root.right = node2;
         node2.setBit(node2.bit += Node.RIGHT);
         treeQ.offer(this.root);
      }
   }
   
   public void decode(BitInputStream in, String outFile) { 
      try {
         PrintWriter print = new PrintWriter(new File(outFile));
         decode(in, print, root);
         print.close();
      } catch (FileNotFoundException E) { 
         System.out.print("");
      }
   }
   
   private void decode(BitInputStream in, PrintWriter print, Node node) {
      int bit = in.readBit(); 
      if (bit != -1) {
         if (node.right != null && node.left != null) { 
            if (bit == 0) {
               decode(in, print, node.left);
            } else { // bit == 1
               decode(in, print, node.right);
            }
         } else { 
            print.write((char) node.character);
         }
      }
   }
   
   public void write(String fileName) { 
      try {
         PrintWriter print = new PrintWriter(new File(fileName));
         Node root = treeQ.poll();
         if (root != null) { 
            write(print, root);
         }
         print.close();
      } catch (FileNotFoundException E) { 
         System.out.print("");
      }
   }
   
   private void write(PrintWriter print, Node node) { 
      if (node.right != null) { 
         write(print, node.right);
      }
      if (node.left != null) { 
         write(print, node.left);
      }
      if (node.right == null && node.left == null) { 
         print.write(node.toString());
      }
   }
   private class Node implements Comparable<Node> { 
      private int weight;
      private int character;
      private String bit;
      private Node left;  // 0
      private Node right; // 1
      public static final String LEFT = "0";
      public static final String RIGHT = "1";
   
      public Node(int weight, int character) { 
         this.weight = weight;
         this.character = character;
         this.bit = "";
      }    
   
      public void setBit(String bit) { 
         this.bit = bit;
      }
   
      public int getWeight() { 
         return weight; }
   
      public int getCharacter() { 
         return character; }
      public int compareTo(Node o) { 
         if (o == null) { throw new NullPointerException(); }
         return weight - o.weight;
      } 
   
      @Override
      public String toString() {
         return character + "\n" + bit;
      }
   }
}