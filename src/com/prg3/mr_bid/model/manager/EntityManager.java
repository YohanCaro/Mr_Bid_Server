package com.prg3.mr_bid.model.manager;

import java.io.IOException;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * Manager Class of the entities
 *
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
	 * Constructor of the EntityManager class
	 */
	public EntityManager() {
		users = new SimpleList<>();
		biddings = new SimpleList<>();
		fileOperations = FileOperations.getInstanceOf();
		cursorBiddings = new Cursor<>(biddings);
		
	}
	
	/**
	 * Adds a new user into the users list and write it in the users file
	 * @param user The user object to be added
	 */
	public void addUser(User user) {
		try {
			users.add(user);
			fileOperations.addUser(user);
			System.out.println(user.toString() + "\n se ha registardo!");
			this.loadUsers();
		} catch (Exception e) {
			System.out.println("Error al escribir el archivo!");
			e.printStackTrace();
		}
	}
	
	public void deleteUser(User user) {
		users.remove(user);
		try {
			fileOperations.deleteUser(user);
		} catch (IOException e) {
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
		cursorUsers.reset();
		System.out.println("C: " + (cursorUsers.isEmpty()));
		while (!cursorUsers.isOut()) {
			System.out.println("cursorUsers.getInfo().getEmail() / " + email);
//			if (cursorUsers.nextAndGetInfo().getEmail().equals(email)) {
			if (cursorUsers.getInfo().getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Carga los usuarios 
	 */
	public void loadUsers() {
		try {
			this.users = fileOperations.getUsersList();
			cursorUsers = new Cursor<>(users);
		} catch (IOException e) {
			System.out.println("Error al cargar los usuarios!");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * Busca un usuario por el correo
	 * @param email correo
	 * @return user
	 */
	public User searchUser(String email) {
		System.out.println("search: " + cursorUsers.size() + " - " + users.size());
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

	public void addBidding(Bidding bidding) {
		bidding.setId(biddings.size() + 1);
		biddings.add(bidding);
		try {
			fileOperations.addBidding(bidding);
		} catch (Exception e) {
			System.out.println("Error en escritura de archivo");
		}
	}
	
	public void deleteBidding(Bidding bidding) {
		biddings.remove(bidding);
		try {
			fileOperations.deleteBidding(bidding);
		} catch (IOException e) {
			System.out.println("Error en escritura de archivo");
		}
	}
	
	public boolean existsBidding(long id) {
		while (!cursorBiddings.isOut()) {
			if(cursorBiddings.nextAndGetInfo().getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void loadBiddings() {
		try {
			this.biddings = fileOperations.getBiddingsList();
		} catch (IOException e) {
			System.out.println("Error en lectura de archivo");
			e.printStackTrace();
		}
	}
	
	public Bidding getBidding(long id) {
		Bidding bidding = cursorBiddings.nextAndGetInfo();
		
		while (!cursorBiddings.isOut()) {
			if(bidding.getId() == id) {
				bidding = cursorBiddings.nextAndGetInfo();
			}
		}
		return bidding;
	}
	
	public SimpleList<User> getUsers() {
		return users;
	}

	public SimpleList<Bidding> getBiddings() {
		return biddings;
	}
}
