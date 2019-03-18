
package Algorithrm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class main {
        public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
            String algorithm;
            Scanner scanner = new Scanner(System.in);        
            System.out.println("Nhập tên giải thuật: ");
            algorithm = scanner.nextLine();
            chooseAlgorithm(algorithm);
        }
        public static void chooseAlgorithm(String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException{
            if (null != algorithm) switch (algorithm) {
                case "DES":
                    
                    break;
                case "RSA":
                    RSA.main();
                    break;
                case "AES":
                    AES.main();
                    break;
                default:
                    break;
            }
        }
}
