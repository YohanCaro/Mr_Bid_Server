package com.prg3.mr_bid.communication;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
	ArrayList clientOutputStreams;

	ArrayList<String> users;
	int port;
	private long idSala;

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
					System.out.println("Received: " + message + "\n");
					data = message.split(":");
					data2 = message.split("Sala");

					System.out.println("data" + data2[0]);// iMPORTANTE ACA SE ALAMACENA EL IDENTIFICADOR DE LA SALA DE
															// DONDE SE ESTA MANDANDO EL MENSAJE

					System.out.println("pos1" + data[0]);
					System.out.println("pos2" + data[1]);
					System.out.println("pos3s" + data[2]);
					


					for (String token : data) {
						System.out.println(token + "\n");
					}

					if (data[2].equals(connect)) {
						tellEveryone((data[0] + ":" + data[1] + ":" + chat));
						System.out.println("ESTA CONCETADO" + data[1]);
						userAdd(data[0]);
					} else if (data[2].equals(disconnect)) {
						tellEveryone((data[0] + ":has disconnected." + ":" + chat));
						userRemove(data[0]);
					} else if (data[2].equals(chat)) {// aca creo que es donde se manda el mensaje al cliente
						tellEveryone(message);
						System.out.println("Segunada condicion" + message);
					} else {
						System.out.println("No Conditions were met. \n");
					}
				}
			} catch (Exception ex) {
				System.out.println("Lost a connection chat. \n");
				clientOutputStreams.remove(client);
			}
		}
	}

	@SuppressWarnings("unchecked")

	private void initComponents() {
		b_startActionPerformed();
	}

	private void b_startActionPerformed() {
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
		// System.out.println("it antes del " + it);
//		System.out.println("El identificador de la sale es: "+idSala);

		while (it.hasNext()) {
			System.out.println(it.hasNext());
			try {
				PrintWriter writer = (PrintWriter) it.next();
				System.out.println(writer);
				writer.println(message);
				System.out.println("Sending: " + message + "\n");
				writer.flush();
				// ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

			} catch (Exception ex) {
				System.out.println("Error telling everyone. \n");
			}
		}
	}

	public ServerChat() {
		initComponents();
		// TODO Auto-generated constructor stub
	}

	public int getPort() {
		return port;
	}

}
