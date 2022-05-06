package org.example.onit.encode;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Encoder {
	private SecretKey secretKey;

	public AES256Encoder(String password) throws InvalidKeySpecException {
		try {
			secretKey = getKeyFromPassword(password, "salt");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public byte[] makeAes(byte[] rawMessage, int cipherMode){
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipherMode, this.secretKey);
			byte [] output = cipher.doFinal(rawMessage);
			return output;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private SecretKey getKeyFromPassword(String password, String salt)
		throws NoSuchAlgorithmException, InvalidKeySpecException {
		var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		var keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		var originalKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
		return originalKey;
	}
}
