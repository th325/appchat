/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import Algorithrm.Hashing;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Huynh
 */
public class  FileTranferByTCP{
    public  static class ProgressForFile implements Runnable {
                Hashing vHashing = new Hashing();
                private String MsgNameFile="";
		String func="";
		Socket receiveSocket = null;
                Socket sendSocket = null;
		ServerSocket receiveServer=null;
		int portFTP=0;
		DataInputStream din = null;
		DataOutputStream don = null;
                boolean Setted=false;
                private File FileReceive;
                private String vHash="";
                private String IPFTP="";
 
                public void setIPFTP(String ip){
                    IPFTP=ip;
                }
                public File getFileReceive(){
                    return FileReceive;
                }
                public String getMsgNameFile(){
                     return MsgNameFile;
                }
                public void setMsgNameFile(){
                    MsgNameFile="";
                }
                public void setHASH(String hex){
                    vHash=hex;
                }
		public void bind(String IP) throws IOException {
			try{
			receiveServer=new ServerSocket(8787);
                        Setted=true;
                        }catch(IOException e){
                            System.out.print("ignore Port");
                        }
                        if (Setted==false){ 
                            while(true){
                                try{
                                   sendSocket=new Socket("127.0.0.1",8787);
                                   break;
                                }catch(Exception e){
                                    System.out.println("reconecting...");
                                }
                            }
                        }
		}
		public void start()throws Exception {
			
			Thread st = new Thread(this);
			st.start();
		}
		
		public void run(){
			try {
                                if(Setted){
                                    receiveSocket=receiveServer.accept();
                                }else{
                                    receiveSocket=sendSocket;
                                }
				while(true) {
					din = new DataInputStream(receiveSocket.getInputStream());
                                        while(true){
                                            System.out.println("new file ");
                                            if (MsgNameFile.equals("")){
                                                break;
                                            }
                                        }
                                            String mfile = din.readUTF();
                                            MsgNameFile=mfile;
                                            System.out.println(mfile);
                                            File f = new File("receive\\"+mfile.split("tokenvalue87b19b5ad4fbd7")[0]);
                                            FileOutputStream fos = new FileOutputStream(f);
                                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                                            InputStream is = receiveSocket.getInputStream();
                                            int size=1;
                                            byte[] buffer = new byte[size];
                                            int byteread=0;
                                            int numarray=Integer.parseInt(mfile.split("tokenvalue87b19b5ad4fbd7")[1]);
                                            for(int i=0;i<numarray;i++) {
                                                    System.out.println(mfile+" "+byteread+" "+size*i);
                                                    bos.write(buffer,0,is.read(buffer,0,size));

                                            }
                                            bos.flush();
                                            System.out.print(numarray);
                                            /*while((byteread=is.read(buffer,0,10240))==10240){
                                                    System.out.println(mfile+" "+byteread+" "+i);
                                                    bos.write(buffer,0,byteread);
                                                    bos.flush();
                                                    i++;
                                            }*/
                                            System.out.println("complete");
                                            bos.close();
                                            fos.close();
                                            FileReceive=f;
				}
			
		}catch(Exception es) {
			//JOptionPane.showMessageDialog(null,"Loi nhan file, chi tiet:"+es);
		}
	}
		public void sentfile(File selectfile){
			try {
			
			FileInputStream fis = new FileInputStream(selectfile);
                       if (sendSocket==null){
                           System.out.println("sendSocket NULLLLLLL");
                       }
			BufferedInputStream bis = new BufferedInputStream(fis);
			don = new DataOutputStream(sendSocket.getOutputStream());
			int size = 1;
			int filesize = (int)selectfile.length();
                        System.out.print(selectfile.length());
			int numarray=(filesize/size);
                        if ((filesize % size)>0){
                            numarray++;
                        }
                          /*Hashing */
                        System.out.print("send name file");
			don.writeUTF(selectfile.getName()+"tokenvalue87b19b5ad4fbd7"+numarray+"tokenvalue87b19b5ad4fbd7"+vHash);
			System.out.println("sent "+selectfile.getName());
			OutputStream os = sendSocket.getOutputStream();
			int current = 0;
                      
			while(current!=filesize) {
				if(filesize-current>=size) {
					current+=size;
				}else {
					filesize=current;
				}
				byte[] buffer = new byte[size];
				bis.read(buffer,0,size);
				os.write(buffer,0,size);
				os.flush();
			}
			fis.close();
			bis.close();
                        
			System.out.println("Send 100%");
			}catch(Exception e) {
				Logger.getLogger(FileTranferByTCP.class.getName()).log(Level.SEVERE, null, e);
			}
            }
	};

}
