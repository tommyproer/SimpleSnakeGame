package com.snake.main.handler;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import com.snake.main.exception.CryptoException;

/**
 * Handles writing and retrieving high score of the game through a file.
 */
public class HighscoreHandler {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private static final String KEY = "AIELCXDFSWOVIDKS";

    public static void writeHighscore(int highscore, String key) throws CryptoException {
        if (!key.equals(KEY)) {
            return;
        }

        try {
            File outputFile = new File("SimpleSnakeGame-hs-encry");
            outputFile.createNewFile();

            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] outputBytes = cipher.doFinal(Integer.toString(highscore).getBytes());

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static int getHighscore(String key) throws CryptoException {
        if (!key.equals(KEY)) {
            return Integer.MAX_VALUE;
        }

        try {
            File inputFile = new File("SimpleSnakeGame-hs-encry");
            if (!inputFile.exists()) {
                return 0;
            }

            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            inputStream.close();
            return Integer.parseInt(new String(outputBytes));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
