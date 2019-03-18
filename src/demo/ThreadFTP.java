/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Huynh
 */
public class ThreadFTP {
    private static final MultiThreadFTP mthreadFTP = new MultiThreadFTP();
    public static class MultiThreadFTP implements Runnable{
        private Socket  receiveSocket = null;
        private Socket  sendSocket = null;
        private ServerSocket receiveSSocket=null;
        public void bind() throws IOException{
            receiveSSocket = new ServerSocket(20);
            sendSocket = new Socket("127.0.0.1",20);
        }
        public void start(){
            Thread thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
           while(true){
               try {
                   receiveSocket=receiveSSocket.accept();
                   /*Set up connect two peer prepare for file*/
                   
                   /*Back to */
               } catch (IOException ex) {
                   Logger.getLogger(ThreadFTP.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }
        public void taskSend() throws IOException{
            sendSocket.getInputStream();
            /*Task Send file */
            
        }
        
    }
    public static void main(String[] args) throws IOException{
        mthreadFTP.bind();
        mthreadFTP.start();
    }
}
