package org.example.onit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import org.example.onit.encode.AES256Encoder;
import org.w3c.dom.ls.LSOutput;

public class Main {

	private static final String fileToEncode = "C:\\Users\\PlaksunovGV\\Desktop\\univer\\onit\\kursach\\aes\\fileToEncode.txt";
	private static final String fileToDecode = "C:\\Users\\PlaksunovGV\\Desktop\\univer\\onit\\kursach\\aes\\encodedFile.txt";
	private static final String passFile = "C:\\Users\\PlaksunovGV\\Desktop\\univer\\onit\\kursach\\aes\\password.txt";
	private static final String resDec = "C:\\Users\\PlaksunovGV\\Desktop\\univer\\onit\\kursach\\aes\\resultDecode.txt";

	private static boolean encode = false;

	public static void main(String[] args) throws InvalidKeySpecException {
		var password = checkPassword();
		var aes256 = new AES256Encoder(password);
		if (encode) {
			var bytes = getBytesOfFile(fileToEncode);
			var encodedBytes = aes256.makeAes(bytes, Cipher.ENCRYPT_MODE);
			writeResult(encodedBytes, fileToDecode);
			System.out.println("Encoded successfully");
		} else {
			var bytesToDecode = getBytesOfFile(fileToDecode);
			var decodedBytes = aes256.makeAes(bytesToDecode, Cipher.DECRYPT_MODE);
			writeResult(decodedBytes, resDec);
			System.out.println("Decoded successfully");
		}
	}


	private static void writeResult(byte[] bytes, String target) {
		try {
			Files.write(Paths.get(target), bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] getBytesOfFile(String filepath) {
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Paths.get(filepath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}

	private static String checkPassword() {
		String password;
		try {
			var reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter the password: ");
			var pass = reader.readLine();
			password = Files.readString(Paths.get(passFile));
			if(pass.equals(password)){
				return pass;
			} else {
				throw new RuntimeException("Incorrect password");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}