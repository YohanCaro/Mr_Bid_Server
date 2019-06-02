package com.prg3.mr_bid.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;

/**
 * Clase ServerController - Controlador del servidor
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class ServerController {
	
	private ArrayList<User> userList;
	private static ServerController controller;
	private FileOperations fileOperations;
	
	/**
	 * Inicia los usuarios ya registrados
	 */
	private ServerController() {
		userList = new ArrayList<>();
		fileOperations = FileOperations.getInstanceOf();
		this.loadUsers();
	}
	
	/**
	 * Añade un usuario al archivo y a la lista
	 * @param user usuario
	 */
	public void addUser(User user) {
		try {
			userList.add(user);
			fileOperations.addUser(user);
			System.out.println(user.toString() + "\n se ha registardo!");
		} catch (Exception e) {
			System.out.println("Error al escribir el archivo!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Analiza si un usuario existe, mediante el correo
	 * @param email correo
	 * @return true/false
	 */
	public boolean existUser(String email) {
		if (!userList.isEmpty()) {
			for (User user : userList) {
				if (user.getEmail().equals(email)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Carga los usuarios 
	 */
	public void loadUsers() {
		try {
			this.userList = fileOperations.getUsersList();
			System.out.println("User:\n"+ this.showUsers());
		} catch (IOException e) {
			System.out.println("Error al cargar los usuarios!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Muestra los usurios registrados (Se tiene que quitar)
	 * @return out users
	 */
	public String showUsers() {
		String out = "";
		for (User user : userList) {
			out += user.toString() + "\n";
		}
		return out;
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
		for (User user : userList) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Crea un único controlador
	 * @return
	 */
	public static ServerController getInstanceOf() {
		if (controller == null) {
			controller = new ServerController();
		}
		return controller;
	}

}
