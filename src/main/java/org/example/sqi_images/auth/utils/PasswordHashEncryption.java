package org.example.sqi_images.auth.utils;

import org.example.sqi_images.common.exception.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static org.example.sqi_images.common.constant.Constants.PBKDF2_WITH_SHA1;
import static org.example.sqi_images.common.exception.type.ErrorType.PASSWORD_ENCRYPT_ERROR;

@Component
public class PasswordHashEncryption {

    private final String salt;
    private final int iterationCount;
    private final int keyLength;

    public PasswordHashEncryption(@Value("${encryption.pbkdf2.salt}") final String salt,
                                  @Value("${encryption.pbkdf2.iteration-count}") final int iterationCount,
                                  @Value("${encryption.pbkdf2.key-length}") final int keyLength) {
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
    }

    public String encrypt(final String plainPassword) {
        try {
            KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_WITH_SHA1);
            byte[] encodedPassword = keyFactory.generateSecret(spec)
                    .getEncoded();
            return Base64.getEncoder()
                    .withoutPadding()
                    .encodeToString(encodedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerException(PASSWORD_ENCRYPT_ERROR);
        }
    }

    public boolean matches(final String plainPassword, final String hashedPassword) {
        return encrypt(plainPassword).equals(hashedPassword);
    }
}
