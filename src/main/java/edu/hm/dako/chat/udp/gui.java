//package edu.hm.dako.chat.udp;
//
//import edu.hm.dako.chat.udp.*;
//
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//
//import java.util.Calendar;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import javafx.stage.WindowEvent;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.PropertyConfigurator;
//
//import edu.hm.dako.chat.common.AuditLogPDU;
//import edu.hm.dako.chat.common.ChatPDU;
//import edu.hm.dako.chat.common.ExceptionHandler;
//import edu.hm.dako.chat.common.ImplementationType;
//import edu.hm.dako.chat.common.SystemConstants;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.Separator;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.*;
//
//import edu.hm.dako.chat.server.*;
//
//public class gui {
//
//	private Button startButton;
//	private Button stopButton;
//	
//	UdpClient auditClient;
//	
//	public gui() throws SocketException, UnknownHostException  {
//	auditClient= new UdpClient();
//	}
//	
//	public static void main(String[] args) throws SocketException {
//		PropertyConfigurator.configureAndWatch("log4j.server.properties", 60 * 1000);
//		launch(args);
//		
//
//	}
//}
