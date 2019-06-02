package com.prg3.mr_bid.controller;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.model.manager.Manager;

/**
 * Clase ServerController - Controlador del servidor
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class ServerController {
	
	private static ServerController controller;
	private Manager manager;
	
	/**
	 * Inicia los usuarios ya registrados
	 */
	private ServerController() {
		manager = new Manager();
		this.loadUsers();
	}
	
	/**
	 * A�ade un usuario al archivo y a la lista
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
	 * Muestra los usurios registrados (Se tiene que quitar)
	 * @return out users
	 */
	public String showUsers() {
		return manager.showUsers();
	}
	
	/**
	 * Verifica que los datos del lgueo sean los correctos
	 * @param email correo
	 * @param password contrase�a
	 * @return true/false
	 */
	public boolean loginAccess(String email, String password) {
		User user = this.searchUser(email);
		if (user != null) {
			System.out.println(user.getEmail() + " " + user.getPassword() + " P: " + password);
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
	 * Crea un �nico controlador
	 * @return
	 */
	public static ServerController getInstanceOf() {
		if (controller == null) {
			controller = new ServerController();
		}
		return controller;
	}

}
