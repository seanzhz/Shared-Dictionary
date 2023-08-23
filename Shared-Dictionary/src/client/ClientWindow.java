/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: ClientWindow
 * Purpose: Handle different event in the UI.
 * 
 * */

package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;


import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class ClientWindow extends Thread {

	private JFrame frmHelpp;
	private JTextField wordTF;
	private JTextArea meaningTA;
	private JTextArea outputTA;
	
	Date date = new Date();
	SimpleDateFormat dtf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	

	/**
	 * Launch the application.
	 * 
	 */

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frmHelpp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHelpp = new JFrame();
		frmHelpp.setTitle("Multi-threaded Dictionary Server");
		frmHelpp.setResizable(false);
		frmHelpp.setBounds(100, 100, 570, 500);
		frmHelpp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHelpp.getContentPane().setLayout(null);

		frmHelpp.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				try {
					Client.bufferedReader.close();
					Client.socket.close();
				} catch (IOException e1) {
					System.out.println("Error happended, IO Exception");
				}

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				try {
					// Client.bufferedWriter.
					Client.bufferedWriter.write("EXIT: The client is send exit request." + "\n");
					Client.bufferedWriter.flush();
					System.out.print("Window closing");
				} catch (IOException e1) {
					System.out.println("Error happended, IO Exception");
				}

			}

		});

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordTF.getText();
				String meaning = meaningTA.getText();
				
				//Process the missing input 
				if (word == null || meaning == null || word.trim().isEmpty() || meaning.trim().isEmpty()) {
					// default title and icon
					JOptionPane.showMessageDialog(frmHelpp, "Please make sure you have enter word and meaning.",
							"Warning: Missing input", JOptionPane.WARNING_MESSAGE);
					outputTA.append(dtf.format(date)+": Missing input \n");
				} else {
					String request = "ADD" + ":" + word + ":" + meaning + "\n";
					System.out.println("ADD COMMAND: -> " + request);
					try {
						Client.bufferedWriter.write(request);
						Client.bufferedWriter.flush();
						String feedbackString = Client.bufferedReader.readLine();

						if (feedbackString.split(":")[0].equals("Add successfully")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.PLAIN_MESSAGE);
							wordTF.setText("");
							meaningTA.setText("");
							System.out.println("ADD Successfully.");
						} else if (feedbackString.split(":")[0].equals("Add Failed")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.ERROR_MESSAGE);
							System.out.println("ADD Failed.");
						} else {
							JOptionPane.showMessageDialog(frmHelpp, "Some unexpected error happended, please try again",
									"Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("ADD Unexpected Error.");
						}
						
						outputTA.append(dtf.format(date)+ ": "+ feedbackString +"\n");

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("IO Exception error!");
					}

				}

			}
		});
		btnAdd.setBounds(369, 116, 146, 45);
		frmHelpp.getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordTF.getText();
				meaningTA.setText("");
				if (word == null || word.trim().isEmpty()) {
					// default title and icon
					JOptionPane.showMessageDialog(frmHelpp, "Please make sure you have enter word.",
							"Warning: Missing word", JOptionPane.WARNING_MESSAGE);
					outputTA.append(dtf.format(date)+": Missing input \n");
				} else {
					String request = "REMOVE" + ":" + word + "\n";
					System.out.println("REMOVE COMMAND: -> " + request);
					try {
						Client.bufferedWriter.write(request);
						Client.bufferedWriter.flush();
						String feedbackString = Client.bufferedReader.readLine();
						if (feedbackString.split(":")[0].equals("Remove successfully")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.PLAIN_MESSAGE);
							wordTF.setText("");
							System.out.println("REMOVE Successfully.");
						} else if (feedbackString.split(":")[0].equals("Remove Failed")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.ERROR_MESSAGE);
							System.out.println("Remove Failed.");
						} else {
							JOptionPane.showMessageDialog(frmHelpp, "Some unexpected error happended, please try again",
									"Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("REMOVE Unexpected Error.");
						}
						
						outputTA.append(dtf.format(date)+ ": "+ feedbackString +"\n");

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("IO Exception error!");
					}

				}

			}
		});
		btnRemove.setBounds(369, 173, 146, 45);
		frmHelpp.getContentPane().add(btnRemove);

		JButton btnQuery = new JButton("Query");
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordTF.getText();
				meaningTA.setText("");
				meaningTA.setText("");
				if (word == null || word.trim().isEmpty()) {
					// default title and icon
					JOptionPane.showMessageDialog(frmHelpp, "Please make sure you have enter word.",
							"Warning: Missing word", JOptionPane.PLAIN_MESSAGE);
					outputTA.append(dtf.format(date)+": Missing input \n");
				} else {
					String request = "QUERY" + ":" + word + "\n";
					System.out.println("QUERY COMMAND: -> " + request);
					try {
						Client.bufferedWriter.write(request);
						Client.bufferedWriter.flush();
						String feedbackString = Client.bufferedReader.readLine();
						if (feedbackString.split(":")[0].equals("Query successfully")) {
							meaningTA.setText(feedbackString.split(":")[1]);
							System.out.println("QUERY Successfully.");
						} else if (feedbackString.split(":")[0].equals("Query Failed")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.ERROR_MESSAGE);
							meaningTA.setText("");
							System.out.println("QUERY Failed.");
						} else {
							JOptionPane.showMessageDialog(frmHelpp, "Some unexpected error happended, please try again",
									"Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("QUERY Unexpected Error.");
						}
						
						outputTA.append(dtf.format(date)+": " + feedbackString + " is the meaning for the "+ word + "\n");

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("IO Exception error!");
					}

				}

			}
		});
		btnQuery.setBounds(369, 230, 146, 45);
		frmHelpp.getContentPane().add(btnQuery);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordTF.getText();
				String meaning = meaningTA.getText();
				// String feedbackString = null;

				if (word == null || meaning == null || word.trim().isEmpty() || meaning.trim().isEmpty()) {
					// default title and icon
					JOptionPane.showMessageDialog(frmHelpp, "Please make sure you have enter word and meaning.",
							"Warning: Missing input", JOptionPane.WARNING_MESSAGE);
					outputTA.append(dtf.format(date)+": Missing input \n");
				} else {
					String request = "UPDATE" + ":" + word + ":" + meaning + "\n";
					System.out.println("UPDATE COMMAND: -> " + request);
					try {
						Client.bufferedWriter.write(request);
						Client.bufferedWriter.flush();
						String feedbackString = Client.bufferedReader.readLine();

						if (feedbackString.split(":")[0].equals("Update successfully")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.PLAIN_MESSAGE);
							System.out.println("UPDATE Successfully.");
						} else if (feedbackString.split(":")[0].equals("Update Failed")) {
							JOptionPane.showMessageDialog(frmHelpp, feedbackString.split(":")[1],
									feedbackString.split(":")[0], JOptionPane.ERROR_MESSAGE);
							System.out.println("UPDATE Failed.");
						} else {
							JOptionPane.showMessageDialog(frmHelpp, "Some unexpected error happended, please try again",
									"Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("UPDATE Unexpected Error.");
						}
						outputTA.append(dtf.format(date)+ ": "+ feedbackString +"\n");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("IO Exception error!");
					}

				}
			}
		});
		btnUpdate.setBounds(369, 287, 146, 45);
		frmHelpp.getContentPane().add(btnUpdate);

		wordTF = new JTextField();
		wordTF.setBounds(48, 123, 264, 34);
		frmHelpp.getContentPane().add(wordTF);
		wordTF.setColumns(10);

		JLabel wordLb = new JLabel("Word:");
		wordLb.setBounds(48, 95, 61, 16);
		frmHelpp.getContentPane().add(wordLb);

		JLabel meaningLb = new JLabel("Meaning:");
		meaningLb.setBounds(48, 162, 61, 16);
		frmHelpp.getContentPane().add(meaningLb);

		JLabel titleLb = new JLabel("Dictionary");
		titleLb.setFont(new Font("Lucida Grande", Font.BOLD, 39));
		titleLb.setBounds(187, 17, 224, 61);
		frmHelpp.getContentPane().add(titleLb);

		meaningTA = new JTextArea();
		meaningTA.setLineWrap(true);
		meaningTA.setBounds(48, 189, 264, 142);
		frmHelpp.getContentPane().add(meaningTA);
		
		JLabel outputLB = new JLabel("Client Console:");
		outputLB.setBounds(48, 363, 105, 16);
		frmHelpp.getContentPane().add(outputLB);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 391, 463, 55);
		frmHelpp.getContentPane().add(scrollPane);
		
		outputTA = new JTextArea();
		scrollPane.setViewportView(outputTA);
		outputTA.setLineWrap(true);
		outputTA.setEditable(false);

	}
}
