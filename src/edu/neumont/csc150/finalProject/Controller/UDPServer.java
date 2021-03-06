package edu.neumont.csc150.finalProject.Controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import edu.neumont.csc150.finalProject.Actor.Player.PlayerID;
import edu.neumont.csc150.finalProject.Main.MainFrame;

public class UDPServer {

	private byte[] receiveData, sendData;
	private DatagramSocket serverSocket;
	protected DatagramPacket receivePacket;
	private InetAddress IPAddress;
	private volatile boolean isClose = false;

	private int assignIDs = 0;
	private static ControlHandler[] controlHandlers;
	private String buttonInput;
	private Thread assignThread;

	public UDPServer() throws Exception {

		initVars();

		System.out.println("UDP Server Started........");

		assignThread = new Thread(new Runnable() {

			@Override
			public void run() {

				boolean assigned;

				do {
					assigned = false;

					recievePacket();

					if (buttonInput.equals("give")) {
						PlayerID existId = null;
						String id = "";
						for (ControlHandler controlHandler : controlHandlers) {
							if (controlHandler != null) {
								if (controlHandler.IPAddress.equals(IPAddress)) {
									assigned = true;
									existId = controlHandler.ID;
								}
							}
						}

						// gets port from incoming packets
						int port = receivePacket.getPort();
						
						id = Integer.toString(assignIDs);
						
						sendData = id.getBytes();
						
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
						
						try {
							serverSocket.send(sendPacket);
							
							if (!assigned) {
								assign(IPAddress);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						
						
					} else {
						
						for (ControlHandler controlHandler : controlHandlers) {
							if (controlHandler != null) {
								if (controlHandler.IPAddress.equals(IPAddress)) {
									String[] readInput = buttonInput.toLowerCase().split("_", 2);

									if (readInput.length == 2) {
										int keyCode = PlayerID.translate(readInput[1], controlHandler.ID);
										int eventType = readInput[0].equals("pressed") ? KeyEvent.KEY_PRESSED
												: KeyEvent.KEY_RELEASED;
										KeyEvent kE = new KeyEvent(MainFrame.game, eventType,
												System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);

										MainFrame.game.dispatchToVisible(kE);
									}
								}
							}
						}
					}
				} while (!isClose);

			}

		});

		assignThread.start();
	}
	
	public void assign(InetAddress IP) {
		if (assignIDs < PlayerID.values().length) {
			controlHandlers[assignIDs] = new ControlHandler(IP, PlayerID.values()[assignIDs]);
			assignIDs++;
		}
	}
	
	public void assign(InetAddress IP, KeyEvent toFind) {
		if (assignIDs < PlayerID.values().length) {
			controlHandlers[assignIDs] = new ControlHandler(IP, PlayerID.findWhich(toFind));
			assignIDs++;
		}
	}

	private void initVars() throws SocketException {
		serverSocket = new DatagramSocket(5555);

		receiveData = new byte[10];
		sendData = new byte[10];
		receiveData = new byte[20];
		sendData = new byte[20];

		controlHandlers = new ControlHandler[PlayerID.values().length];
	}

	private void recievePacket() {
		receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// receives any data
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// gets rid of excess string
		buttonInput = new String(receivePacket.getData()).substring(0, receivePacket.getLength());
		System.out.println("RECEIVED: " + buttonInput);

		// gets IP address of incoming packets
		IPAddress = receivePacket.getAddress();
		System.out.println("FROM" + IPAddress);
	}
	
	public void stopServer(){
		serverSocket.close();		
		isClose = true;
	}
}
