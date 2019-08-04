package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.bst_file.BSTFile;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * 
 * @author Luis!
 *
 */
public class UsersPersistence {
	private BSTFile<User> bstUsers;
	private Comparator<User> uComparator;
	
	public UsersPersistence(String pathUsersFile, String indexBSTFile) throws FileNotFoundException {
		uComparator = new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getEmail().compareTo(o2.getEmail());
			}
		};
		bstUsers = new BSTFile<>(User.class, User.RECORD_SIZE, 
				indexBSTFile , pathUsersFile, uComparator);
	}
	
	public void addNewUser(User user) throws IOException {
		bstUsers.add(user);
	}
	
	public void deleteUser(User user) {
		bstUsers.delete(user);
	}
	
	public SimpleList<User> getAllUsers() {
		SimpleList<User> usersList = new SimpleList<>(uComparator);
		long tempIndex = 0;
		User user = null;
		do {
			user = bstUsers.getData(tempIndex);
			if(user!=null) {
				usersList.add(user);
			}else {
				System.out.println("usuario nulo");
			}
			tempIndex++;
		}while(user!=null);
		return usersList;
	}
	
	public User getUserByEmail(String email) {
		User user = new User("", "", email, "", null, "", null, null, null);
		User finded = bstUsers.getData(bstUsers.search(user));
		return finded;
	}
	
}
