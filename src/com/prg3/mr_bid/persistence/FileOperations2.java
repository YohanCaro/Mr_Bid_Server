package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
import com.prg3.mr_bid.utilities.Constants;

public class FileOperations2 {
	private BiddingPersistence2 biddingPersistence;
	private UsersPersistence2 usersPersistence;
	private static FileOperations2 fileOperations2 = null;
	
	private FileOperations2() throws FileNotFoundException {
		biddingPersistence = new BiddingPersistence2(Constants.biddingsFilePath, 
				Constants.indexBiddingsPath);
		usersPersistence = new UsersPersistence2(Constants.usersFilePath,
				Constants.indexUsersPath);		
	}
	
	/**
	 * Metodo de patron de diseño singleton de la clase FileOperation
	 * @return una referencia estatica de la clase FileOperation
	 * @throws FileNotFoundException los archivos no se encuentran en la ruta especificada
	 */
	public static FileOperations2 getInstanceOf() throws FileNotFoundException {
		return (fileOperations2==null)?fileOperations2 = new FileOperations2():fileOperations2;
	}
	
	public void addUser(User user) throws IOException {
		usersPersistence.addNewUser(user);
	}
	
	public void deleteUser(User user) {
		usersPersistence.deleteUser(user);
	}
	
	public User getUserByEmail(String email) {
		return usersPersistence.getUserByEmail(email);
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
	
	public void updateBidding(Bidding bidding) throws IOException {
		biddingPersistence.updateBidding(bidding);
	}
	
	public Bidding getBiddingById(long id) {
		return biddingPersistence.getBiddingById(id);
	}
	
	public SimpleList<Bidding> getBiddingsList() throws IOException{
		return biddingPersistence.getAllBiddings();
	}
	
	
}
