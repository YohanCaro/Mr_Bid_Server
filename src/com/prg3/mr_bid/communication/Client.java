package com.prg3.mr_bid.communication;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.prg3.mr_bid.controller.ServerController;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.utilities.Constants;

/**
 * Clase Client - Maneja las peticiones del servidor
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Client implements Runnable {
	
	private User user;
	private Server server;
	private Socket socket;
	private DataInputStream dataIS;
	private DataOutputStream dataOS;
	private boolean isConect;
		
	/**
	 * Constructor que crea un cliente del servidor
	 * @param server servidor
	 * @param socket socket
	 * @throws UnknownHostException uhe
	 * @throws IOException ioe
	 */
	public Client(Server server, Socket socket) throws UnknownHostException, IOException {
		this.socket = socket;
		this.server = server;
		dataIS = new DataInputStream(this.socket.getInputStream());
		dataOS = new DataOutputStream(this.socket.getOutputStream());
		this.isConect = true;
	}
	
	/**
	 * Inicia el hilo de petiones 
	 */
	public void initClient() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Commands command;
		String jsonString = "";
		while (isConect) {
			try {
				command = Constants.gson.fromJson(this.dataIS.readUTF(), Commands.class);
				try {
					if(!command.equals(Commands.UPDATE_BID)|| !command.equals(Commands.GETIMG)) {
						jsonString = this.dataIS.readUTF();
					}
				} catch (NullPointerException e) {
					
				}	
				this.reciveRequest(command, jsonString);
			} catch (IOException e) {
				server.getClients().remove(this);
				isConect = false;
			}
		}
	}
	

	/**
	 * 	al crear una subasta, el cliente envia el número de imagenes de la subasta, luego
	 * envía en un ciclo de n imagenes cada imagen usando un BufferedImage
	 * @param numImgs
	 * @param bidId
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> getImages(int numImgs, long bidId) throws IOException {
		ArrayList<String> biddingsPath = new ArrayList<>();
		BufferedImage bufferedImage;
		for (int i = 1; i <= numImgs; i++) {
			bufferedImage = ImageIO.read(socket.getInputStream());
			String imagePath = "data/biddingImages/bidding"+bidId+"_"+i+".png";
			FileOperations.getInstanceOf().saveImage(imagePath, bufferedImage);
			bufferedImage.flush();
			biddingsPath.add(imagePath);
		}		
		return biddingsPath;
	}
	
	/**
	 * What should this method do?
	 * @throws IOException 
	 */
	public void sendImages(long bidId) throws IOException {
		ArrayList<String> paths = FileOperations.getInstanceOf().
				getBiddingsList().get((int) bidId).getProduct().getImages();
		sendData(Commands.GETIMG, paths.size()+" "+bidId);
		for (int i = 0; i < paths.size(); i++) {
			BufferedImage bufferedImage = ImageIO.read(new File(paths.get(i)));
			ImageIO.write(bufferedImage, "png", dataOS);
			bufferedImage.flush();
		}
	}
	
	/**
	 * Recibe las peticiones de los clientes
	 * @param c commnads
	 * @param g cadena json
	 * @throws IOException ioe
	 */
	public void reciveRequest(Commands c, String g) throws IOException {
		switch (c) {
		case LOGIN:
			String[] data = g.split(",");
			if (ServerController.getInstanceOf().loginAccess(data[0], data[1])) {
				user = ServerController.getInstanceOf().searchUser(data[0]);
				this.sendData(Commands.ERROR_LOGIN, user);
			} else {
				this.sendData(Commands.ERROR_LOGIN, "false");
			}
			break;
		case SIGNIN:
			User user = Constants.gson.fromJson(g, User.class);
			if (user != null) {
				if (!ServerController.getInstanceOf().existUser(user.getEmail())) {
					ServerController.getInstanceOf().addUser(user);
				} else {
					this.sendData(Commands.ERROR_SINGIN, "Usuario ya registrado");
				}
			} else {
				System.out.println("El usuario ha llegado nulo");
			}
			break;
		case UPBIDDING:
			Bidding b = Constants.gson.fromJson(g, Bidding.class);
			if (b != null) {
				ServerController.getInstanceOf().addBidding(b);
				this.sendData(Commands.UPBIDDING, ServerController.getInstanceOf().getManager().getBiddings());
			}
			break;
		case UPDATE_BID:
			ArrayList<Bidding> biddings = FileOperations.getInstanceOf().getBiddingsList();
			System.out.println(Constants.gson.toJson(biddings));
			this.sendData(Commands.UPDATE_BID, Constants.gson.toJson(biddings));
			break;	
		case SENDIMG:
			String[] datas = g.split(" ");
			long id = Long.parseLong(datas[1]);
			ArrayList<String> paths = getImages(Integer.parseInt(datas[0]), 
					id);
			ArrayList<Bidding> newBiddings = FileOperations.getInstanceOf().getBiddingsList();			
			newBiddings.get((int) id).getProduct().setImages(paths);
			FileOperations.getInstanceOf().updateBiddings(newBiddings);
			break;
		case GETIMG:
			
			break;
		}
	}
	
	/**
	 * Envia un comando al cliente
	 * @param command comando
	 * @throws IOException ioe
	 */
	public void sendCommand(Commands command) throws IOException {
		dataOS.writeUTF(Constants.gson.toJson(command));
	}
	
	/**
	 * Envia un comando y un objeto al cliente
	 * @param command comando
	 * @param o objeto en jsonString
	 * @throws IOException ioe
	 */
	public void sendData(Commands command, Object o) throws IOException {
		dataOS.writeUTF(Constants.gson.toJson(command));
		dataOS.writeUTF(Constants.gson.toJson(o));
	}
	

}
