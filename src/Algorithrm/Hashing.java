/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithrm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Huynh
 */
public class Hashing{
    public String getValueHash(String mode,String filepath) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(mode);
        String hex = checksum(filepath, md);
        return hex;
    }
    private String checksum(String filepath, MessageDigest md) throws IOException {

        // DigestInputStream is better, but you also can hash file like this.
        
        try (InputStream fis = new FileInputStream(filepath)) {
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
        public boolean checkHash(String mode,String filepath,String hex) throws IOException, NoSuchAlgorithmException{
            if (getValueHash(mode,filepath).equals(hex)){
                return true;
            }
            return false;
        }
    

    
}
