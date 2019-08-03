package com.prg3.mr_bid.model.entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.prg3.mr_bid.utilities.DataOperations;

/**
 * Clase Product - Crea un producto para subastar
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Product {
	
	private String nameProduct;
	private String description;
	private String image;
	
	/**
	 * Crea un producto con los siguientes datos
	 * @param nameProduct nombre del producto
	 * @param description descripción del producto
	 * @param image imagenes del producto
	 */
	public Product(String nameProduct, String description, String image) {
		this.nameProduct = nameProduct;
		this.description = description;
		this.image = image;
	}

	/**
	 * Obtiene le nombre del producto
	 * @return nameProduct nombre
	 */
	public String getNameProduct() {
		return nameProduct;
	}

	/**
	 * Obtiene la descripción del producto
	 * @return description descrición
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Obtiene unalista con la ruta de las imagenes
	 * @return images imagenes
	 */
	public String getImage() {
		return image;
	}

	public void setImage(String path) {
		this.image = path;		
	}
	
	/**
	 * 20 char name = 20 bytes
	 * 20 char description = 20 bytes
	 * 40 char imagePath = 40 bytes 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] productBytes = new byte[80];
		String data = nameProduct;
		if(data.length()<20) {
			data = DataOperations.getInstanceOf().completeLenght(data, 20);
		}
		byte[] tempBytes = data.getBytes("UTF-8");
		for (int i = 0; i < tempBytes.length && i<20; i++) {
			productBytes[i] = tempBytes[i];
		}
		data = this.description;
		if(data.length()<20) {
			data = DataOperations.getInstanceOf().completeLenght(data, 20);
		}
		tempBytes = data.getBytes("UTF-8");
		for (int i = 20; i-20 < tempBytes.length && i<40; i++) {
			productBytes[i] = tempBytes[i-20];
		}
		data = this.image;
		if(data.length()<40) {
			data = DataOperations.getInstanceOf().completeLenght(data, 40);
		}
		tempBytes = data.getBytes("UTF-8");
		for (int i = 40;i-40<tempBytes.length&&i<80; i++) {
			productBytes[i] = tempBytes[i-40];
		}	
		return productBytes;
	}
	
}
