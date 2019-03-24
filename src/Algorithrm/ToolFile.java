/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

import com.sun.deploy.util.StringUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Huynh
 */
public class ToolFile {
   public void copyFile(String src,String dest) throws IOException{
        Path source = Paths.get(src);
		// Destination file.
	Path destination = Paths.get(dest);
       Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
   public static  void AppendByte( File file_name,int size) throws FileNotFoundException, IOException{
    String str = "World";
    BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, true));
    writer.append(' ');
    writer.append(str);
    writer.close(); 
   }
   public static String fillString(char fillChar, int count){  
       // creates a string of 'x' repeating characters  
       char[] chars = new char[count];  
       java.util.Arrays.fill(chars, fillChar);  
       return new String(chars);  
   }
    public static void runfile() throws IOException{
        
         XWPFDocument document = new XWPFDocument(new FileInputStream("123.docx"));
       //Blank Document
       //Write the Document in file system
       //create Paragraph
       try ( //Write the Document in file system
               FileOutputStream out = new FileOutputStream( new File("createdocument.docx"))) {
           //Blank Document
           //Write the Document in file system
           //create Paragraph
           File getLengOriginalFile = new File("123.docx"); 
           int length2 = (int)getLengOriginalFile.length();
           int msize = (int)1024-length2%1024;
           String field=fillString('\u200e',msize);
           //for(int i=0;i<msize;i++)field+="c";
//           XWPFParagraph paragraph = document.createParagraph();
//           XWPFRun run = paragraph.createRun();
//           run.setText(field);
           //
           document.write(out);
       }
    }
    public static void main(String[] args) throws IOException{
          // TODO code application logic here
        //Blank Document
        runfile();
        
      
//        XWPFDocument document = new XWPFDocument(new FileInputStream("createdocument.docx"));
//      //Write the Document in file system
//        FileOutputStream out = new FileOutputStream( new File("createdocument1.docx"));   
        //Blank Document    
      //Write the Document in file system       
      //create Paragraph
      File getLengOriginalFile = new File("createdocument.docx");  
      int length2 = (int)getLengOriginalFile.length();
      int count  = 0;
      while(length2 % 1024 != 0){
        XWPFDocument document = new XWPFDocument(new FileInputStream("createdocument.docx"));
      //Write the Document in file system
        FileOutputStream out = new FileOutputStream( new File("createdocument.docx"));  
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
       
        run.setText(" ");
        //
        
        document.write(out);
        out.close();
        File filess = new File("createdocument.docx");       
        length2 = (int)filess.length();
        System.out.println(filess.length());
        count++;
        
      }
        runfile();
        XWPFDocument document = new XWPFDocument(new FileInputStream("createdocument.docx"));
        FileOutputStream out = new FileOutputStream( new File("createdocument.docx"));  
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setText(fillString(' ', count));
        document.write(out);
        out.close();
        
        File filess = new File("createdocument.docx");       
        System.out.println(filess.length());
    }
}
