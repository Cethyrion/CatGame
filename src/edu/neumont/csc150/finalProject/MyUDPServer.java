package edu.neumont.csc150.finalProject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MyUDPServer {

	public static void main(String args[]) throws Exception {
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		System.out.println("waiting......");
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);

			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);

			InetAddress IPAddress = receivePacket.getAddress();
			System.out.println(IPAddress);
			
			int port = receivePacket.getPort();

			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();

//			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

//			serverSocket.send(sendPacket);
		}
	}
}