package com.prg3.mr_bid.model.manager;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * Clase manager de las entidades utilizadas en la aplicación
 * @author LUIS MARTINEZ
 * @version 1.0 - 2/06/2019
 */
public class EntityManager {
	private SimpleList<User> users;
	private SimpleList<Bidding> biddings;
	private FileOperations fileOperations;
	private Cursor<User> cursorUsers;
	private Cursor<Bidding> cursorBiddings;
	
	/**
	 * Constructor de la clase EntityManager
	 * @throws FileNotFoundException no se encuentran los archivos de subastas y usuarios 
	 */
	public EntityManager() throws FileNotFoundException {
		users = new SimpleList<>();
		biddings = new SimpleList<>();
		fileOperations = FileOperations.getInstanceOf();
		cursorBiddings = new Cursor<>(biddings);
		cursorUsers = new Cursor<>(users);
	}
	
	/**
	 * Añade un nuevo usuario en la lista de usuarios y lo añade al BST de usuarios en persistencia
	 * @param user El objeto usuario a añadir
	 */
	public void addUser(User user) {
		try {
			users.add(user);
			fileOperations.addUser(user);
			System.out.println(user.toString() + "\n se ha registrado!");
			this.loadUsers();
		} catch (Exception e) {
			System.out.println("Error al escribir el archivo!");
		}
	}
	
	/**
	 * Elimina un usuario de la lista de usuarios y lo elimina del BST de usuarios en persistencia
	 * @param user El objeto usuario a eliminar
	 */
	public void deleteUser(User user) {
		users.remove(user);
		fileOperations.deleteUser(user);
	}
	
	/**
	 * Analiza si un usuario existe mediante el correo
	 * @param email correo del usuario
	 * @return true/false
	 */
	public boolean existUser(String email) {
		cursorUsers.reset();
		while (!cursorUsers.isOut()) {
			if (cursorUsers.nextAndGetInfo().getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Carga los usuarios desde la persistencia
	 */
	public void loadUsers() {
		try {
			this.users = fileOperations.getUsersList();
			cursorUsers = new Cursor<>(users);
		} catch (IOException e) {
			System.out.println("Error al cargar los usuarios!");
		} catch (Exception e) {
		}
	}
	
	/**
	 * Muestra los usurios registrados (Se tiene que quitar)
	 * @return out users
	 */
	public String showUsers() {
		String out = "";
		cursorUsers.reset();
		while (!cursorUsers.isOut()) {
			out += cursorUsers.nextAndGetInfo().toString() + "\n";
		}
		return out;
	}
	
	/**
	 * Busca un usuario por su correo electronico
	 * @param email correo del usuario
	 * @return objeto usuario, en caso de que este sea encontrado
	 */
	public User searchUser(String email) {
		cursorUsers.reset();
		while (!cursorUsers.isOut()) {
			User user = cursorUsers.getInfo();
			if (user.getEmail().equals(email)) {
				return user;
			}
			cursorUsers.next();
		}
		return null;
	}

	/**
	 * Añade una nueva subasta en la lista de subastas y la añade al BST de subastas en persistencia
	 * @param user El objeto subasta a añadir
	 */
	public void addBidding(Bidding bidding) {
		bidding.setId(biddings.size() + 1);
		biddings.add(bidding);
		try {
			fileOperations.addBidding(bidding);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en escritura de archivo");
		}
	}
	
	/**
	 * Elimina una subasta de la lista de subastas y la elimina del BST de subastas en persistencia
	 * @param user El objeto subasta a eliminar
	 */
	public void deleteBidding(Bidding bidding) {
		biddings.remove(bidding);
		try {
			fileOperations.deleteBidding(bidding);
		} catch (IOException e) {
			System.err.println("Error en escritura de archivo");
		}
	}
	
	/**
	 * Analiza si una subasta existe mediante la búsqueda por id
	 * @param id identificador de la subasta
	 * @return true/false
	 */
	public boolean existsBidding(long id) {
		while (!cursorBiddings.isOut()) {
			if(cursorBiddings.nextAndGetInfo().getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Carga las subastas desde la persistencia
	 */
	public void loadBiddings() {
		try {
			this.biddings = fileOperations.getBiddingsList();
		} catch (IOException e) {
			System.out.println("Error en lectura de archivo");
		}
	}
	
	/**
	 * Devuelve la lista de subastas
	 * @return lista simple de objetos Bidding
	 */
	public SimpleList<Bidding> getBiddings() {
		return biddings;
	}
}
