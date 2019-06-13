/**
 * 
 */
package com.prg3.mr_bid.runner;

import java.io.IOException;

import com.prg3.mr_bid.communication.Server;

/** Clase Run - Crear el servidor
 *
 * @author Yohan Caro
 * @version 1.0 - 1/06/2019
 */
public class Run {

	/**
	 * Main principal del servidor!
	 * @param args parametros de inico (port)
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				Server server = new Server(Integer.parseInt(args[0]));
				System.out.println(server.getInetAddress().getLocalHost());
				System.out.println("Servidor encendido!");
				server.start();
//				server.close();
			} catch (NumberFormatException | IOException e) {
				System.err.println("Error: El parámetro debe ser numerico!");
			} 
		} else {
			System.err.println("Debe ingresar un puerto como parámetro!");
		}
	}

}
