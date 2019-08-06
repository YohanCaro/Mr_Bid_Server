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
	
	@Override
	public String toString() {
		return "Product [nameProduct=" + nameProduct + ", description=" + description + ", image=" + image + "]";
	}
	
	public String stringProduct() {
		return nameProduct + "-" + description + "-" + image;
	}

	/**
	 * 20 char name = 21 bytes
	 * 30 char description = 30 bytes
	 * 40 char imagePath = 40 bytes 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] bytes = new byte[91];
		String data = Utilities.completeLength(nameProduct, 21);
		System.out.println(data);
		bytes = Utilities.completeBytes(bytes, Utilities.stringToBytes(data), 0);
		data = Utilities.completeLength(description, 30);
		System.out.println(data);
		bytes = Utilities.completeBytes(bytes, Utilities.stringToBytes(data), 21);
		data = Utilities.completeLength(image, 40);
		System.out.println(data);
		bytes = Utilities.completeBytes(bytes, Utilities.stringToBytes(data), 51);
		return bytes;
	}
	
}
