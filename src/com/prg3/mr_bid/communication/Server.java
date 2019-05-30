package com.prg3.mr_bid.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.utilities.Constants;

public class Server extends ServerSocket implements Runnable {
	
	private ArrayList<Client> clients;
	private boolean isLive;

	public Server(int port) throws IOException {
		super(port);
		this.isLive = true;
		this.clients = new ArrayList<>();
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
	
}
