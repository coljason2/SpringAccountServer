package com.Tomcat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import org.apache.catalina.LifecycleException;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.awt.event.ActionEvent;

/*
 * Export jar file must choose Extract require lib to jar
 * 
 * */
public class Server {

	private JFrame frame;
	private JTextField PortText;
	private JButton button;
	private App App = new App();
	private Thread serverThread;
	private JFileChooser fc = new JFileChooser();
	private File f;

	/**
	 * Launch the application.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		System.setProperty("file.encoding", "UTF-8");
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null, null);
		System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
		System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Server window = new Server();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Spring Server");
		frame.setBounds(100, 100, 255, 129);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {

				// System.out.println(f.getAbsolutePath());
				int port = Integer.parseInt(PortText.getText());
				App.initializeserver(port, f);
				serverThread = new Thread(App);
				serverThread.start();
			}
		});
		btnStart.setBounds(23, 52, 87, 23);
		frame.getContentPane().add(btnStart);

		PortText = new JTextField();
		PortText.setText("7001");
		PortText.setBounds(66, 11, 96, 21);
		frame.getContentPane().add(PortText);
		PortText.setColumns(10);

		JLabel labelport = new JLabel("PORT：");
		labelport.setBounds(10, 14, 46, 15);
		frame.getContentPane().add(labelport);

		button = new JButton("Stop");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					App.getTomcat().stop();
				} catch (LifecycleException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(129, 52, 87, 23);
		frame.getContentPane().add(button);

		JButton btnNewButton = new JButton("file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(null);
				f = fc.getSelectedFile();
			}
		});
		btnNewButton.setBounds(175, 10, 64, 23);
		frame.getContentPane().add(btnNewButton);

	}
}
