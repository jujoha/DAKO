package edu.hm.dako.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.hm.dako.chat.common.AuditLogPDU;

public class UdpClient {
	
	private DatagramSocket socketclient;
	private InetAddress address;
	
	private byte[] buf;
	
	public UdpClient() throws SocketException, UnknownHostException {
	
	socketclient = new DatagramSocket();
	address = InetAddress.getByName("localhost");
	
	}
	
	public String sendEcho(AuditLogPDU audit) throws IOException {
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

}
