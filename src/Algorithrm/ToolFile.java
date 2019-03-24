/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

import com.sun.deploy.util.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
   public void AppendByte(File file_name,int size) throws FileNotFoundException, IOException{
       int appendbyte=(int) (size-file_name.length() % size);
       String field = fillString(' ',appendbyte);
       FileOutputStream fos = new FileOutputStream(file_name);
       fos.write(field.getBytes());
       fos.close();
       
   }
   public static String fillString(char fillChar, int count){  
       // creates a string of 'x' repeating characters  
       char[] chars = new char[count];  
       java.util.Arrays.fill(chars, fillChar);  
       return new String(chars);  
   }  
    
}
