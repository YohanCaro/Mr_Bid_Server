package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.bst_file.BSTFile;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * Clase que maneja la persistencia de los objetos User, usando un arbol BST para esto
 * @author Luis!
 * @since 2/08/2019 v1.0
 */
public class UsersPersistence {
	private BSTFile<User> bstUsers;
	private Comparator<User> uComparator;
	
	/**
	 * Constructor de la clase UserPersistence
	 * @param pathUsersFile ruta del archivo que contiene la información de usuarios registrados
	 * @param indexBSTFile ruta del archivo que contiene los indices de los registros de usuarios para el arbol bst
	 * @throws FileNotFoundException archivo(s) no encontrados
	 */
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
	
	/**
	 * Añade un nuevo objeto User al arbol bst en persistencia
	 * @param user objeto User a añadir
	 * @throws IOException problema en la escritura del archivo
	 */
	public void addNewUser(User user) throws IOException {
		bstUsers.add(user);
	}
	
	/**
	 * Elimina un objeto User del arbol bst en persistencia
	 * @param user objeto User a eliminar
	 */
	public void deleteUser(User user) {
		bstUsers.delete(user);
	}
	
	/**
	 * Obtiene el listado de usuarios almacenados en persistencia
	 * @return lista simple de objetos User
	 */
	public SimpleList<User> getAllUsers() {
		SimpleList<User> usersList = new SimpleList<>(uComparator);
		long tempIndex = 0;
		User user = null;
		do {
			user = bstUsers.getData(tempIndex);
			if(user!=null) {
				usersList.add(user);
			}
			tempIndex++;
		}while(user!=null);
		return usersList;
	}
	
	/**
	 * Realiza busqueda de usuarios ordenados por email
	 * @param email correo electronico del usuario buscado
	 * @return objeto User en caso de que exista
	 */
	public User getUserByEmail(String email) {
		User user = new User("", "", email, "", null, "", null, null, null);
		User finded = bstUsers.getData(bstUsers.search(user));
		return finded;
	}
	
}
