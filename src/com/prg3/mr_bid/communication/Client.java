package com.prg3.mr_bid.communication;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.prg3.mr_bid.controller.ServerController;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.utilities.Constants;

public class Client implements Runnable {
	
	private User user;
	private Server server;
	private Socket socket;
	private DataInputStream dataIS;
	private DataOutputStream dataOS;
	private boolean isConect;
		
	public Client(Server server, Socket socket) throws UnknownHostException, IOException {
		this.socket = socket;
		this.server = server;
		dataIS = new DataInputStream(this.socket.getInputStream());
		dataOS = new DataOutputStream(this.socket.getOutputStream());
		this.isConect = true;
	}
	
	public void initClient() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Commands command;
		String jsonString;
		while (isConect) {
			try {
				command = Constants.gson.fromJson(this.dataIS.readUTF(), Commands.class);
				jsonString = this.dataIS.readUTF();
				this.reciveRequest(command, jsonString);
			} catch (IOException e) {
				server.getClients().remove(this);
				isConect = false;
			}
		}
	}
	
	//al crear una subasta, el cliente envia el número de imagenes de la subasta, luego
	//envía en un ciclo de n imagenes cada imagen usando un BufferedImage
	
	public ArrayList<String> getImages(int numImgs, long bidId) throws IOException {
		ArrayList<String> biddingsPath = new ArrayList<>();
		BufferedImage bufferedImage;
		for (int i = 0; i < numImgs; i++) {
			bufferedImage = ImageIO.read(socket.getInputStream());
			String imagePath = "data/biddingImages/bidding"+bidId+"_"+i+".png";
			ImageIO.write(bufferedImage,"png", new FileOutputStream(imagePath));
			bufferedImage.flush();
			biddingsPath.add(imagePath);
		}		
		return biddingsPath;
	}
	
	public void reciveRequest(Commands c, String g) throws IOException {
		System.out.println(g);
		switch (c) {
		case LOGIN:
//			Constants.user.setCreditCard(new CreditCard(new BidDate(10, 10, 2010), "Juan", "123", "321"));
			break;
		case SIGNIN:
			User user = Constants.gson.fromJson(g, User.class);
			if (!ServerController.getInstanceOf().existUser(user.getEmail())) {
				ServerController.getInstanceOf().addUser(user);
			} else {
				this.sendData(Commands.ERROR_SINGIN, "Usuario ya registrado");
			}
			break;
		case UPBIDDING:
			
			break;
		}
	}
	
	public void sendCommand(Commands command) throws IOException {
		dataOS.writeUTF(Constants.gson.toJson(command));
	}
	
	public void sendData(Commands command, Object o) throws IOException {
		dataOS.writeUTF(Constants.gson.toJson(command));
		dataOS.writeUTF(Constants.gson.toJson(o));
	}

}
