/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: Connection
 * Purpose: create connection for each client and server, 
 * use it to process each command between client and server. 
 * 
 * */

package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Connection extends Thread {

	Socket socket = null;
	// input stream
	BufferedReader bufferedReader;
	// output stream
	BufferedWriter bufferedWriter;
	// dictionary
	Dictionary dictionary;

	public void run() {

		try {
			while (true) {
				String read = null;
				read = bufferedReader.readLine();

				if (read != null) {
					String[] requestArray;
					requestArray = read.split(":");
					// Some command are consisted of COMMAND:word:meaning, some command are
					// consisted of COMMAND:word or COMMAND:other
					if (requestArray.length == 3) {
						String action = requestArray[0];
						String word = requestArray[1].toLowerCase();
						String meaning = requestArray[2];
						switch (action) {
						case "ADD": {
							add(word, meaning);
							break;
						}
						case "UPDATE": {
							update(word, meaning);
							break;
						}
						default:
							System.out.println("Unexpected action: " + action);
						}
					} else if (requestArray.length == 2) {
						String action = requestArray[0];
						String word = requestArray[1].toLowerCase();
						switch (action) {
						case "QUERY": {
							query(word);
							break;
						}
						case "REMOVE": {
							remove(word);
							break;
						}
						case "EXIT": {
							this.interrupt();
							System.out.println("Socket: " + socket + "has been closed.");
							break;
						}
						default:
							System.out.println("Unexpected action: " + action);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IO Exception error");
		}
	}

	public Connection(Socket socket, String path) {
		this.socket = socket;
		dictionary = new Dictionary(path);
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Encoding Exception Error");
		} catch (IOException e) {
			System.out.println("IO Exception error");
		}
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Encoding Exception Error");
		} catch (IOException e) {
			System.out.println("IO Exception error");
		}

	}

	public synchronized void add(String word, String meaning) {

		// The process that check whether missing input is in the GUI part
		// Check whether the word existed
		if (!dictionary.wordExisting(word)) {
			// add the word
			dictionary.addWord(word, meaning);
			String feedbackString = "Add successfully: The word and its meaning has been added." + "\n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		} else {
			String feedbackString = "Add Failed: Word already existed." + "\n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}
		}

	}

	public synchronized void query(String word) {

		// The process that check whether missing word input is in the GUI part
		// First, check whether we have this word
		if (dictionary.wordExisting(word)) {
			String feedbackString = "Query successfully:" + dictionary.getMeaning(word) + "\n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		} else {
			String feedbackString = "Query Failed: The word is not existed! \n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		}

	}

	public synchronized void remove(String word) {
		// The process that check whether missing word input is in the GUI part
		// First, check whether we have this word
		if (dictionary.wordExisting(word)) {
			dictionary.removeWord(word);
			String feedbackString = "Remove successfully:" + "Word " + word + " has been removed successfully." + "\n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}
		} else {
			String feedbackString = "Remove Failed: The word is not existed! \n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		}
	}

	public synchronized void update(String word, String meaning) {
		// The process that check whether missing word input is in the GUI part
		// First, check whether we have this word
		if (dictionary.wordExisting(word)) {
			dictionary.updateWord(word, meaning);
			String feedbackString = "Update successfully:" + " The meaning of word  " + word
					+ " has been updated successfully." + "\n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		} else {
			String feedbackString = "Update Failed: The word is not existed! \n";
			System.out.println(feedbackString);
			try {
				bufferedWriter.write(feedbackString);
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("IO Exception error");
			}

		}

	}

}
