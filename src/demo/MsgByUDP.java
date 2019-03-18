/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 *
 * @author Huynh
 */
public class MsgByUDP {
    public static class MultiThread implements Runnable{

        DatagramSocket socketudp=null;
        ConnectClass TemptObj = null;
        private String receiveMsg="";
        public boolean newMsg=false;
        public void bind(DatagramSocket socket,ConnectClass cnn) throws SocketException{
            socketudp=socket;
            TemptObj=cnn;
        }
        public void setReceiveMsg(){
            receiveMsg="";
            newMsg=false;
        }
        public String getReceiveMsg(){
            return receiveMsg;
        }
        
        public void start() {
           Thread UdpThread= new Thread(this);
           UdpThread.start();
        }
        public void run() {
           byte[] buffer = new byte[1024];
           DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
           try{
               while(true){
                   socketudp.receive(packet);
                    while(true){
                        if (receiveMsg.equals("")){
                            break;
                        }
                    }
                    String msg=new String(buffer,0,packet.getLength());
                    System.out.println(msg+String.valueOf(packet.getOffset()));
                    receiveMsg=msg;
                    /*Return Receive Message */
                    //TemptObj.ReceiveMsg(String.valueOf(packet.getPort()),msg);
                   
               }
           }catch(Exception e){
               
           }
        }
        public void sento(InetSocketAddress address,String msg) throws IOException{
            byte[] buffer=msg.getBytes();
            DatagramPacket packet =new DatagramPacket(buffer,buffer.length);
            packet.setSocketAddress(address);
            socketudp.send(packet);
        }
        
    }

}