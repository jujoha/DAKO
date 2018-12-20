package edu.hm.dako.chat.udp;

import java.io.*;
import java.net.*;
import edu.hm.dako.chat.tcp.*;

import edu.hm.dako.chat.common.AuditLogPDU;
import edu.hm.dako.chat.common.ExceptionHandler;


public class AuditLogTCP {
	BufferedWriter writer;
	static int logincount;
	static int logoutcount;
	static int messagecount;
	
	public static void main(String args[]) throws BindException, IOException {
		BufferedWriter writer= new BufferedWriter(new FileWriter("AuditLogTCP.log"));
    	writer.write("Log File:");
		TcpServerSocket socket= new TcpServerSocket(6789, 8000, 8000);
		AuditLogPDU receivedPdu = null;
		TcpConnection con= (TcpConnection) socket.accept();
		
		boolean running=true;
		
		while(running == true) {
			
			
			
			try {
				receivedPdu= (AuditLogPDU) con.receive();
				
		    		
				writer.append(receivedPdu.toString());
					
				writer.flush();
				
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
	        
	        System.out.println("socket geschlossen");
	     
		}
	}

	
	  
	  
	  
	/*public static void main(String argv[]) throws Exception {
		  String clientSentence;
		  String capitalizedSentence;
		  ServerSocket socket = new ServerSocket(6789);


	 while (true) {
		 AuditLogPDU ausgabe
		   Socket connectionSocket = socket.accept();
		   BufferedReader inFromClient =
		    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		   DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		   ausgabe= connectionSocket.getInputStream();
		   clientSentence = inFromClient.readLine();
		   System.out.println("Received: " + clientSentence);
		   capitalizedSentence = clientSentence.toUpperCase() + 'n';
		   outToClient.writeBytes(capitalizedSentence);
		  }
}
*/
