package com.pgr3.mr_bid.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

public class Client implements Runnable {
	
	private Server server;
	private Socket socket;
	private DataInputStream dataIS;
	private DataOutputStream dataOS;
	private Gson gson;
	private boolean isConect;
	
	public Client(Server server, Socket socket) throws UnknownHostException, IOException {
		this.socket = socket;
		this.server = server;
		dataIS = new DataInputStream(this.socket.getInputStream());
		dataOS = new DataOutputStream(this.socket.getOutputStream());
		gson = new Gson();
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
				command = gson.fromJson(this.dataIS.readUTF(), Commands.class);
				jsonString = this.dataIS.readUTF();
				this.server.reciveRequest(command, jsonString, this);
			} catch (IOException e) {
				isConect = false;
				e.printStackTrace();
			}
		}
	}
	
	public void sendCommand(Commands command) throws IOException {
		dataOS.writeUTF(gson.toJson(command));
	}
	
	public void sendData(Commands command, Object o) throws IOException {
		dataOS.writeUTF(gson.toJson(command));
		dataOS.writeUTF(gson.toJson(o));
	}

}
