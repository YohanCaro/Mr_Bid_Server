package com.prg3.mr_bid.model.entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.prg3.mr_bid.utilities.Utilities;

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
	 * 20 char name = 21 bytes
	 * 20 char description = 20 bytes
	 * 40 char imagePath = 40 bytes 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] bytes = new byte[81];
		String data = Utilities.completeLength(nameProduct, 20);
		bytes = Utilities.completeBytes(bytes, data.getBytes("UTF-8"), 0);
		data = Utilities.completeLength(description, 21);
		bytes = Utilities.completeBytes(bytes, data.getBytes("UTF-8"), 20);
		data = Utilities.completeLength(description, 40);
		bytes = Utilities.completeBytes(bytes, data.getBytes("UTF-8"), 40);
		return bytes;
	}
	
}
