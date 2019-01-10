package edu.hm.dako.chat.udp;

import java.io.*;
import java.net.*;
import edu.hm.dako.chat.tcp.*;

import edu.hm.dako.chat.common.AuditLogPDU;
import edu.hm.dako.chat.common.ExceptionHandler;

/**
 * TCP basierter Logserver
 * @author dominikasam
 *
 */

public class AuditLogTCP {
	BufferedWriter writer;
	static int logincount;
	static int logoutcount;
	static int messagecount;
	
	
	public static void main(String args[]) throws IOException {
		AuditLogTCP test = new AuditLogTCP();
		test.run();
		
	}
	
	/**
	 * startet den Server
	 * @throws IOException
	 */
	public void run() throws IOException {
		System.out.println("TCP Log gestartet");
		BufferedWriter writer= new BufferedWriter(new FileWriter("AuditLogTCP.log"));
    	writer.write("Log File:");
		TcpServerSocket socket= new TcpServerSocket(50001, 300000, 300000);
		AuditLogPDU receivedPdu = null;
		TcpConnection con= (TcpConnection) socket.accept();
		
		boolean running=true;
		
		while(running == true) {
			
			
			
			try {
				receivedPdu= (AuditLogPDU) con.receive();
				
		    		
				writer.append(receivedPdu.toString());
				//schreiben in Log File	
				writer.flush();
				System.out.println(receivedPdu.toString());
				
				//erfasst welche Art von Anfrage stattfand
					switch (receivedPdu.getPduType()) {

					case LOGIN_EVENT:
						logincount= logincount +1;
						break;


					case CHAT_MESSAGE_EVENT:
						messagecount= messagecount +1;

						break;


					case LOGOUT_EVENT:
						logoutcount= logoutcount +1;

						break;


					}
				} catch (Exception e) {
				
				}
			//schließt den Server wenn shutdown pdu empfangen wird
			
			if (receivedPdu.toString(receivedPdu.getPduType()).equals ("Typ: AUDIT-End")) {
	           running= false;
	        }
	    	
	        }
	        
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
	        messagecount=0;
	        logincount=0;
	        logoutcount=0;
		}
	}

	
	  
	