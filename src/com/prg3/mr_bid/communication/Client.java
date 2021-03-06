package com.prg3.mr_bid.communication;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import com.prg3.mr_bid.controller.ServerController;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
import com.prg3.mr_bid.utilities.Constants;
import com.prg3.mr_bid.utilities.Utilities;

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
	 * Al crear una subasta, el cliente envia la imagen usando un BufferedImage
	 * @param numImgs
	 * @param bidId
	 * @returnString con la ruta
	 * @throws IOException
	 */
	public String getImage(long bidId) throws IOException {
		String biddingsPath = "";
		BufferedImage bufferedImage;
		bufferedImage = ImageIO.read(socket.getInputStream());
		if(bufferedImage!=null) {
			String imagePath = "data/biddingImages/bidding"+bidId+".png";
			FileOperations.getInstanceOf().saveImage(imagePath, bufferedImage);
			bufferedImage.flush();
			biddingsPath= imagePath;
		}
		return biddingsPath;
	}
	
	/**
	 * Envia las imagenes de una determinada subasta identidicada por su id
	 * @throws IOException exception
	 */
	public void sendImages(long bidId) throws IOException {
		String path = FileOperations.getInstanceOf().
				getBiddingsList().get((int) bidId-1).getProduct().getImage();
		sendData(Commands.GETIMG, bidId);
		BufferedImage bufferedImage = ImageIO.read(new File(path));
		ImageIO.write(bufferedImage, "png", dataOS);
		this.dataOS.flush();
	}
	
	/**
	 * Recibe y evalua las peticiones de los clientes
	 * @param c objeto Commands con comando de peticion
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
				SimpleList<Bidding> biddings = FileOperations.getInstanceOf().getBiddingsList();
				this.sendData(Commands.UPDATE_BID, Utilities.biddingsToString(biddings));
			}
			break;
		case UPDATE_BID:
			SimpleList<Bidding> biddings = FileOperations.getInstanceOf().getBiddingsList();
			this.sendData(Commands.UPDATE_BID, Utilities.biddingsToString(biddings));
			break;	
		case SENDIMG:
			
			break;
		case GETIMG:
			SimpleList<Bidding> idBid = FileOperations.getInstanceOf().getBiddingsList();
			Cursor<Bidding> cursor = new Cursor<>(idBid);
			while(!cursor.isOut()) {
				this.sendImages(cursor.nextAndGetInfo().getId());
			}
			break;
		case NEWOFFER:
			String[] datas = g.split("-");
			Bidding updateBid = FileOperations.getInstanceOf().getBiddingById(Long.parseLong(datas[0]));
			updateBid.setValue(Integer.parseInt(datas[1]));
			SimpleList<Bidding> bids = FileOperations.getInstanceOf().getBiddingsList();
			bids.set((int)(updateBid.getId()-1), updateBid);
			FileOperations.getInstanceOf().updateBiddings(bids);
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
		if (o.getClass().equals(String.class)) {
			dataOS.writeUTF(o.toString());
		} else {
			dataOS.writeUTF(Constants.gson.toJson(o));
		}
	}
	
	/**
	 * Obtiene el sockect del cliente
	 * @return socket s
	 */
	public Socket getSocket() {
		return socket;
	}

}
