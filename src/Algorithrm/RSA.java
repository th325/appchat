
package Algorithrm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;


public class RSA {
        public File Encrypt(File file_name,File Encryptkey) throws FileNotFoundException, Throwable{
           
            FileInputStream fis = new FileInputStream(file_name.getPath());
            FileOutputStream fos = new FileOutputStream("receive//EncryptedRSA_" + file_name.getName());
            System.out.println(Encryptkey.getPath());
             System.out.println("ADRESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            encryptwithRSA(Encryptkey.getName(), fis, fos);// Xuất file đã mã hóa
            return new File("EncryptedRSA_" + file_name.getName());
        }
        public String Decrypt(File file_name,File privatekey) throws FileNotFoundException, Throwable{
             System.out.println("asdasdasdasssssssssssssssssssssssssssssssssss");
            System.out.println(privatekey.getPath());
             System.out.println("ADRESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            FileInputStream fis2 = new FileInputStream(file_name.getPath()); 
            FileOutputStream fos2 = new FileOutputStream("receive//DecryptedRSA_"+file_name.getName().split("_")[1]);
            decryptwithRSA(privatekey.getName(), fis2, fos2);  // Xuất file đã giải mã
            return "receive//DecryptedRSA_"+file_name.getName().split("_")[1];
        }
        public static void main() throws NoSuchAlgorithmException, InvalidKeySpecException{
            // Sinh cặp khóa
               // keypairGenerator();
            ////// 
                BufferedReader PublicKey = null;
                BufferedReader PrivateKey = null;
		try {
                        String file_name;
                        Scanner scanner = new Scanner(System.in);        
                        System.out.println("Nhập tên file cần mã hóa: ");
                        file_name = scanner.nextLine();
			FileInputStream fis = new FileInputStream(file_name); // File cần đưa vào 
			FileOutputStream fos = new FileOutputStream("EncryptedRDS_" + file_name); 
                        
//                        PublicKey = new BufferedReader(new FileReader("publicKeyRSA.txt")); 
//                        String pbKey = PublicKey.readLine();   
//                        PublicKey.close();
                        ///////
                        
                        String key_name;
                        System.out.println("Nhập tên file chứa key giải mã: ");
                        key_name = scanner.nextLine();
//                        PrivateKey = new BufferedReader(new FileReader(key_name)); 
//                        String pvKey = PrivateKey.readLine();  
//                        PrivateKey.close();
                        
                        encryptwithRSA("publicKeyRSA.txt", fis, fos);// Xuất file đã mã hóa
			FileInputStream fis2 = new FileInputStream("Encrypted_" + file_name); 
			FileOutputStream fos2 = new FileOutputStream("Decryptedz_" + file_name);
			decryptwithRSA(key_name, fis2, fos2);  // Xuất file đã giải mã
		} catch (Throwable e) {
                        e.printStackTrace();

                } finally{
                    try{
                        if((PublicKey != null) && (PrivateKey != null)){
                             PublicKey.close();
                             PrivateKey.close();
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
        }
        public static void keypairGenerator() throws NoSuchAlgorithmException, InvalidKeySpecException{
            try {
			SecureRandom sr = new SecureRandom();
			// Thuật toán phát sinh khóa - RSA
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048, sr); // Ở đây Sử dụng key 1024 bit. (hoặc có thể lớn hơn tùy chọn)
                       
			// Khởi tạo cặp khóa
			KeyPair kp = kpg.genKeyPair();
		
			PublicKey publicKey = kp.getPublic();
			PrivateKey privateKey = kp.getPrivate();
                        KeyFactory fact = KeyFactory.getInstance("RSA");
                        RSAPublicKeySpec pub = fact.getKeySpec(publicKey, RSAPublicKeySpec.class);
                        RSAPrivateKeySpec priv = fact.getKeySpec(privateKey, RSAPrivateKeySpec.class);
                        createKeyFile("publicKeyRSA.txt", pub.getModulus(), pub.getPublicExponent());
                        createKeyFile("privateKeyRSA.txt", priv.getModulus(), priv.getPrivateExponent());
//			File publicKeyFile = createKeyFile(new File("publicKeyRSA.txt"));
//			File privateKeyFile = createKeyFile(new File("privateKeyRSA.txt"));

//			// Lưu Public Key
//			FileOutputStream fos = new FileOutputStream(publicKeyFile);
//			fos.write(publicKey.getEncoded());
//			fos.close();
//
//			// Lưu Private Key
//			fos = new FileOutputStream(privateKeyFile);
//			fos.write(privateKey.getEncoded());
//			fos.close();

			System.out.println("Generate key successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
        public static void createKeyFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
                ObjectOutputStream fileOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
                try {
                    fileOut.writeObject(mod);
                    fileOut.writeObject(exp);
                } catch (Exception e) {
                        throw new IOException("Unexpected error");
                } finally {
                        fileOut.close();
                        
                }
}
//        private static File createKeyFile(File file) throws IOException {
//		if (!file.exists()) {
//			file.createNewFile();
//		} else {
//			file.delete();
//			file.createNewFile();
//		}
//		return file;
//	}
        static Key readKeyFromFile(String keyFileName) throws IOException {
                InputStream in = new FileInputStream(keyFileName);
                ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
                try {
                    BigInteger m = (BigInteger) oin.readObject();
                    BigInteger e = (BigInteger) oin.readObject();
                    KeyFactory fact = KeyFactory.getInstance("RSA");
                    if (keyFileName.startsWith("public"))
                        return fact.generatePublic(new RSAPublicKeySpec(m, e));
                    else
                        return fact.generatePrivate(new RSAPrivateKeySpec(m, e));
                } catch (Exception e) {
                    throw new RuntimeException("Spurious serialisation error", e);
                } finally {
                    oin.close();
                    
                }
}
        public static void encryptwithRSA(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithRSA(key, Cipher.ENCRYPT_MODE, is, os);
	}
 
	public static void decryptwithRSA(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecryptwithRSA(key, Cipher.DECRYPT_MODE, is, os);
	}

	public static void encryptOrDecryptwithRSA(String key, int mode, InputStream is, OutputStream os) throws Throwable {
                ///
                // Lấy key từ file chứa pucblic key để mã hóa
//		X509EncodedKeySpec dks = new X509EncodedKeySpec(key.getBytes()); 
//		KeyFactory skf = KeyFactory.getInstance("RSA");
//		PublicKey rsaPublicKey = skf.generatePublic(dks);
//                //////
//                // Lấy key từ file chứa private key để giải mã
//                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key.getBytes());
//                KeyFactory factory = KeyFactory.getInstance("RSA");
//		PrivateKey rsaPrivateKey = factory.generatePrivate(spec);
//		Cipher cipher = Cipher.getInstance("RSA"); 
                //////// 
                // Chọn chế độ giải mã hoặc mã hóa
                Key rsaPublicKey = readKeyFromFile(key);
                Key rsaPrivateKey = readKeyFromFile(key);
                Cipher cipher = Cipher.getInstance("RSA"); 
		if (mode == Cipher.ENCRYPT_MODE) {                   
			cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);                    
			CipherInputStream cis = new CipherInputStream(is, cipher);
			DES.doCopy(cis, os); // Gọi hàm doCopy để write to file
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			DES.doCopy(is, cos);
		}
	}

}
