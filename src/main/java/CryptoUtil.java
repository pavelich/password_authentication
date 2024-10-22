import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;

public class CryptoUtil {

    public static void encrypt(String data, String filePath, String passphrase) throws Exception {
        SecretKey secretKey = generateKey(passphrase);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(encryptedData);
        }
    }

    public static String decrypt(String filePath, String passphrase) throws Exception {
        SecretKey secretKey = generateKey(passphrase);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] fileData = Files.readAllBytes(new File(filePath).toPath());
        byte[] decryptedData = cipher.doFinal(fileData);
        return new String(decryptedData);
    }

    private static SecretKey generateKey(String passphrase) throws Exception {
        byte[] key = new byte[16];
        System.arraycopy(passphrase.getBytes(), 0, key, 0, Math.min(key.length, passphrase.length()));
        return new SecretKeySpec(key, "AES");
    }
}
