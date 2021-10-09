import java.io.*;
import java.util.*;
public class HuffmanCompressor {

   public static void main(String[] args) { 
   Scanner kb = new Scanner(System.in);
   System.out.println("Enter the name of the file you'd like to compress.");
   String filename = kb.nextLine();
   
     // write coded version of the text file
      int[] table = buildTable(filename + ".txt");
      HuffmanTree tree = new HuffmanTree(table);
      tree.write(filename + ".code");
  
      compress(filename, table);
      expand(filename + ".code", filename, tree);
   
   }
   
   public static void expand(String codeFile, String fileName, HuffmanTree tree) { 
      BitInputStream in = new BitInputStream(codeFile);
      tree.decode(in, fileName + ".new");
   }
   public static void compress(String filename, int[] table) { 
      // write the short version of the text file
      String[] bits = storeBits(table, filename + ".code");
      writeShortFile(bits, filename + ".short");
   }
   
   public static void writeCodedFile(String filename) { 
   // write coded version of the text file
      int[] table = buildTable(filename + ".txt");
      HuffmanTree tree = new HuffmanTree(table);
      tree.write(filename + ".code");
   }
   
   private static int[] buildTable(String filename) { 
      int[] table = new int[256];
      try (FileReader fr = new FileReader(filename)) { 
         int content;
         while ((content = fr.read()) != -1) {
            table[content]++;
         }
      } catch (IOException E) { 
         System.out.print("");
      }
      return table;
   }
   
   private static String[] storeBits(int[] table, String filename) { 
      String[] bits = new String[256];
      try {
      Scanner codeReader = new Scanner(new File(filename));
         while(codeReader.hasNextInt()) { 
            int ascii = Integer.parseInt(codeReader.nextLine());
            String bit = codeReader.nextLine();
            bits[ascii] = bit;
         }
      } catch (FileNotFoundException E) { 
         System.out.print("");
      }
      return bits;
   
   }
   
   private static void writeShortFile(String[] bits, String filename) { 
      BitOutputStream writer = new BitOutputStream(filename);
      for (int i = 0; i < bits.length; i++) { 
         String bitChar = bits[i];
         for (int j = 0; j < bitChar.length(); i++) { 
            int bit = (int) bitChar.charAt(j);
            writer.writeBit(bit);
         }
      }
      writer.close();
   
   } 
}