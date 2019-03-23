
package Algorithrm;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
public class DES {
	public File Encrypt(File file_name,File EncryptKey) throws NoSuchAlgorithmException {
                //keyGenerator();
                BufferedReader PublicKey = null;
                BufferedReader EncrypKey = null;
		try {
                        File File_encrypt = new File("EncryptedDES_" + file_name.getName());
                        FileOutputStream fos = new FileOutputStream(File_encrypt.getPath());    
                        FileInputStream fis = new FileInputStream(file_name.getPath());
                        EncrypKey = new BufferedReader(new FileReader(EncryptKey.getPath()));
                        String key1 = EncrypKey.readLine();
                        encryptwithDES(key1, fis, fos);// Xuất file đã mã hóa  
                        ////////
                        // Key1 dùng cho việc mã hóa
                        return new File("EncryptedDES_" + file_name.getName());
		} catch (Throwable e) {
                        e.printStackTrace();

                } finally{
                    try{
                        if(PublicKey != null){
                             PublicKey.close();
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            return null;
	}
        public String Decrypt(File file_name,File PublicKey) throws NoSuchAlgorithmException {
                //keyGenerator();
                BufferedReader TakeKey = null;
		try { 
                        FileInputStream fis2 = new FileInputStream(file_name);
                        System.out.print(file_name.getName());
			FileOutputStream fos2 = new FileOutputStream("DecryptedDES_"+file_name.getName().split("_")[1]); // Xuất file đã giải mã
                        ////////
                        // Key2 dùng cho việc giải mã
                            TakeKey = new BufferedReader(new FileReader(PublicKey)); // Get key từ tệp txt
                            String key2 = TakeKey.readLine(); 
                            decryptwithDES(key2, fis2, fos2);
                        return "DecryptDES_"+file_name.getName().split("_")[1];    
		} catch (Throwable e) {
                        e.printStackTrace();

                }
                return null;
                
	}
        public static void keyGenerator() {
            try {

			// Tạo khóa cho DES
			SecretKey Key = KeyGenerator.getInstance("DES").generateKey();
                       
			File publicKeyFile = createKeyFile(new File("KeyDES.txt"));
                        
			// Lưu Public Key của DES
			FileOutputStream fos = new FileOutputStream(publicKeyFile);
			fos.write(Key.getEncoded());
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
	public static void encryptwithDES(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithDES(key, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void decryptwithDES(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithDES(key, Cipher.DECRYPT_MODE, is, os);
	}

	public static void encryptOrDecryptwithDES(String key, int mode, InputStream is, OutputStream os) throws Throwable {
                DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);                
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}
        /// Ghi dữ liệu vào file
	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[32];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}



}
