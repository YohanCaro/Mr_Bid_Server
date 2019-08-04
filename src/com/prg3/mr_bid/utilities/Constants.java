package com.prg3.mr_bid.utilities;

import com.google.gson.Gson;

/**
 * Clase Constants - Constantes
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Constants {
	
	public static Gson gson = new Gson();//borrar ...
	/**
	 * Ruta de archivo contenedor de la información de los usuarios registrados
	 */
	public static String usersFilePath = "data/appData/usersData.DAT";
	/**
	 * Ruta de archivo contenedor de la información de las subastas creadas en la aplicación
	 */
	public static String biddingsFilePath = "data/appData/bidsData.DAT";
	/**
	 * Ruta de archivo contenedor de indices que alimentan al arbol bst de usuarios
	 */
	public static String indexUsersPath = "data/bstApp/bstUsers.DAT";
	/**
	 * Ruta de archivo contenedor de indices que alimentan al arbol bst de subastas
	 */
	public static String indexBiddingsPath = "data/bstApp/bstBiddings.DAT";
}
