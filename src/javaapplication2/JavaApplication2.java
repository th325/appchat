/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import Algorithrm.Hashing;
import GUI.*;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import demo.*;
import java.util.LinkedList;
import javax.swing.text.BadLocationException;
/**
 *
 * @author phamc
 */
public class JavaApplication2 {

    static ChatFrame chatMain;
    static StartFrame start;
    static test testApp;
    
    /**
     * @param args the command line arguments
     */
    public class MultiThreadUpdate implements Runnable{

        @Override
        public void run() {
            
        }
        
    }
    public static void main(String[] args) throws InterruptedException, BadLocationException, Throwable {
        // TODO code application logic here
        String name = null;
        System.out.print("start");
        start = new StartFrame();
        start.setVisible(true);
        Hashing vHashing = new Hashing();
        while(name == null) {
            name = start.getName();
            TimeUnit.MILLISECONDS.sleep(1);
        }      
        start.setVisible(false);
        
//        testApp = new test();
//        
//        testApp.setVisible(true);
//        testApp.addUser(name);
        
        LinkedList<InfoClient> listInfo;
        chatMain = new ChatFrame();
        chatMain.setVisible(true);
        chatMain.setStartFrame(start);
        chatMain.setNameUserChatWithMe("Hello "+name);
        int sizeListPre=0;
        while(true){
             if (start.getChoose().cnn!=null){
                listInfo =start.getChoose().cnn.getListUserInfo();  
                System.out.println("waiting..");
                
                if (listInfo!=null){
                       System.out.println(listInfo.size());
                    if (sizeListPre!=listInfo.size()){
                       chatMain.removeAllUser();
                       for(InfoClient info:listInfo){
                           System.out.println(listInfo.size());
                           if (!info.getName().equals(name)){
                               System.out.print(info.getName()+" "+name);
                                 chatMain.addUser(info);
                           }
                      }
                       sizeListPre=listInfo.size();
                    }
                    if (!start.getChoose().cnn.progressMsg().getReceiveMsg().equals("")){
                     chatMain.addUserMsg(false,start.getChoose().cnn.progressMsg().getReceiveMsg(), false);
                     start.getChoose().cnn.progressMsg().setReceiveMsg();
                     
                    }
                    try{
                        if (!start.getChoose().cnn.progressFile().getMsgNameFile().equals("")){
                            chatMain.addUserMsg(false,start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[0], true);
                            
                            if(chatMain.getAlgorithm()!=null){
                                System.out.print("Decrypt"+start.getChoose().cnn.progressFile().getMsgNameFile());
                                String pathfile = chatMain.getAlgorithm().getAlgorithmInfo().vprogressAlgorithm.DecryptAlgorithmForFile(start.getChoose().cnn.progressFile().getFileReceive());
                                if(vHashing.checkHash(chatMain.getAlgorithm().getAlgorithmInfo().getHashFunction(),pathfile , start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[2])){
                                    System.out.println("Hash value is the same");
                                }else{
                                     System.out.print("HASHING");
                                    System.out.println(vHashing.getValueHash("MD5",pathfile));
                                    System.out.println(start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[2]);
                                    System.out.print("HASHED");
                                }
                            }
                            start.getChoose().cnn.progressFile().setMsgNameFile();
                        }
                    }catch(Exception e){
                        System.out.print("...81/line/ignore");
                    }
                    Thread.sleep(3000);
                }
             }
        }

    }
    
}
