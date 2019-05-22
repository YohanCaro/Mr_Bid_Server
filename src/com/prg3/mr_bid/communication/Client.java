package com.prg3.mr_bid.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.utilities.Constants;

public class Client implements Runnable {
	
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
				isConect = false;
				e.printStackTrace();
			}
		}
	}
	
	public void reciveRequest(Commands c, String g) throws IOException {
		System.out.println(g);
		switch (c) {
		case LOGIN:
			Constants.user = Constants.gson.fromJson(g, User.class);
			Constants.user.setCreditCard(new CreditCard(new BidDate(10, 10, 2010), "Juan", "123", "321"));
			this.sendData(c, Constants.user);
			break;
		case SIGNIN:
			Constants.product = Constants.gson.fromJson(g, Product.class);
			this.sendData(c, Constants.product);
			break;
		case UPBIDDING:
			
			break;
		default:
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
