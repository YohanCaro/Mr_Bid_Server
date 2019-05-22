package communication;

import java.io.IOException;

import com.prg3.mr_bid.communication.Server;

public class TestServer {

	public static void main(String[] args) {
		try {
			Server server = new  Server(12345);
			System.out.println("Servidor encendido");
			server.start();
		} catch (IOException e) {
			System.err.println("El servidor explotó!");
			e.printStackTrace();
		}
	}

}
