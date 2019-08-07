package com.prg3.mr_bid.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.prg3.mr_bid.controller.ServerController;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
import com.prg3.mr_bid.utilities.Constants;
import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase Server - Servidor que controla a los usuarios y las subastas
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Server extends ServerSocket implements Runnable {
	
	private SimpleList<Client> clients;
	private boolean isLive;
	ServerChat server_frame;

	/**
	 * Crea el servidor con un puerto definido por el usuario
	 * @param port puerto
	 * @throws IOException ioe
	 */
	public Server(int port) throws IOException {
		super(port);
		this.isLive = true;
		this.clients = new SimpleList<>();
		this.server_frame = new ServerChat();
	}
	
	/**
	 * Inicia la conexión del servidor
	 */
	public void start() {
		new Thread(this).start();
	}

	@Override
	/**
	 * Hilo de recepción de peticiones 
	 */
	public void run() {
		while (isLive) {
			try {
				clients.add(this.createClient());
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * Inicia la conexión con un cliente
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client createClient() throws UnknownHostException, IOException {
		Client c = new Client(this, this.accept());
		c.initClient();
		this.showUsers();
		c.sendData(Commands.UPBIDDING,
				Utilities.biddingsToString(FileOperations.getInstanceOf().getBiddingsList()));
		return c;
	}
	
	/**
	 * Muestra los usuarios conectados
	 */
	public void showUsers() {
		Cursor<Client> cursor = new Cursor<>(clients);
		while (!cursor.isOut()) {
			System.out.println("* " + cursor.nextAndGetInfo().getSocket().getInetAddress().getCanonicalHostName());
		}
	}
	
	/**
	 * Obtiene los clientes
	 * @return clients clientes
	 */
	public SimpleList<Client> getClients() {
		return clients;
	}
	
}
