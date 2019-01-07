package edu.hm.dako.chat.server;

import java.io.*;

import java.net.*;

import edu.hm.dako.chat.common.AuditLogPDU;

public class UdpClient {
	
	private DatagramSocket socketclient;
	private InetAddress address;
	
	private byte[] buf;
	
	public UdpClient() throws SocketException, UnknownHostException {
	
	socketclient = new DatagramSocket();
	address = InetAddress.getByName("localhost");
	
	}
	
	/*public String sendEcho(AuditLogPDU audit) throws IOException {
		buf = audit.getBytes(audit);
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 4445);
        socketclient.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socketclient.receive(packet);
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
    }
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
	}



