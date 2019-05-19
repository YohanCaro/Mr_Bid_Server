package com.prg3.mr_bid.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pgr3.mr_bid.model.entity.User;

public class UsersPersistence {
	private File file;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public UsersPersistence() {
		file = new File("data/appData/usersData.json");		
	}
	
	public void addNewUser(User user) throws Exception {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("FirstName", user.getFirstName());
		jsonObject.addProperty("LastName", user.getLastName());
		jsonObject.addProperty("e-mail", user.getEmail());
		jsonObject.addProperty("Password", encryptPasswd(user.getPassword()));
		jsonObject.addProperty("birthDate", user.getBirthDate().toString());
		jsonObject.addProperty("Gender", user.isFemale());
		jsonObject.addProperty("CreditCard", user.getCreditCard().convertToString());
		System.out.println(jsonObject.toString());
		openFile('w');
		bufferedWriter.write(jsonObject.toString());
		bufferedWriter.flush();
		
		closeFile('w');
	}
	
	
	
	/**
	 * Prepares the file for the read/write operations
	 * @param mode indicates the mode to open the file (w=write/r=read)
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void openFile(char mode) throws IOException {
		switch (mode) {
		case 'w':
			FileWriter fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			break;
		case 'r':
			FileReader fileReader = new FileReader(file);		
			bufferedReader = new BufferedReader(fileReader);
			break;
		}
	}
	
	/**
	 * Closes the read/write objects of the file
	 * @param mode indicates the object to be closed (w=write/r=read)
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void closeFile(char mode) throws IOException {
		switch (mode) {
		case 'w':
			bufferedWriter.close();
			break;
		case 'r':		
			bufferedReader.close();
			break;
		}
	}
	
	public String encryptPasswd(String password) throws Exception {
		final byte[] bytes = password.getBytes("UTF-8");
		final Cipher aes = getCipher(true);
		final byte[] encrypted = aes.doFinal(bytes);
		return Arrays.toString(encrypted);
	}

	public String desencrypt(String encrypted) throws Exception {
		String[] byteValues = encrypted.substring(1, encrypted.length() - 1).split(",");
		byte[] encryptedBytes = new byte[byteValues.length];				
		for (int i=0, len=encryptedBytes.length; i<len; i++) 
			encryptedBytes[i] = Byte.parseByte(byteValues[i].trim());     	
		final Cipher aes = getCipher(false);
		final byte[] bytes = aes.doFinal(encryptedBytes);
		final String password = new String(bytes, "UTF-8");
		return password;
	}

	private Cipher getCipher(boolean operationMode) throws Exception {
		final String frase = "\r\n"+"LongPhraseWithDifferentLettersNumbersAndSpecialCharacters_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_!_";
		final MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(frase.getBytes("UTF-8"));
		final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		if (operationMode) {
			aes.init(Cipher.ENCRYPT_MODE, key);
		} else {
			aes.init(Cipher.DECRYPT_MODE, key);
		}

		return aes;
	}
}
