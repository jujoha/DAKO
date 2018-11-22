package edu.hm.dako.chat.udp;

import java.io.IOException;
import java.net.*
;

public class AuditLogServer extends Thread {
	
	private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
 
    public AuditLogServer() throws SocketException {
        socket = new DatagramSocket(4445);
    }
    
    public static void main(String[] args) throws SocketException {
    	AuditLogServer test = new AuditLogServer();
    	test.run();
    	
    }
 
    public void run() {
    	System.out.println("Audit Server gestartet");
        running = true;
 
        while (running) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
				socket.receive(packet);
				System.out.println(packet.getAddress().toString());
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
}


