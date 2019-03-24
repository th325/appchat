/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Huynh
 */
public class Server {
    private Socket socket=null;
    private ServerSocket serversocket=null;
    private ObjectInputStream objIn = null;
    private ObjectOutputStream objOut=null;
    private LinkedList<InfoClient> listObject;
    private LinkedList<Socket> listSocket;
    private InfoClient Iclient=null;
    private static ServerFrame FServer = null;
    public Server(ServerFrame fserver) {
       FServer=fserver;
    }
    public void  initia() throws IOException{
        serversocket = new ServerSocket(7777);
        listObject = new LinkedList<InfoClient>();
        listSocket = new LinkedList<Socket>();
        threadServer tS = new threadServer();
        tS.start();
    }
    public class threadServer implements Runnable{
        public void start(){
            Thread thread = new Thread(this);
            thread.start();
        }
        public void run() {
            while(true){
            System.out.print("Running");
                try {
                    socket=serversocket.accept();
                    System.out.print(socket);
                    objIn = new ObjectInputStream(socket.getInputStream());
                    Iclient =(InfoClient) objIn.readObject();
                    int index=checkExis(Iclient);
                    if(index!=-1){
                        listSocket.remove(index);
                         listObject.remove(index);
                    }else{
                    listSocket.add(socket);
                    listObject.add(Iclient);
                    }
                    FServer.UpdateFrame(listObject);
                    Thread.sleep(1000);
                    updateToAll();
                   
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                
         
        }
        }
        
    }
    public int checkExis(InfoClient info){
        int i=0;
        for(InfoClient sk:listObject){
            if(sk.getName().equals(info.getName()))return i;
            i++;
        }
        return -1;
    }
    public void updateToAll() throws IOException{
        for(Socket sk:listSocket){
            try{
            objOut = new ObjectOutputStream(sk.getOutputStream());
            objOut.writeObject(listObject);
            }catch(IOException e){
                
            }
        }
    }
    
}
