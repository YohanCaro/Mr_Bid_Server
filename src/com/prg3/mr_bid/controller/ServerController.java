package com.prg3.mr_bid.controller;

import java.io.FileNotFoundException;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.model.manager.EntityManager;

/**
 * Clase ServerController - Controlador del servidor
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class ServerController {
	
	private static ServerController controller;
	private EntityManager manager;
	
	/**
	 * Inicia los usuarios ya registrados
	 * @throws FileNotFoundException 
	 */
	private ServerController() throws FileNotFoundException {
		manager = new EntityManager();
		this.loadUsers();
		this.loadBiddings();
	}
	
	/**
	 * Añade un usuario al archivo y a la lista
	 * @param user usuario
	 */
	public void addUser(User user) {
		manager.addUser(user);
	}
	
	/**
	 * Analiza si un usuario existe, mediante el correo
	 * @param email correo
	 * @return true/false
	 */
	public boolean existUser(String email) {
		return manager.existUser(email);
	}
	
	/**
	 * Carga los usuarios 
	 */
	public void loadUsers() {
		manager.loadUsers();
	}
	
	/**
	 * Añade una subasta a la persistencia y a memoria en ejecucion
	 * @param bid objeto Bidding
	 */
	public void addBidding(Bidding bid) {
		manager.addBidding(bid);
	}
	
	/**
	 * Carga en memoria todos los objetos Bidding de la persistencia
	 */
	public void loadBiddings() {
		manager.loadBiddings();
	}
	
	/**
	 * Verifica que los datos del lgueo sean los correctos
	 * @param email correo
	 * @param password contraseña
	 * @return true/false
	 */
	public boolean loginAccess(String email, String password) {
		User user = this.searchUser(email);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Busca un usuario por el correo
	 * @param email correo
	 * @return user
	 */
	public User searchUser(String email) {
		return manager.searchUser(email);
	}
	
	/**
	 * Crea un único controlador
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static ServerController getInstanceOf() throws FileNotFoundException {
		if (controller == null) {
			controller = new ServerController();
		}
		return controller;
	}

}
