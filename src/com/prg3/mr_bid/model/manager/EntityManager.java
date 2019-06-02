package com.prg3.mr_bid.model.manager;

import java.io.IOException;
import java.util.ArrayList;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;

/**
 * Manager Class of the entities
 *
 * @author LUIS MARTINEZ
 * @version 1.0 - 2/06/2019
 */
public class EntityManager {
	private ArrayList<User> users;
	private ArrayList<Bidding> biddings;
	private FileOperations fileOperations;
	
	/**
	 * Constructor of the EntityManager class
	 */
	public EntityManager() {
		users = new ArrayList<>();
		biddings = new ArrayList<>();
		fileOperations = FileOperations.getInstanceOf();
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
		if (!users.isEmpty()) {
			for (User user : users) {
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
			this.users = fileOperations.getUsersList();
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
		for (User user : users) {
			out += user.toString() + "\n";
		}
		return out;
	}
	
	/**
	 * Busca un usuario por el correo
	 * @param email correo
	 * @return user
	 */
	public User searchUser(String email) {
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	public void addBidding(Bidding bidding) {
		biddings.add(bidding);
		try {
			fileOperations.addBidding(bidding);
		} catch (Exception e) {
			System.out.println("Error en escritura de archivo");
			e.printStackTrace();
		}
	}
	
	public void deleteBidding(Bidding bidding) {
		biddings.remove(bidding);
		try {
			fileOperations.deleteBidding(bidding);
		} catch (IOException e) {
			System.out.println("Error en escritura de archivo");
			e.printStackTrace();
		}
	}
	
	public boolean existsBidding(long id) {
		boolean exists = false;
		for (Bidding bidding : biddings) {
			if(bidding.getId()==id) {
				exists = true;
			}
		}
		return exists;
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
		Bidding bidding = null;
		for (Bidding bidding1 : biddings) {
			if(bidding1.getId()==id) {
				bidding = bidding1;
			}
		}
		return bidding;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Bidding> getBiddings() {
		return biddings;
	}
}
