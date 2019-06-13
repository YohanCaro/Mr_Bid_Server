package com.prg3.mr_bid.model.entity;

import java.util.ArrayList;

/**
 * Clase Product - Crea un producto para subastar
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Product {
	
	private String nameProduct;
	private String description;
	private ArrayList<String> images;
	
	/**
	 * Crea un producto con los siguientes datos
	 * @param nameProduct nombre del producto
	 * @param description descripción del producto
	 * @param images imagenes del producto
	 */
	public Product(String nameProduct, String description, ArrayList<String> images) {
		this.nameProduct = nameProduct;
		this.description = description;
		this.images = images;
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
	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> paths) {
		this.images = paths;		
	}
	
}
