package com.prg3.mr_bid.communication;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
	ArrayList clientOutputStreams;
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
			String[] data2;
			try {
				while ((message = reader.readLine()) != null) {
					data = message.split(":");
					data2 = message.split("Sala");
					if (data[2].equals(connect)) {
						tellEveryone((data[0] + ":" + data[1] + ":" + chat));
					} else if (data[2].equals(disconnect)) {
						tellEveryone((data[0] + ":has disconnected." + ":" + chat));
					} else if (data[2].equals(chat)) {// aca creo que es donde se manda el mensaje al cliente
						tellEveryone(message);
					} else {
						System.out.println("No Conditions were met. \n");
					}

				}
			} catch (Exception ex) {
				clientOutputStreams.remove(client);
			}
		}
	}

	@SuppressWarnings("unchecked")

	private void initComponents() {
		Thread starter = new Thread(new ServerStart());
		starter.start();
	}

	public class ServerStart implements Runnable {
		@Override
		public void run() {
			clientOutputStreams = new ArrayList();
			try {
				ServerSocket serverSock = new ServerSocket(2222);

				while (true) {
					Socket clientSock = serverSock.accept();
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
					clientOutputStreams.add(writer);
					Thread listener = new Thread(new ClientHandler(clientSock, writer));
					listener.start();
				}
			} catch (Exception ex) {
				System.out.println("Error making a connection. \n");
			}
		}
	}

	/**
	 * Metodo encargado de mandar el em
	 * @param message
	 */
	public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch (Exception ex) {
				System.out.println("Error telling everyone. \n");
			}
		}
	}

	public ServerChat() {
		initComponents();
		// TODO Auto-generated constructor stub
	}

}
