/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: Client
 * Purpose: send socket to connect, run client UI 
 * 
 * */

package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	// Different object is not shared a same socket
	static Socket socket = null;
	static BufferedReader bufferedReader = null;
	static BufferedWriter bufferedWriter = null;
	static int count = 0;

	public static void main(String[] args) {
		// Set up the UI as a thread to run
		ClientWindow clientUI = new ClientWindow();
		clientUI.start();
		//Check whether port within the valid range
		if (Integer.parseInt(args[1]) <= 1024 || Integer.parseInt(args[1]) >= 49152 )
		{
			System.out.println("Invalid Port Number");
			System.exit(-1);
		}
		
		// Establish the connect
		try {
			//socket = new Socket("localhost", 8080);
			socket = new Socket(args[0], Integer.parseInt(args[1]));
		} catch (UnknownHostException e) {
			System.out.println("Error happended, Error type: Unknown Host Exception");
		} catch (IOException e) {
			System.out.println("Error happended, Error type: IO Exception ");
		}
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error happended, Error type: Unknown Host Exception");
		} catch (IOException e) {
			System.out.println("Error happended, Error type: IO Exception ");
		}
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error happended, Unsupported Encoding Exception.");
		} catch (IOException e) {
			System.out.println("Error happended, Error type: IO Exception.");
		}
		System.out.println("Client: Connection established!");
	}

}
