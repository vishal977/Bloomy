package org.purple.bloomy.core.hashing;

import org.purple.bloomy.core.exception.BloomyHashingException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5HashingService implements BloomyHashingService {

    public static final String MD_5 = "MD5";

    @Override
    public int getHash(String plainText) throws BloomyHashingException {
        try {
            MessageDigest md = MessageDigest.getInstance(MD_5);
            byte[] bytes = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
            return byteArrayToInt(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new BloomyHashingException("Unable to generate hash", e);
        }
    }

    private static int byteArrayToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4 && i < bytes.length; i++) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }
}
