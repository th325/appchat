/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Huynh
 */
public class ProgressAlgorithm{
    private DES des;
    private AES aes;
    private RSA rsa;
    public String Algorithm;
    private File Publickey;
    private boolean PublickeyFile;
    private File Privatekey;
    private boolean PrivateKeyFile;
    private String HashFunction;
    private String NameAlgorithm;
    public ProgressAlgorithm(String nameAlgorithm, File publickey,File privatekey,  String hashfunction) {
       Publickey=publickey;
       Privatekey=privatekey;
       NameAlgorithm=nameAlgorithm;
       HashFunction = hashfunction;
    }
    public File getPrivatekey(){
        return Privatekey;
    }
    public File getPublickey(){
        return Publickey;
    }
    public File EncryptAlgorithmForFile(File file) throws NoSuchAlgorithmException, Throwable{
        switch(NameAlgorithm){
            case "AES":
                aes = new AES();
                return aes.Encrypt(file, this.Publickey);
            case "DES":
                des = new DES();
                return des.Encrypt(file, this.Publickey);
            case "RSA":
                //Only File
                rsa = new RSA();
                System.out.print(this.Publickey);
                return rsa.Encrypt(file, this.Publickey);
            default:
                return null;
        }
    }
    public String DecryptAlgorithmForFile(File file) throws NoSuchAlgorithmException, Throwable{
        switch(NameAlgorithm){
            case "AES":
                aes = new AES();
                aes.Decrypt(file, this.Publickey);
                break;
            case "DES":
                des = new DES();
                return des.Decrypt(file, this.Publickey);
               
            case "RSA":
                //Only File
                rsa = new RSA();
                rsa.Decrypt(file, this.Privatekey);
                break;
            default:
                return null;
        }
        return null;
    }
    
}
