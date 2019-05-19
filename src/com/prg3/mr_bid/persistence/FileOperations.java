package com.prg3.mr_bid.persistence;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class FileOperations {
	/**
	 * @author Luis!
	 * @throws IOException 
	 * 
	 */
	public FileOperations(String path) throws IOException {

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
