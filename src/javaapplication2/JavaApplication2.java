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
import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
/**
 *
 * @author phamc
 */
public class JavaApplication2 {
    static int size=1024;
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
                            String FileName=start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[0];
                            String HashValue=start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[2];
                            String FileSize=start.getChoose().cnn.progressFile().getMsgNameFile().split("tokenvalue87b19b5ad4fbd7")[1];
                            start.getChoose().cnn.progressFile().setMsgNameFile();
                            if(chatMain.getAlgorithm()!=null){
                                while(Integer.parseInt(FileSize)!=(int)new File("receive\\"+FileName).length()/size){
                                    /*loop*/
                                }
                                System.out.print("Decrypt..."+FileName);
                                String pathfile = chatMain.getAlgorithm().getAlgorithmInfo().vprogressAlgorithm.DecryptAlgorithmForFile(new File("receive\\"+FileName));
                                if(vHashing.checkHash(chatMain.getAlgorithm().getAlgorithmInfo().getHashFunction(),pathfile , HashValue)){
                                    System.out.println("Hash value is the same");
                                }else{
                                    System.out.print("HASHING");
                                    System.out.println(vHashing.getValueHash("MD5",pathfile));
                                    System.out.println(HashValue);
                                    System.out.print("HASHED");
                                }
                            }
                            
                        }
                    }catch(Exception e){
                        Logger.getLogger(JavaApplication2.class.getName()).log(Level.SEVERE, null, e);
                    }
                    Thread.sleep(3000);
                }
             }
        }

    }
    
}
