
package Algorithrm;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
        public File Encrypt(File file_name,File EncryptKey) throws FileNotFoundException, IOException, Throwable{
                        FileInputStream fisKey = new FileInputStream(EncryptKey.getPath());
                        byte[] encodedSecretKey1 = new byte[(int) EncryptKey.length()];
                        fisKey.read(encodedSecretKey1);
                        fisKey.close();
                        
			FileInputStream fis = new FileInputStream(file_name.getPath()); // File cần đưa vào 
			FileOutputStream fos = new FileOutputStream("EncryptedAES_" + file_name.getName()); 
                        encryptwithAES(encodedSecretKey1, fis, fos);
            return new File("EncryptedAES_" + file_name.getName());
        }
        public void Decrypt(File file_name,File key_name) throws FileNotFoundException, IOException, Throwable{
                        File fileSecretKey = key_name;
                        FileInputStream fisKey = new FileInputStream(key_name.getPath());
                        byte[] encodedSecretKey2 = new byte[(int) fileSecretKey.length()];
                        fisKey.read(encodedSecretKey2);
                        fisKey.close();
                        
//                        PrivateKey = new BufferedReader(new FileReader(key_name)); 
//                        String pvKey = PrivateKey.readLine();  
                        ////////////
			FileInputStream fis2 = new FileInputStream(file_name.getPath()); 
			FileOutputStream fos2 = new FileOutputStream("DecryptAES_"+file_name.getName());
			decryptwithAES(encodedSecretKey2, fis2, fos2);  // Xuất file đã giải mã
        }
        public static void main() throws NoSuchAlgorithmException{
            // Sinh cặp khóa
            keyGenerator();
            ////// 
            BufferedReader EncrypKey = null;
            BufferedReader PrivateKey = null;
		try {
                        String file_name;
                        Scanner scanner = new Scanner(System.in);        
                        System.out.println("Nhập tên file cần mã hóa: ");
                        file_name = scanner.nextLine();
                        
                        File fileSecretKey = new File("KeyAES.txt");
                        FileInputStream fisKey = new FileInputStream("KeyAES.txt");
                        byte[] encodedSecretKey1 = new byte[(int) fileSecretKey.length()];
                        fisKey.read(encodedSecretKey1);
                        fisKey.close();
                        
			FileInputStream fis = new FileInputStream(file_name); // File cần đưa vào 
			FileOutputStream fos = new FileOutputStream("Encrypted_" + file_name); 
                        encryptwithAES(encodedSecretKey1, fis, fos);
                           ////////
                          
                        // Key1 dùng cho việc mã hóa
//                        EncrypKey = new BufferedReader(new FileReader("KeyAES.txt"));
//                        String key1 = EncrypKey.readLine();
//                        encryptwithAES(key1, fis, fos);// Xuất file đã mã hóa
//                      File fileSecretKey = new File(key_name);
                        
                       
                        ////////////
                        // Key2 dùng cho việc giải mã
                        
                        String key_name;
                        System.out.println("Nhập tên file chứa key giải mã: ");
                        key_name = scanner.nextLine();
                        fileSecretKey = new File(key_name);
                        fisKey = new FileInputStream(key_name);
                        byte[] encodedSecretKey2 = new byte[(int) fileSecretKey.length()];
                        fisKey.read(encodedSecretKey2);
                        fisKey.close();
                        
//                        PrivateKey = new BufferedReader(new FileReader(key_name)); 
//                        String pvKey = PrivateKey.readLine();  
                        ////////////
			FileInputStream fis2 = new FileInputStream("Encrypted_" + file_name); 
			FileOutputStream fos2 = new FileOutputStream("Decryptedz_" + file_name);
			decryptwithAES(encodedSecretKey2, fis2, fos2);  // Xuất file đã giải mã
		} catch (Throwable e) {
                        e.printStackTrace();

                } finally{
                    try{
                        if(PrivateKey != null){
                             PrivateKey.close();
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
        }
        public static void keyGenerator() throws NoSuchAlgorithmException{
            try {
			
			 // Thuật toán phát sinh khóa - AES
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128); // 
			// Tạo khóa bí mật
			SecretKey secretKey = kgen.generateKey();
                         
                        
			File privateKeyFile = createKeyFile(new File("KeyAES.txt"));
                        
			// Lưu Private Key
			FileOutputStream fos = new FileOutputStream(privateKeyFile);
                        byte[] binary = secretKey.getEncoded();
			fos.write(binary);
			fos.close();

			System.out.println("Generate key successfully");
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        }
        private static File createKeyFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}
        public static void encryptwithAES(byte[] key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithAES(key, Cipher.ENCRYPT_MODE, is, os);
	}
 
	public static void decryptwithAES(byte[] key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithAES(key, Cipher.DECRYPT_MODE, is, os);
	}

	public static void encryptOrDecryptwithAES(byte[] key, int mode, InputStream is, OutputStream os) throws Throwable {
                ///
                // Lấy key từ file chứa pucblic key để mã hóa
                IvParameterSpec iv = new IvParameterSpec(key);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES"); 
                
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // AES/ECB/PKCS5PADDING
                // Chọn chế độ giải mã hoặc mã hóa
		if (mode == Cipher.ENCRYPT_MODE) {                   
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);  
               
			CipherInputStream cis = new CipherInputStream(is, cipher);
			DES.doCopy(cis, os); // Gọi hàm doCopy để write to file
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			DES.doCopy(is, cos);
		}
	}

}
