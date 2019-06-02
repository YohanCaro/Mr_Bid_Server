package com.prg3.mr_bid.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;


/**
 * @author Luis!
 * @version 19-05-2019 
 * 
 * The FileOperations class contains all the methods related to 
 * the persistence of the program 
 */
public class FileOperations {
	private UsersPersistence usersPersistence;
	private BiddingPersistence biddingPersistence;
	private Gson gson;
	private static FileOperations fileOperations = null;
	
	private FileOperations() {
		gson = new Gson();
		usersPersistence = new UsersPersistence(gson);
		biddingPersistence = new BiddingPersistence(gson);
	}
	
	public static FileOperations getInstanceOf() {
		return (fileOperations==null)?fileOperations = new FileOperations():fileOperations;
	}
	
	public void addUser(User user) throws Exception {
		usersPersistence.addNewUser(user);
	}
	
	public void deleteUser(User user) throws IOException {
		usersPersistence.deleteUser(user);
	}
	
	public ArrayList<User> getUsersList() throws IOException {
		return usersPersistence.getAllUsers();
	}
	
	public void addBidding(Bidding bidding) throws Exception {
		biddingPersistence.addNewBidding(bidding);
	}
	
	public void deleteBidding(Bidding bidding) throws IOException {
		biddingPersistence.deleteBidding(bidding);
	}
	
	public void updateBiddings(ArrayList<Bidding> biddings) throws IOException {
		biddingPersistence.updateBiddings(biddings);
	}
	
	public ArrayList<Bidding> getBiddingsList() throws IOException{
		return biddingPersistence.getAllBiddings();
	}
	
	public String objectToJson(Object object) {
		return gson.toJson(object);
	}
	
	/**
	 * Converts a string json into an object of a specific class
	 * @param json
	 * @param class1
	 * @return
	 */
	public Object jsonToObject(String json, Class class1) {
		return gson.fromJson(json, class1);
	}
}
