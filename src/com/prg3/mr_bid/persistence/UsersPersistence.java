package com.prg3.mr_bid.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
/**
 * Class of the users persistence
 * @author Luis!
 * @version 1.0 - 26/05/2019
 */
public class UsersPersistence {
	private File file;
	private Gson gson;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	/**
	 * Constructor of the userPersistence
	 * @param gson a gson object to manage all the object conversion
	 */
	public UsersPersistence(Gson gson) {
		file = new File("data/appData/usersData.json");		
		this.gson = gson;
	}
	
	/**
	 * Writes a new user into the users file (converted to json structure)
	 * @param user a user object to be written
	 * @throws Exception file exception
	 */
	public void addNewUser(User user) throws Exception {
		openFile('w',true);
		user.setPassword(this.encryptPasswd(user.getPassword()));
		bufferedWriter.write(gson.toJson(user));
		bufferedWriter.newLine();
		closeFile('w');
	}
	
	/**
	 * Deletes an user to the users file
	 * @param user the user to be deleted
	 * @throws IOException
	 */
	public void deleteUser(User user) throws IOException {
		openFile('r',true);
		boolean finded=false;
		String line = "";
		int numLine=0;
		while(!finded&&(line = bufferedReader.readLine())!=null) {
			if(gson.fromJson(line, User.class).getFirstName().equals(user.getFirstName())) {
				finded=true;
			}else 
				numLine++;
		}
		deleteLine(numLine);
		closeFile('r');
	}
	
	/**
	 * Gets all the users into an array list
	 * @return an array list of the users into the users file
	 * @throws Exception file exception/cipher exception
	 */
	public SimpleList<User> getAllUsers() throws Exception{
		SimpleList<User> users = new SimpleList<User>();
		openFile('r', true);
		String line = "";
		while((line = bufferedReader.readLine())!=null) {
			User user = gson.fromJson(line, User.class);
			user.setPassword(decrypt(user.getPassword()));
			users.add(user);
		}		
		closeFile('r');
		return users;
	}
	
	/**
	 * Receives the email of an user. Next to this, the email is
	 * seeked into the users data. If the user is founded, return an user object with the 
	 * request info; in another case, return a null object
	 * @param email The email corresponding to an user
	 * @return the user object corresponding to the required user
	 * @throws Exception It's activated when the file can't be founded
	 */
	public User getUserByEmail(String email) throws Exception {
		User user = null;
		openFile('r', true);
		String line = "";
		while(user==null&&(line = bufferedReader.readLine())!=null) {
			String[] splits = line.split("\"");
			if(splits[12].equals(email)) {
				user = gson.fromJson(line, User.class);
				user.setPassword(decrypt(user.getPassword()));
			}
		}
		closeFile('r');			
		return user;
	}
	
	/**
	 * Receives the full name of an user (firstName+" "+lastName). Next to this, the name is
	 * seeked into the users data. If the user is founded, return an user object with the 
	 * request info; in another case, return a null object
	 * @param fullName the first name and the last name of an user splited by a space character
	 * @return the user object corresponding to the required user
	 * @throws Exception It's activated when the file can't be founded
	 */
	public User getUserByFullName(String fullName) throws Exception {
		User user = null;
		openFile('r', true);
		String line = "";
		while(user==null&&(line = bufferedReader.readLine())!=null) {
			String[] splits = line.split("\"");
			if((splits[3]+" "+splits[7]).equals(fullName)) {
				user = gson.fromJson(line, User.class);
				user.setPassword(decrypt(user.getPassword()));
			}
		}
		closeFile('r');			
		return user;
	}
	
	/**
	 * Deletes a line of the users file
	 * @param indexLine the index of the file line to be deleted
	 * @throws IOException file exception
	 */
	private void deleteLine(int indexLine) throws IOException {
		String dataFile = "";
		String currentLine="";
		int counter = 0;
		openFile('r', true);
		while((currentLine=bufferedReader.readLine())!=null) {
			if(counter!=indexLine) 
				dataFile+=currentLine+"\n";
			counter++;	
		}
		overWriteFile(dataFile);
	}
	
	/**
	 * Overwrites the users file
	 * @param dataFile a string with the new information of the file
	 * @throws IOException file exception
	 */
	private void overWriteFile(String dataFile) throws IOException {
		openFile('w',false);
		String[] lines = dataFile.split("\n");
		for (int i = 0; i < lines.length; i++) { 
			bufferedWriter.write(lines[i]);
			bufferedWriter.newLine();
		}
		closeFile('w');
	}
	
	/**
	 * Prepares the file for the read/write operations
	 * @param mode indicates the mode to open the file (w=write/r=read)
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void openFile(char mode, boolean accumulate) throws IOException {
		switch (mode) {
		case 'w':
			FileWriter fileWriter = new FileWriter(file, accumulate);
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
	
	/**
	 * Encrypt a password to a bytes array
	 * @param password the password to be encrypted
	 * @return a string with a bytes array
	 * @throws Exception
	 */
	private String encryptPasswd(String password) throws Exception {
		final byte[] bytes = password.getBytes("UTF-8");
		final Cipher aes = getCipher(true);
		final byte[] encrypted = aes.doFinal(bytes);
		return Arrays.toString(encrypted);
	}

	/**
	 * Decrypt a encrypted password (in the bytes array structure)
	 * @param encrypted a encrypted password
	 * @return the password in a string
	 * @throws Exception Cipher exception
	 */
	private String decrypt(String encrypted) throws Exception {
		String[] byteValues = encrypted.substring(1, encrypted.length() - 1).split(",");
		byte[] encryptedBytes = new byte[byteValues.length];				
		for (int i=0, len=encryptedBytes.length; i<len; i++) 
			encryptedBytes[i] = Byte.parseByte(byteValues[i].trim());     	
		final Cipher aes = getCipher(false);
		final byte[] bytes = aes.doFinal(encryptedBytes);
		final String password = new String(bytes, "UTF-8");
		return password;
	}

	/**
	 * Gets a cipher object used at the encrypt/decrypt operations
	 * @param operationMode indicates the mode of operation to do
	 * @return a cipher object
	 * @throws Exception A exception of the cipher class
	 */
	private Cipher getCipher(boolean operationMode) throws Exception {
		final String phrase = "\r\n"+"LongPhraseWithDifferentLettersNumbersAndSpecialCharacters_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_!_";
		final MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(phrase.getBytes("UTF-8"));
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
