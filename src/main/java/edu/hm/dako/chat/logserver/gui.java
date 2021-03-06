package edu.hm.dako.chat.logserver;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import edu.hm.dako.chat.common.AuditLogPDU;
import edu.hm.dako.chat.server.SimpleChatServerImpl;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

/**
 * gui zum starten der LogServer
 * 
 * @author julianhaimerl
 *
 */

public class gui extends JFrame {

	private JPanel contentPane;
	private final Action tcpStarten = new SwingAction();
	private final Action udpStarten = new SwingAction_1();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton udp = new JButton("UDP starten");
		udp.setAction(tcpStarten);
		udp.setBounds(53, 85, 117, 81);
		contentPane.add(udp);
		
		
		JButton tcp = new JButton("TCP starten");
		tcp.setAction(udpStarten);
		tcp.setBounds(256, 85, 117, 81);
		contentPane.add(tcp);
		
		
	}
	
	/**
	 * startet den TCPLogServer
	 * @author dominikasam
	 *
	 */

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "tcpStartern");
			putValue(SHORT_DESCRIPTION, "startet den TCP Server");
		}
		public void actionPerformed(ActionEvent e) {
			// just for not interrupting the GUI
			new Thread(new Runnable() {
			    public void run() {
			    	try {
			    		AuditLogTCP server = new AuditLogTCP();
						server.run();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	/**
	 * startet den UDPLogServer
	 * 
	 * @author dominikasam
	 *
	 */
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "udp Starten");
			putValue(SHORT_DESCRIPTION, "Startet den udp server");
		}
		public void actionPerformed(ActionEvent b) {
			// just for not interrupting the GUI
			new Thread(new Runnable() {
			    public void run() {
			    	try {
						AuditLogServer server = new AuditLogServer();
						server.run();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}).start();
		}
	}
	
}
