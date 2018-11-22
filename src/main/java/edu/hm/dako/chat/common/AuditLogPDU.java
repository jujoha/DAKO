package edu.hm.dako.chat.common;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class AuditLogPDU {

	// Kommandos bzw. PDU-Typen
	private PduType pduType;
	
	// Zeitstempel
	private String timestamp;
	
	// Name des Clients, von dem ein Event initiiert wurde
	private String eventUserName;
	
	// Name des Client-Threads, der den Request absendet
	private String clientThreadName;

	// Name des Threads, der den Request im Server
	private String serverThreadName;
	
	// Nutzdaten (eigentliche Chat-Nachricht in Textform)
	private String message;
	
	
	
	// Fehlercode, derzeit nur 1 Fehlercode definiert
	//Julian: ka ob wir das noch brauchen
		private int errorCode;
		public final static int NO_ERROR = 0;
		public final static int LOGIN_ERROR = 1;
		
		
		public AuditLogPDU() {
			pduType = PduType.UNDEFINED;
			eventUserName = null;
			clientThreadName = null;
			serverThreadName = null;
			errorCode = NO_ERROR;
			message = null;
			timestamp = null;
		}


		public AuditLogPDU(PduType cmd, String message) {
			this.pduType = cmd;
			this.message = message;
		}
		
		public String toString() {

			return "\n"
					+ "AuditLogPDU ****************************************************************************************************"
					+ "\n" + "PduType: " + this.pduType + ", " + "\n" 
					+ "eventUserName: " + this.eventUserName + ", " + "\n"
					+ "clientThreadName: " + this.clientThreadName + ", " + "\n"
					+ "serverThreadName: " + this.serverThreadName + ", " + "\n" 
					+ "errrorCode: " + this.errorCode + ", " + "\n"
					+ "timesamp: " + this.timestamp + ", " + "\n"
					+ "message: " + this.message + "\n"
					+ "**************************************************************************************************** ChatPdu"
					+ "\n";
		}

		public void setPduType(PduType pduType) {
			this.pduType = pduType;
		}

		public void setClientThreadName(String threadName) {
			this.clientThreadName = threadName;
		}

		public void setServerThreadName(String threadName) {
			this.serverThreadName = threadName;
		}

		public void setMessage(String msg) {
			this.message = msg;
		}
		
		public void setEventUserName(String name) {
			this.eventUserName = name;
		}

		public void setTimestamp() {
			this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		}

		public PduType getPduType() {
			return pduType;
		}

		public String getEventUserName() {
			return eventUserName;
		}

		public String getClientThreadName() {
			return (clientThreadName);
		}

		public String getServerThreadName() {
			return (serverThreadName);
		}

		public String getMessage() {
			return (message);
		}
		
		public String getTimestamp() {
			return (timestamp);
		}

		public int getErrorCode() {
			return (errorCode);
		}
		
		public void setErrorCode(int code) {
			this.errorCode = code;
		}
		
		/**
		 * Erzeugen einer Logout-Event-PDU
		 * 
		 * @param userName
		 *          Client, der Logout-Request-PDU gesendet hat
		 * @param receivedPdu
		 *          Empfangene PDU (Logout-Request-PDU)
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createLogoutEventPdu(String userName, AuditLogPDU receivedPdu) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.LOGOUT_EVENT);
			pdu.setTimestamp();
			pdu.setEventUserName(userName);
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(receivedPdu.getClientThreadName());
			return pdu;
		}

		/**
		 * Erzeugen einer Login-Event-PDU
		 * 
		 * @param userName
		 *          Client, der Login-Request-PDU gesendet hat
		 * @param clientList
		 *          Liste der registrierten User
		 * @param receivedPdu
		 *          Empfangene PDU (Login-Request-PDU)
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createLoginEventPdu(String userName, AuditLogPDU receivedPdu) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.LOGIN_EVENT);
			pdu.setTimestamp();
			pdu.setEventUserName(userName);
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(receivedPdu.getClientThreadName());
			return pdu;
		}

		/**
		 * Erzeugen einer Login-Response-PDU
		 * 
		 * @param eventInitiator
		 *          Urspruenglicher Client, der Login-Request-PDU gesendet hat
		 * @param receivedPdu
		 *          Empfangene PDU
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createLoginResponsePdu(String eventInitiator, AuditLogPDU receivedPdu) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.LOGIN_RESPONSE);
			pdu.setTimestamp();
			pdu.setEventUserName(eventInitiator);
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(receivedPdu.getClientThreadName());
			return pdu;
		}

		/**
		 * Erzeugen einer Chat-Message-Event-PDU
		 * 
		 * @param userName
		 *          Client, der Chat-Message-Request-PDU gesendet hat
		 * @param receivedPdu
		 *          (Chat-Message-Request-PDU)
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createChatMessageEventPdu(String userName, AuditLogPDU receivedPdu) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.CHAT_MESSAGE_EVENT);
			pdu.setTimestamp();
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(receivedPdu.getClientThreadName());
			pdu.setEventUserName(userName);
			pdu.setMessage(receivedPdu.getMessage());
			return pdu;
		}

		/**
		 * Erzeugen einer Logout-Response-PDU
		 * 
		 * @param eventInitiator
		 *          Urspruenglicher Client, der Logout-Request-PDU gesendet hat
		 * @param numberOfSentEvents
		 *          Anzahl an den Client gesendeter Events
		 * @param numberOfLostEventConfirms
		 *          Anzahl verlorener EventConfirms des Clients
		 * @param numberOfReceivedEventConfirms
		 *          Anzahl empfangender EventConfirms des Clients
		 * @param numberOfRetries
		 *          Anzahl wiederholter Nachrichten
		 * @param numberOfReceivedChatMessages
		 *          Anzahl empfangender Chat-Messages des Clients
		 * @param clientThreadName
		 *          Name des Client-Threads
		 * @return Aufgebaute ChatPDU
		 */
		public static AuditLogPDU createLogoutResponsePdu(String clientThreadName) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.LOGOUT_RESPONSE);
			pdu.setTimestamp();
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(clientThreadName);
			return pdu;
		}

		/**
		 * Erzeugen einer Chat-Message-Response-PDU
		 * 
		 * @param eventInitiator
		 *          Urspruenglicher Client, der Chat-Message-Request-PDU gesendet hat
		 * @param numberOfSentEvents
		 *          Anzahl an den Client gesendeter Events
		 * @param numberOfLostEventConfirms
		 *          Anzahl verlorener EventConfirms des Clients
		 * @param numberOfReceivedEventConfirms
		 *          Anzahl empfangender EventConfirms des Clients
		 * @param numberOfRetries
		 *          Anzahl wiederholter Nachrichten
		 * @param numberOfReceivedChatMessages
		 *          Anzahl empfangender Chat-Messages des Clients
		 * @param serverTime
		 *          Requestbearbeitungszeit im Server
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createChatMessageResponsePdu(String eventInitiator, String clientThreadName) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.CHAT_MESSAGE_RESPONSE);
			pdu.setTimestamp();
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(clientThreadName);
			pdu.setEventUserName(eventInitiator);
			return pdu;
		}

		/**
		 * Erzeugen einer Login-Response-PDU mit Fehlermeldung
		 * 
		 * @param pdu
		 *          Empfangene PDU
		 * @param errorCode
		 *          Fehlercode, der in der PDU uebertragen werden soll
		 * @return Erzeugte PDU
		 */
		public static AuditLogPDU createLoginErrorResponsePdu(AuditLogPDU receivedPdu, int errorCode) {

			AuditLogPDU pdu = new AuditLogPDU();
			pdu.setPduType(PduType.LOGIN_RESPONSE);
			pdu.setTimestamp();
			pdu.setServerThreadName(Thread.currentThread().getName());
			pdu.setClientThreadName(receivedPdu.getClientThreadName());
			pdu.setErrorCode(errorCode);
			return pdu;
		}

}
