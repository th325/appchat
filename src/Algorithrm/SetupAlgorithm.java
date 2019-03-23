/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author Huynh
 */
public class SetupAlgorithm {
    public ProgressAlgorithm vprogressAlgorithm;
    public String Algorithm;
    private File Publickey;
    private boolean PublickeyFile;
    private File Privatekey;
    private boolean PrivateKeyFile;
    private String HashFunction;
    private String NameAlgorithm;
    public String getNameAlgorithm(){
        return NameAlgorithm;
    }
    public File getPublickey(){
        return Publickey;
    }
    public File getPrivatekey(){
        return Privatekey;
    }
    public String getHashFunction(){
        return HashFunction;
    }
    public boolean getFilePublic(){
        return PublickeyFile;
    }
    public boolean getFilePrivate(){
        return PrivateKeyFile;
    }
    public SetupAlgorithm(String nameAlgorithm,File publickey,File privatekey,String hashfunction) throws IOException{
        ToolFile vToolFile = new ToolFile();
        vToolFile.copyFile(publickey.getPath(), publickey.getName());
       
        File publicnew = new File(publickey.getName());
        File privatenew = null;
         if (nameAlgorithm.equals("RSA")){ 
             privatenew = new File(privatekey.getName());
             vToolFile.copyFile(privatekey.getPath(), privatekey.getName());
             this.Privatekey=privatenew;
         }     
        vprogressAlgorithm = new ProgressAlgorithm(nameAlgorithm,publicnew,privatenew,hashfunction);
        this.Publickey=publicnew;
        this.HashFunction=hashfunction;
        this.NameAlgorithm=nameAlgorithm;
    }
}
