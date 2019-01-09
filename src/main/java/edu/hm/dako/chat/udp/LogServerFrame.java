package edu.hm.dako.chat.udp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class LogServerFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogServerFrame frame = new LogServerFrame();
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
	public LogServerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton UDP = new JButton("UDP starten");
		UDP.setBounds(63, 59, 118, 116);
		UDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AuditLogServer udp = new AuditLogServer();
					udp.run();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(UDP);
		
		JButton TCP = new JButton("TCP starten");
		TCP.setBounds(258, 59, 116, 116);
		contentPane.add(TCP);
		
		
		
		
		 TCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent b) {
				AuditLogTCP tcp = new AuditLogTCP();
				try {
					tcp.run();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
		});
		
		
	}
	
	

}
