package com.prg3.mr_bid.utilities;

public class DataOperations {
	private static DataOperations dataOperations = null;
	
	private DataOperations() {
		
	}
	
	public static DataOperations getInstanceOf() {
		if(dataOperations==null) {
			dataOperations = new DataOperations();
		}
		return dataOperations;
	}
	
	public String completeLenght(String data, int lenght) {
		String dataC = data;
		for (int i = 0; dataC.length()<lenght; i++, dataC+=" ");		
		return dataC;
	}
	
	public String deleteFinalSpaces(String data) {
		String freeSpaces = "";
		for (int i = data.length()-1; i >= 0; i--) {
			if(data.charAt(i)!=' ') {
				freeSpaces+=data.charAt(i);
			}
		}
		return this.invertString(freeSpaces);
	}
	
	private String invertString(String inverted) {
		String normalS = "";
		for (int i = inverted.length()-1; i>0; i--) {
			normalS+=inverted.charAt(i);
		}
		return normalS;
	}
}
