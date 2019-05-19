package com.prg3.mr_bid.persistence;

import java.util.Locale;
import java.util.ResourceBundle;


public class FileOperations {
	/**
	 * @author Luis!
	 * @since 19-05-2019 
	 * 
	 * The FileOperations class contains all the methods related to 
	 * the persistence of the program 
	 */
	public FileOperations(String path) {

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
}
