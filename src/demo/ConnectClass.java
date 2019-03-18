/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import demo.FileTranferByTCP.ProgressForFile;
import demo.MsgByUDP.MultiThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import GUI.*;
import java.util.LinkedList;

/**
 *
 * @author Huynh
 */
public class ConnectClass extends Application {
    private int PortD=0;
    private MultiThread udp_progress=null;
    private ProgressForFile tcp_progress=null;
    private FrameChat fchat=null;
    private FileTranfer filetranfer=null;
    
    private ProgressForFile prof=null;
    private Socket socket = null;
    private chooseGuestClass choose=null;
    private LinkedList<InfoClient> listInfo;
    private DatagramSocket socketudp;
    
    /**
     *
     */
    public  FileTranferByTCP vFileTranferByTCP;
    public ProgressForFile progressFile(){
       return  tcp_progress;
    }
    
    public MultiThread progressMsg(){
        return udp_progress;
    }
    public LinkedList<InfoClient> getListUserInfo() {
        return listInfo;
    }
    public DatagramSocket getSocket(){
        return socketudp;
    }
    private StartFrame start=null;
    public class TaskOfServer implements Runnable{
        Socket sockets=null;
        ObjectOutputStream objOut=null;
        ObjectInputStream objIn=null;
        public void bind() throws IOException{
            sockets = new Socket("127.0.0.1",7777);
        }
        public void start(){
            Thread thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
            while(true){
                try {
                    objIn = new ObjectInputStream(sockets.getInputStream());
                    listInfo = (LinkedList<InfoClient>) objIn.readObject();
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(ConnectClass.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        public void sendInfoToServer(InfoClient info) throws IOException{
            objOut = new ObjectOutputStream(sockets.getOutputStream());
            objOut.writeObject(info);
        }
        
    }
    @Override
    public void start(Stage primaryStage) {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public void main(String[] args) throws SocketException, IOException {
      
    }
    public void TakeFrame(FrameChat fchats){
        fchat=fchats;
    }
    public boolean comparepathFile(String path){
    String regularExpression =  "([a-zA-Z]:)?(\\\\[a-zA-Z0-9 ._-]+)+\\\\?";

    Pattern pattern = Pattern.compile(regularExpression);
    
    boolean isMatched = Pattern.matches(regularExpression,path);
        return isMatched;
    }
    /*Return message Frame*/
    public void ReceiveMsg(String Port,String Msg) throws InterruptedException{
        System.out.println(Port+":"+Msg);
        Thread.sleep(1000);
        FrameChat fc =choose.findFrame(Integer.parseInt(Port));
        if (fc!=null)fc.UpdateOnFrame(Port,Msg);
        /*
        
        */
        //System.out.print(fchat.getName());
        /*update Msg on class FrameChat.java*/
    }
    public void SetBindForObj(InfoClient info,chooseGuestClass choosec,StartFrame st) throws SocketException, IOException, Exception{
        /*initia*/
            choose=choosec;
        /*UDP MESSAGE*/
            System.out.println(info.getPort());
            //vFileTranferByTCP = new FileTranferByTCP();
            socketudp=new DatagramSocket(info.getPort());
            udp_progress= new MultiThread();
            udp_progress.bind(socketudp,this);
            udp_progress.start();
            start=st;
            tcp_progress=new ProgressForFile();
            tcp_progress.bind();
            tcp_progress.start();
        /*Connect to Server*/
            System.out.print("PLE");
            TaskOfServer task = new TaskOfServer();
            task.bind();
            task.start();
            
            task.sendInfoToServer(info);
            
            
        /*Conduct tranfer info*/
        /*finished*/
            System.out.print("succesful tranfer");
        /*TCP FILE*/
            
       /* if (isSever){
            System.out.print("Server");
            ServerSocket ssocket=null;
            try{
            ssocket = new ServerSocket(2222);
            }catch(Exception e){
                
            }
            socket = ssocket.accept();
            System.out.print("Server DONE");
        }else{
            System.out.print("Client");
            
            socket=new Socket("127.0.0.1",2222);
            System.out.print("Client DONE");
            
        }*/
       
        try{
            
        }catch(Exception e){
            System.out.print(e);
        }
        /**/
        System.out.println(info.getPort()+"joined");
        /*filetranfer = new FileTranfer();
        filetranfer.ReceiveFile(socketudp);*/
        
        
    }
 
    public void Backend(String[] str) throws SocketException, Exception{
        try{ 
    /*Take agrument*/
        String IPD=str[0];
        int PortD=Integer.parseInt(str[1]);
        //int PortS=Integer.parseInt(str[2]);
        String msg=str[2];
    /*Send Progress*/
        //Take address/s receiver
        InetSocketAddress address= new InetSocketAddress(IPD,PortD);
        //conduct send
        System.out.println(comparepathFile(msg));
        
        if (comparepathFile(msg)){
            System.out.println("PathFIle");
            if (socket!=null){
                System.out.println("sending...");
                File file = new File(msg);
                
                 System.out.println("sending 100%");
            }else{
                 System.out.println("Can't send file in the time");
            }
             //filetranfer.BackendOfSendFile(msg,address);
              System.out.print("88");
        }else
             udp_progress.sento(address, msg);
       
        }catch(IOException e){
            System.out.print(95);
        }
    }
    
}
