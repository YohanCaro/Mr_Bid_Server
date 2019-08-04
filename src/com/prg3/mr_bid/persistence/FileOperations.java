package com.prg3.mr_bid.persistence;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.simple_list.SimpleList;


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
	
	/**
	 * Private constructor of the FileOperation class without parameters
	 */
	private FileOperations() {
		gson = new Gson();
		usersPersistence = new UsersPersistence(gson);
		biddingPersistence = new BiddingPersistence(gson);
	}
	
	/**
	 * Method of a singleton design pattern of the FileOperation class
	 * @return an static reference of the FileOperation class
	 */
	public static FileOperations getInstanceOf() {
		return (fileOperations==null)?fileOperations = new FileOperations():fileOperations;
	}
	
	public void addUser(User user) throws Exception {
		usersPersistence.addNewUser(user);
	}
	
	public void saveImage(String path, BufferedImage bufferedImage) throws FileNotFoundException, IOException {
		ImageIO.write(bufferedImage, "png", new FileOutputStream(path));
	}
	
	public void deleteUser(User user) throws IOException {
		usersPersistence.deleteUser(user);
	}
	
	public SimpleList<User> getUsersList() throws Exception {
		return usersPersistence.getAllUsers();
	}
	
	public void addBidding(Bidding bidding) throws Exception {
		biddingPersistence.addNewBidding(bidding);
	}
	
	public void deleteBidding(Bidding bidding) throws IOException {
		biddingPersistence.deleteBidding(bidding);
	}
	
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		biddingPersistence.updateBiddings(biddings);
	}
	
	public SimpleList<Bidding> getBiddingsList() throws IOException{
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
