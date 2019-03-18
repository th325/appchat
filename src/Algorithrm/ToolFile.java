/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

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
    
}
