package com.prg3.mr_bid.controller;

import java.util.ArrayList;

import com.prg3.mr_bid.model.entity.User;

public class ServerController {
	
	private ArrayList<User> userList;
	private static ServerController controller;
	
	/**
	 * 
	 */
	private ServerController() {
		userList = new ArrayList<>();
	}
	
	public void addUser(User user) {
		userList.add(user);
		System.out.println(user.toString() + "\n se ha registardo!");
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
	
	public static ServerController getInstanceOf() {
		if (controller == null) {
			controller = new ServerController();
		}
		return controller;
	}

}
