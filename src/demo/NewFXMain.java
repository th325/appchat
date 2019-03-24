/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewFXMain {

    public static void main(String[] args) {

        try {

            // convert file to byte[]
            byte[] bFile = readBytesFromFile("123.docx");
            char s='\0';
            byte[] append=String.valueOf(s).getBytes();
            byte[] destination = new byte[bFile.length + append.length];
// copy mac into end of destination (from pos ciphertext.length, copy mac.length bytes)
            System.arraycopy(append, 0, destination, bFile.length, append.length);
            //java nio
            //byte[] bFile = Files.readAllBytes(new File("C:\\temp\\testing1.txt").toPath());
            //byte[] bFile = Files.readAllBytes(Paths.get("C:\\temp\\testing1.txt"));

            // save byte[] into a file
            Path path = Paths.get("1234.docx");
            Files.write(path, destination);

            System.out.println("Done");

            //Print bytes[]
            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }
            File f1 =new File("123.docx");
            File f2 =new File("1234.docx");
            System.out.println(f1.length()+ " "+f2.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

}