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
import com.prg3.mr_bid.utilities.Constants;

/**
 * Clase Server - Servidor que controla a los usuarios y las subastas
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Server extends ServerSocket implements Runnable {
	
	private ArrayList<Client> clients;
	private boolean isLive;
	Server_frame server_frame;

	/**
	 * Crea el servidor con un puerto
	 * @param port puerto
	 * @throws IOException ioe
	 */
	public Server(int port) throws IOException {
		super(port);
		this.isLive = true;
		this.clients = new ArrayList<>();
		this.server_frame = new Server_frame();
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
		c.sendData(Commands.UPBIDDING, ServerController.getInstanceOf().getManager().getBiddings()); //Envia lista de subastas
		return c;
	}
	
	/**
	 * Obtiene los clientes
	 * @return clients clientes
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}
	
}
