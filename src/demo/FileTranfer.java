/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.O;


/**
 *
 * @author Huynh
 */
public class FileTranfer extends Application {
    int ONE_PIECE=1024*32;
    private  MultiThreadForFile udp_progress_file=null;
    @Override
    public void start(Stage primaryStage) {
       
    }

    /**
     * @param args the command line arguments
     */
    public class MultiThreadForFile implements Runnable{

        DatagramSocket socketfile=null;
        public void bind(DatagramSocket socket){
            socketfile=socket;
        }
        public void start(){
            Thread udpFile = new Thread(this);
            udpFile.start();
        }
        public void run() {
           /*byte[] buffer = new byte[ONE_PIECE];
           DatagramPacket packet=null;
           while(true){
               packet=new DatagramPacket(buffer,buffer.length);
               try {
                   socketfile.receive(packet);
                   System.out.print("received");
                    InetAddress address;
                    address = socketfile.getInetAddress();
                    ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    File filereceive = new File("E:");
                    FileInfoClass fileInfo = (FileInfoClass) ois.readObject();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filereceive));
                    for(int i=0;i<fileInfo.getPiecesOfFile();i++){
                        packet=new DatagramPacket(buffer,buffer.length);
                        socketfile.receive(packet);
                        bos.write(buffer,0,ONE_PIECE);//Need Edit varriable
                    }
                    packet=new DatagramPacket(buffer,buffer.length);
                    socketfile.receive(packet);
                    bos.write(buffer,0,fileInfo.getLastByteLength());
                    bos.flush();
                    bos.close();
               } catch (IOException ex) {
                    System.out.print("134");
               } catch (ClassNotFoundException ex) {
                   System.out.print("136");
               }
              
              
             //  FileInfoClass fileinfo= (FileInfoClass)ois.readObject();
              
              
           }*/
            
        }
        public void sendfile(String SrcOfFile,InetSocketAddress address){
            try{
            File FileSend = new File(SrcOfFile);
            FileInputStream fin = new FileInputStream(FileSend);
            BufferedInputStream bis = new BufferedInputStream(fin);
            
            byte[] BufferOfFile = new byte[ONE_PIECE];
            
            long filesize = FileSend.length();
            int pieceOfFile = (int)filesize/ONE_PIECE;
            int lastByteLength=(int)filesize%ONE_PIECE;
            
            if (lastByteLength >0){
                pieceOfFile ++;
            }
            
            byte[][] fileBytes=new byte[pieceOfFile][ONE_PIECE];
            int count=0;
            while(bis.read(BufferOfFile,0,ONE_PIECE)>0){
                fileBytes[count++]=BufferOfFile;
                BufferOfFile=new byte[ONE_PIECE];
            }
            
            FileInfoClass fileInfo = new FileInfoClass();
            
            fileInfo.setFilename(FileSend.getName());
            fileInfo.setFileSize(FileSend.length());
            fileInfo.setPiecesOfFile(pieceOfFile);
            fileInfo.setLastByteLength(lastByteLength);
            
            // send file info
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(fileInfo);
            byte[] pathbuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(pathbuffer,pathbuffer.length);
            packet.setSocketAddress(address);
            socketfile.send(packet);
            
            packet = new DatagramPacket(baos.toByteArray(), 
            baos.toByteArray().length);
            packet.setSocketAddress(address);
            // send file
            socketfile.send(packet);
            
            
            for (int i = 0; i < (count - 1); i++) {
            packet = new DatagramPacket(fileBytes[i], ONE_PIECE);
            packet.setSocketAddress(address);
            socketfile.send(packet);
            
        }
        // send last bytes of file
            
        packet = new DatagramPacket(fileBytes[count - 1], ONE_PIECE);
        
        packet.setSocketAddress(address);
        System.out.print("157");
        System.out.print(packet.getSocketAddress());
        socketfile.send(packet);
        // close stream
        bis.close();
        System.out.print("sent");
        }
        catch(IOException io){
            System.out.println(io);
        }
    }
        
    }
    public static void main(String[] args) {
        
    }
    public void ReceiveFile(DatagramSocket socket) throws SocketException{
        udp_progress_file = new MultiThreadForFile();
        udp_progress_file.bind(socket);
        udp_progress_file.start();
        System.out.print("receiveFile");
    }
    public void BackendOfSendFile(String pathFile,InetSocketAddress address){
        udp_progress_file.sendfile(pathFile,address);
        
    }
    
}
