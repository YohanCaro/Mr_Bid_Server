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
	 * 20 char name = 40 bytes
	 * 20 char description = 40 bytes
	 * 20 char imagePath = 40 bytes 
	 * @return
	 */
	public byte[] getBytes() {
		byte[] productBytes = new byte[120];
		String data = nameProduct;
		if(data.length()<20) {
			data = DataOperations.getInstanceOf().completeLenght(data, 20);
		}
		byte[] tempBytes = data.getBytes();
		for (int i = 0; i < tempBytes.length && i<40; i++) {
			productBytes[i] = tempBytes[i];
		}
		System.out.println(data+"|nombre"+"Tamaño:"+data.length());
		System.out.println("ya añadió el nombre... ");
		this.printArray(tempBytes);
		data = this.description;
		if(data.length()<20) {
			data = DataOperations.getInstanceOf().completeLenght(data, 20);
		}
		tempBytes = data.getBytes();
		for (int i = 40; i-40 < tempBytes.length && i<80; i++) {
			productBytes[i] = tempBytes[i-40];
		}
		System.out.println(data+"|descp"+"Tamaño:"+data.length());
		System.out.println("ya añadió la descripción... ");
		this.printArray(tempBytes);
		data = this.image;
		if(data.length()<20) {
			data = DataOperations.getInstanceOf().completeLenght(data, 20);
		}
		tempBytes = data.getBytes();
		for (int i = 80;i-80<tempBytes.length&&i<120; i++) {
			productBytes[i] = tempBytes[i-80];
		}	
		System.out.println(data+"|imagen"+"Tamaño:"+data.length());
		System.out.println("ya añadió la ruta img... ");
		this.printArray(tempBytes);
		return productBytes;
	}
	
	private void printArray(byte[] bytes) {
		System.out.println("tamaño "+bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]+" ");
		}
		System.out.println();
	}
	
	public Product getByBytes(byte[] bytes) throws UnsupportedEncodingException {
		Product product = null;
		byte[] nameBytes = new byte[40];
		byte[] descBytes = new byte[40];
		byte[] imgBytes = new byte[40];
		String name = new String(nameBytes, "UTF-8");
		name = DataOperations.getInstanceOf().deleteFinalSpaces(name);
		String desc = new String(descBytes, "UTF-8");
		desc = DataOperations.getInstanceOf().deleteFinalSpaces(desc);
		String img = new String(imgBytes, "UTF-8");
		img = DataOperations.getInstanceOf().deleteFinalSpaces(img);
		product = new Product(name, desc, img);
		return product;
	}
	
}
