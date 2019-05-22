package com.prg3.mr_bid.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.User;

/**
 * @author Luis!
 * @since 19-05-2019 
 * 
 * The FileOperations class contains all the methods related to 
 * the persistence of the program 
 */
public class FileOperations {
	private UsersPersistence usersPersistence;
	private Gson gson;
	public FileOperations(String path) {
		gson = new Gson();
		usersPersistence = new UsersPersistence();
	}
	
	public void addUser(User user) throws Exception {
		usersPersistence.addNewUser(user);
	}
	
	public void deleteUser(User user) throws IOException {
		usersPersistence.deleteUser(user);
	}
	
	public ArrayList<User> getUsersList() throws IOException {
		return usersPersistence.getAllUsers();
	}
	
	/**
	 * Gives the resources of a specific language
	 * @param requestLanguage the language of the request dialogs
	 * @return a resourceBundle object with dialogs in the specific language
	 */
	public ResourceBundle getLanguageResource(String requestLanguage) {
		Locale locale = null;
		switch (requestLanguage) {
		case "Spanish":
			locale = new Locale("es", "CO");
			break;
		case "English":
			locale = new Locale("en","US");
			break;
		case "French":
			locale = new Locale("fr","FR");
			break;
		}
		return ResourceBundle.getBundle("data.languages.dialogs", locale);
	}
	
	public String objectToJson(Object object) {
		return gson.toJson(object);
	}
	
	public Object jsonToObject(String json, Class class1) {
		return gson.fromJson(json, class1);
	}
}
