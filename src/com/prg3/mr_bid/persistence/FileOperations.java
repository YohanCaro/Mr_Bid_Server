package com.prg3.mr_bid.persistence;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
import com.prg3.mr_bid.utilities.Constants;

/**
 * Clase que maneja la persistencia de los objetos Bidding y User, 
 * para alimentar correctamente la aplicación 
 * @author Luis!
 * @since 2/08/2019 v1.0
 */
public class FileOperations {
	private BiddingPersistence biddingPersistence;
	private UsersPersistence usersPersistence;
	private static FileOperations fileOperations2 = null;
	
	/**
	 * Constructor privado de la clase FileOperations
	 * @throws FileNotFoundException si no encuentra alguno de los 4 archivos requeridos
	 */
	private FileOperations() throws FileNotFoundException {
		biddingPersistence = new BiddingPersistence(Constants.biddingsFilePath, 
				Constants.indexBiddingsPath);
		usersPersistence = new UsersPersistence(Constants.usersFilePath,
				Constants.indexUsersPath);		
	}
	
	/**
	 * Metodo de patron de diseño singleton de la clase FileOperation
	 * @return una referencia estatica de la clase FileOperation
	 * @throws FileNotFoundException los archivos no se encuentran en la ruta especificada
	 */
	public static FileOperations getInstanceOf() throws FileNotFoundException {
		return (fileOperations2==null)?fileOperations2 = new FileOperations():fileOperations2;
	}
	
	/**
	 * Añade un objeto User a la persistencia
	 * @param user objeto User
	 * @throws IOException error en archivo
	 */
	public void addUser(User user) throws IOException {
		usersPersistence.addNewUser(user);
	}
	
	/**
	 * Elimina un usuario de la persistencia
	 * @param user objeto User
	 */
	public void deleteUser(User user) {
		usersPersistence.deleteUser(user);
	}
	
	/**
	 * Obtiene un usuario por su email
	 * @param email email del usuario buscado
	 * @return objeto User
	 */
	public User getUserByEmail(String email) {
		return usersPersistence.getUserByEmail(email);
	}
	
	/**
	 * Obtiene un listado de usuarios almacenados en persistencia
	 * @return lista simple de objetos User
	 * @throws Exception error archivo de subastas
	 */
	public SimpleList<User> getUsersList() throws Exception {
		return usersPersistence.getAllUsers();
	}
	
	/**
	 * Añade un objeto Bidding a la persistencia
	 * @param bidding objeto Bidding a agregar
	 * @throws Exception error archivo de subastas
	 */
	public void addBidding(Bidding bidding) throws Exception {
		biddingPersistence.addNewBidding(bidding);
	}
	
	/**
	 * Elimina una subasta de la persistencia
	 * @param bidding objeto Bidding a eliminar
	 * @throws IOException error archivo de subastas
	 */
	public void deleteBidding(Bidding bidding) throws IOException {
		biddingPersistence.deleteBidding(bidding);
	}
	
	/**
	 * Actualiza las subastas de la persistencia con una lista actualizada de subastas
	 * @param biddings lista simple de objetos Bidding
	 * @throws IOException error archivo subastas
	 */
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		biddingPersistence.updateBiddings(biddings);
	}
	
	/**
	 * Actualiza el contenido de una subasta ya existente en la persistencia
	 * @param bidding objeto Bidding a actualizar
	 * @throws IOException error archivo subastas
	 */
	public void updateBidding(Bidding bidding) throws IOException {
		biddingPersistence.updateBidding(bidding);
	}
	
	/**
	 * Obtiene una subasta por su identificador
	 * @param id identificador del objeto Bidding
	 * @return objeto Bidding
	 */
	public Bidding getBiddingById(long id) {
		return biddingPersistence.getBiddingById(id);
	}
	
	/**
	 * Devuelve una lista de objetos subasta de la persistencia
	 * @return lista simple de objetos Bidding
	 * @throws IOException error archivo subastas
	 */
	public SimpleList<Bidding> getBiddingsList() throws IOException{
		return biddingPersistence.getAllBiddings();
	}

	/**
	 * Guarda una imagen en el directorio especificado
	 * @param path ruta donde guarda la imagen
	 * @param bufferedImage objeto buffer para escritura de imagenes
	 * @throws FileNotFoundException error con archivo
	 * @throws IOException
	 */
	public void saveImage(String path, BufferedImage bufferedImage) throws FileNotFoundException, IOException {
		ImageIO.write(bufferedImage, "png", new FileOutputStream(path));
	}
	
}
