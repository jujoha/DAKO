package edu.hm.dako.chat.udp;

import java.io.IOException;
import java.io.*;
import java.net.*;


import edu.hm.dako.chat.common.AuditLogPDU;

public class AuditLogServer extends Thread {
	
	private DatagramSocket socket;
    private boolean running;
    
 
    public AuditLogServer() throws SocketException {
        socket = new DatagramSocket(4445);
    }
    
    public static void main(String[] args) throws SocketException {
    	AuditLogServer test = new AuditLogServer();
    	test.run();
    	
    }
    
    public void startLog() {
    	this.run();
    }
    
    public void run() {
    	System.out.println("Audit Server gestartet");
        running = true;
 
        while (running) {
    	AuditLogPDU ausgabe= (AuditLogPDU) recvLog();
    	System.out.println(ausgabe.toString());
        }
        socket.close();
    }
    
    public void close() {
    	running=false;
 
    	System.out.println("Log Beendet");
    }
    
    
    public Object recvLog()  
    {    try
        {
          byte[] recvBuf = new byte[5000];
          DatagramPacket packet = new DatagramPacket(recvBuf,
                                                     recvBuf.length);
          socket.receive(packet);
          
          ByteArrayInputStream byteStream = new
                                      ByteArrayInputStream(recvBuf);
          ObjectInputStream is = new
               ObjectInputStream(new BufferedInputStream(byteStream));
          Object o = is.readObject();
          is.close();
          return(o);
        }
        catch (IOException e)
        {
          System.err.println("Exception:  " + e);
          e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        { e.printStackTrace(); }
        return(null);  }
 
   /* public void run() {
    	System.out.println("Audit Server gestartet");
        running = true;
 
        while (running) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
            	
				socket.receive(packet);
				System.out.println(packet.getData());
				
				//System.out.println(packet.getAddress().toString());
				//AuditLogPDU audit = newAuditLogPDU(packet);
				;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received 
              = new String(packet.getData(), 0, packet.getLength());
             
            if (received.equals("end")) {
                running = false;
                continue;
            }
            try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        socket.close();
    }
    */
}


