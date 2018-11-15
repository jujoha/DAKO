package edu.hm.dako.chat.udp;
import java.io.*;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.hm.dako.chat.connection.Connection;
import edu.hm.dako.chat.connection.ServerSocketInterface;


public class UdpServerSocket implements ServerSocketInterface{
	
	

	   public static void main(String args[]) throws Exception
	      {
		   System.out.println("erstellt");
	         DatagramSocket serverSocket = new DatagramSocket(9000);
	            byte[] receiveData = new byte[1024];
	            byte[] sendData = new byte[1024];
	            while(true)
	               {
	                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	                  serverSocket.receive(receivePacket);
	                  String sentence = new String( receivePacket.getData());
	                  System.out.println("RECEIVED: " + sentence);
	                  InetAddress IPAddress = receivePacket.getAddress();
	                  int port = receivePacket.getPort();
	                  String capitalizedSentence = sentence.toUpperCase();
	                  sendData = capitalizedSentence.getBytes();
	                  DatagramPacket sendPacket =
	                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
	                  serverSocket.send(sendPacket);
	               }
	      }

	@Override
	public Connection accept() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}
}
