/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: Server
 * Purpose: Repsonible for run the Server UI and create connection
 * 
 * */

package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	static int port;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		ServerWindow serverUI;
		
		//Check: whether the port input is in valid range
		if (Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) >= 49152 ){
			System.out.println("Invalid Port Number");
			System.exit(-1);
		}
		
		if(! args[1].equals("src/dictionary.json") ) {
			System.out.println("Wrong file path");
			System.exit(-1);
		}
		
		port = Integer.parseInt(args[0]);
		
		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			serverUI = new ServerWindow(Integer.parseInt(args[0]), InetAddress.getLocalHost().getHostAddress());
			serverUI.start();

		} catch (IOException e) {
			System.out.println("Error happended, Error type: IO Exception ");
		}
		while (true) {
			Socket socket = null;

			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Error happended, IO Exception");
			}
			Connection connection = new Connection(socket, args[1]);
			connection.start();
			System.out.println("Server: connection established!");

		}

	}

}
