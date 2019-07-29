package com.prg3.mr_bid.utilities;

import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.model.entity.Product;

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
		while( dataC.length()<lenght) {
			dataC+=" ";
		}	
		return dataC;
	}
	
	public String deleteFinalSpaces(String data) {
		String freeSpaces = "";
		boolean finalSpace = false;
		for (int i = data.length()-1; i >= 0; i--) {
			if(data.charAt(i)!=' ') {
				finalSpace=true;
			}
			if(finalSpace) {
				freeSpaces+=data.charAt(i);
			}
		}
		return this.invertString(freeSpaces);
	}
	
	private String invertString(String inverted) {
		String normalS = "";
		for (int i = inverted.length()-1; i>=0; i--) {
			normalS+=inverted.charAt(i);
		}
		return normalS;
	}
	
	public Product getProductByBytes(byte[] bytes) throws UnsupportedEncodingException {
		Product product = null;
		byte[] nameBytes = new byte[20];
		for (int i = 0; i < nameBytes.length; i++) {
			nameBytes[i] = bytes[i];
		}
		byte[] descBytes = new byte[20];
		for (int i = 0; i < descBytes.length; i++) {
			descBytes[i] = bytes[i+20];
		}
		byte[] imgBytes = new byte[40];
		for (int i = 0; i < imgBytes.length; i++) {
			imgBytes[i] = bytes[i+40];
		}
		String name = new String(nameBytes, "UTF-8");
		name = deleteFinalSpaces(name);
		String desc = new String(descBytes, "UTF-8");
		desc = deleteFinalSpaces(desc);
		String img = new String(imgBytes, "UTF-8");
		img = deleteFinalSpaces(img);
		product = new Product(name, desc, img);
		return product;
	}
}
