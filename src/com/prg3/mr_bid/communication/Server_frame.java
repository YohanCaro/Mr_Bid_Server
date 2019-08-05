package com.prg3.mr_bid.communication;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server_frame {
	ArrayList clientOutputStreams;
	ArrayList<String> users;
	int port;

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		PrintWriter client;

		public ClientHandler(Socket clientSocket, PrintWriter user) {
			client = user;
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception ex) {

				System.out.println("Unexpected error... \n");
			}

		}
		
		
		
		@Override
		public void run() {
			String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
			String[] data;

			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Received: " + message + "\n");
					data = message.split(":");

					for (String token : data) {
						System.out.println(token + "\n");
					}
					if (data[2].equals(connect)) {
						tellEveryone((data[0] + ":" + data[1] + ":" + chat));
						userAdd(data[0]);
					} else if (data[2].equals(disconnect)) {
						tellEveryone((data[0] + ":has disconnected." + ":" + chat));
						userRemove(data[0]);
					} else if (data[2].equals(chat)) {
						tellEveryone(message);
					} else {
						System.out.println("No Conditions were met. \n");
					}
				}
			} catch (Exception ex) {
				System.out.println("Lost a connection. \n");
				ex.printStackTrace();
				clientOutputStreams.remove(client);
			}
		}
	}



	@SuppressWarnings("unchecked")

	private void initComponents() {
		b_startActionPerformed();
	}// </editor-fold>//GEN-END:initComponents

	private void b_startActionPerformed() {// GEN-FIRST:event_b_startActionPerformed
		Thread starter = new Thread(new ServerStart());
		starter.start();

		System.out.println("Server started...\n");
	}

	public class ServerStart implements Runnable {
		@Override
		public void run() {
			clientOutputStreams = new ArrayList();
			users = new ArrayList();

			try {
				ServerSocket serverSock = new ServerSocket(2222);

				while (true) {
					Socket clientSock = serverSock.accept();
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
					clientOutputStreams.add(writer);

					Thread listener = new Thread(new ClientHandler(clientSock, writer));
					listener.start();
					System.out.println("Got a connection. \n");
				}
			} catch (Exception ex) {
				System.out.println("Error making a connection. \n");
			}
		}
	}

	public void userAdd(String data) {
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		System.out.println("Before " + name + " added. \n");
		users.add(name);
		System.out.println("After " + name + " added. \n");
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);

		for (String token : tempList) {
			message = (token + add);
			tellEveryone(message);
		}
		tellEveryone(done);
	}

	public void userRemove(String data) {
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		users.remove(name);
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);

		for (String token : tempList) {
			message = (token + add);
			tellEveryone(message);
		}
		tellEveryone(done);
	}

	public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				System.out.println("Sending: " + message + "\n");
				writer.flush();
				// ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

			} catch (Exception ex) {
				System.out.println("Error telling everyone. \n");
			}
		}
	}

	public Server_frame() {
		initComponents();
		// TODO Auto-generated constructor stub
	}
	public int getPort() {
		return port;
	}

}
