package com.prg3.mr_bid.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;

public class ServerController {
	
	private ArrayList<User> userList;
	private static ServerController controller;
	private FileOperations fileOperations;
	
	/**
	 * 
	 */
	private ServerController() {
		userList = new ArrayList<>();
		fileOperations = FileOperations.getInstanceOf();
		this.loadUsers();
	}
	
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
	
	public String showUsers() {
		String out = "";
		for (User user : userList) {
			out += user.toString() + "\n";
		}
		return out;
	}
	
	public static ServerController getInstanceOf() {
		if (controller == null) {
			controller = new ServerController();
		}
		return controller;
	}

}
