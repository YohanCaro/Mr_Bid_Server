package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.util.Comparator;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.bst_file.BSTFile;

public class UsersPersistence2 {
	private BSTFile<User> bstUsers;
	
	public UsersPersistence2(String pathUsersFile, String indexBSTFile) throws FileNotFoundException {
		bstUsers = new BSTFile<>(User.class, User.RECORD_SIZE, 
				indexBSTFile , pathUsersFile, 
				new Comparator<User>() {
					@Override
					public int compare(User o1, User o2) {
						return o1.getEmail().compareTo(o2.getEmail());
					}
				});
	}
	
}
