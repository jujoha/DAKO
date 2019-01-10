package edu.hm.dako.chat.server;

import java.io.*;



import java.net.*;

import edu.hm.dako.chat.common.AuditLogPDU;

/** 
 * erzeugt einen UDP-Client für den Server
 * 
 * @author dominikasam
 *
 */

public class UdpClient {
	
	private DatagramSocket socketclient;
	private InetAddress address;
	
	private byte[] buf;
	
	public UdpClient(String ipadress) throws SocketException, UnknownHostException {
	
	socketclient = new DatagramSocket();
	address = InetAddress.getByName(ipadress);
	
	}
	
	
	/**
	 * wandelt PDU in DatagramPacket um
	 * @param o
	 */
	public void sendAudit(Object o)  
	{    try    
	{      
	      ByteArrayOutputStream byteStream = new
	          ByteArrayOutputStream(5000);
	      ObjectOutputStream os = new ObjectOutputStream(new
	                              BufferedOutputStream(byteStream));
	      os.flush();
	      os.writeObject(o);
	      os.flush();
	      //retrieves byte array
	      byte[] sendBuf = byteStream.toByteArray();
	      
	      DatagramPacket packet = new DatagramPacket(
	                          sendBuf, sendBuf.length, address, 4445);
	      
	      socketclient.send(packet);
	      os.close();
	    }
	    catch (UnknownHostException e)
	    {
	      System.err.println("Exception:  " + e);
	      e.printStackTrace();    }
	    catch (IOException e)    { e.printStackTrace();
	 }
	  }
	
/**
 * schließt Socket
 */
public void close(){
	socketclient.close();
	
}
}



