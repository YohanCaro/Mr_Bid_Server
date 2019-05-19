package com.pgr3.mr_bid.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.pgr3.mr_bid.model.entity.BidDate;
import com.pgr3.mr_bid.model.entity.CreditCard;
import com.pgr3.mr_bid.model.entity.Product;
import com.pgr3.mr_bid.model.entity.User;
import com.pgr3.mr_bid.utilities.Constants;

public class Server extends ServerSocket implements Runnable {
	
	private ArrayList<Client> clients;
	private Gson gson;
	private boolean isLive;

	public Server(int port) throws IOException {
		super(port);
		this.isLive = true;
		this.clients = new ArrayList<>();
		gson = new Gson();
	}
	
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (isLive) {
			try {
				clients.add(this.createClient());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Client createClient() throws UnknownHostException, IOException {
		Client c = new Client(this, this.accept());
		c.initClient();
		return c;
	}
	
	public void reciveRequest(Commands c, String g, Client client) throws IOException {
		System.out.println(g);
		switch (c) {
		case LOGIN:
			Constants.user = gson.fromJson(g, User.class);
			Constants.user.setCreditCard(new CreditCard(new BidDate(10, 10, 2010), "Juan", "123", "321"));
			client.sendData(c, Constants.user);
			break;
		case SIGNIN:
			Constants.product = gson.fromJson(g, Product.class);
			client.sendData(c, Constants.product);
			break;
		case UPBIDDING:
			
			break;
		default:
			break;
		}
	}
	
	public void sendData() {
		
	}

}
