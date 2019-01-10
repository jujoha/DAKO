package edu.hm.dako.chat.udp;

import java.io.IOException;
import java.io.*;
import java.net.*;


import edu.hm.dako.chat.common.AuditLogPDU;


/** UDP basierter LogServer zum protokollieren
 * der empfangenen Anfragen
 * @author dominikasam
 *
 */

public class AuditLogServer extends Thread {
	
	private DatagramSocket socket;
    private boolean running;
    BufferedWriter writer;
 
    //Zähler für Art der empfangenen Logs
    static int logincount;
	static int logoutcount;
	static int messagecount;
	
    public AuditLogServer() throws IOException {
       
    	writer= new BufferedWriter(new FileWriter("AuditLogUDP.log"));
    	writer.write("Log File:");
    	messagecount=0;
        
       
    }
    
    public static void main(String[] args) throws IOException {
    	AuditLogServer test = new AuditLogServer();
    	test.run();
    	
    }
   
   
    /**
     * startet den LogServer
     */
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
    	AuditLogPDU receivedPdu= (AuditLogPDU) recvLog();
    	try {
    		//schreiben in LogFile
			writer.append(receivedPdu.toString());
			
			
		writer.flush();
		
		//erfassen welche Art von Anfrage stattfand
		switch (receivedPdu.getPduType()) {

		case LOGIN_EVENT:
			logincount= logincount +1;
			break;


		case LOGOUT_EVENT:
			logoutcount= logoutcount +1;
			
			break;
			
		case CHAT_MESSAGE_EVENT:
			messagecount= messagecount +1;
			System.out.println("messagecount = " + messagecount);

			break;
			
	


		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(receivedPdu.toString());
    	
    	
    	//schließen des Servers bei shutdown pdu
    	if (receivedPdu.toString(receivedPdu.getPduType()).equals ("Typ: AUDIT-End")) {
            running = false;
            continue;
        }
    	
        }
        Thread.currentThread().interrupt();
        socket.close();
        try {
        	writer.append("logincount: " + logincount);
        	writer.flush();
        	writer.append("logoutcount: " + logoutcount);
        	writer.flush();
        	writer.append("messagecount: " + messagecount);
        	writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(messagecount);
        System.out.println("socket geschlossen");
     
    }
    
    
    /**
     * wandelt das empfangene Packet wieder in eine PDU um
     * @return
     */
    
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


