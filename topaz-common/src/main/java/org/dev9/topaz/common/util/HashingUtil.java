package org.dev9.topaz.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class HashingUtil {
    private final static int ITERATIONS = 8192;
    private final static int HASH_BITS = 32 * 8;

    public static String hashPassword(final char[] password, final String salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, Hex.decodeHex(salt.toCharArray()), ITERATIONS, HASH_BITS);
            SecretKey key = skf.generateSecret(spec);
            byte[] result = key.getEncoded();
            return Hex.encodeHexString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException e) {
            throw new RuntimeException(e);
        }
    }

}