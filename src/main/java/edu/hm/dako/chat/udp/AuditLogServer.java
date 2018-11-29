package edu.hm.dako.chat.udp;

import java.io.IOException;
import java.io.*;
import java.net.*;


import edu.hm.dako.chat.common.AuditLogPDU;

public class AuditLogServer extends Thread {
	
	private DatagramSocket socket;
    private boolean running;
    
 
    public AuditLogServer() throws SocketException {
       // socket = new DatagramSocket(4445);
    }
    
    public static void main(String[] args) throws SocketException {
    	AuditLogServer test = new AuditLogServer();
    	test.run();
    	
    }
    
    public void startLog() {
    	this.run();
    }
    
    public void run() {
    	try {
			socket = new DatagramSocket(4445);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket kann nicht erstellt werden");
			e.printStackTrace();
		}
    	System.out.println("Audit Server gestartet");
        running = true;
 
        while (running) {
    	AuditLogPDU ausgabe= (AuditLogPDU) recvLog();
    	System.out.println(ausgabe.toString());
    	
    	
    	
    	if (ausgabe.toString(ausgabe.getPduType()).equals ("Typ: AUDIT-End")) {
            running = false;
            continue;
        }
    	
        }
        socket.close();
        System.out.println("socket geschlossen");
      Thread.currentThread().interrupt();
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
 
   
}


