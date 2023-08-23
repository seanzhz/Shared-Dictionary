/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: ServerWindow
 * Purpose: Display connection status in the window
 * 
 * */

package server;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;


public class ServerWindow extends Thread {

	private JFrame frame;
	private JLabel hostLb;
	private JLabel portLb;
	private JLabel countLb;

	/**
	 * Launch the application.
	 * 
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow(Server.port, InetAddress.getLocalHost().getHostAddress());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * Create the application.
	 */
	public ServerWindow(int port, String host) {
		initialize(port, host);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int port, String host) {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				portLb.setText("" + port);
				hostLb.setText(host);

			}

		});

		JLabel lblNewLabel = new JLabel("Host：");
		lblNewLabel.setBounds(38, 87, 61, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Port: ");
		lblNewLabel_1.setBounds(38, 133, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("Server Dashboard");
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.BOLD, 26));
		lblNewLabel_4.setBounds(84, 17, 239, 46);
		frame.getContentPane().add(lblNewLabel_4);

		hostLb = new JLabel("");
		hostLb.setBounds(84, 87, 144, 16);
		frame.getContentPane().add(hostLb);

		portLb = new JLabel("");
		portLb.setBounds(84, 133, 61, 16);
		frame.getContentPane().add(portLb);

		countLb = new JLabel("");
		countLb.setBounds(382, 133, 61, 16);
		frame.getContentPane().add(countLb);
	}
}
