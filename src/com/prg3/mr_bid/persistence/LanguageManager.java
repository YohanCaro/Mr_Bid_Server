package com.prg3.mr_bid.persistence;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
	private Locale locale;
	private ResourceBundle bundle;
	
	private LanguageManager() {
		bundle = ResourceBundle.getBundle("data.languages.dialogs", locale);
	}
	
	
	public static void getInstanceOf() {
		
	}
}
