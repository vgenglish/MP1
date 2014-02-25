/****************************************************************************
 *	CS-498 MP1
 *	Feb. 25 2014
 *	Team 05: english7, 
 *	
 *	Client.java: Class responsible for client interaction with the server
 ****************************************************************************/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Panel implements Runnable{
	// Components for GUI
	private TextArea ta = new TextArea();
	private TextField tf = new TextField();
	
	// The socket connecting client to server
	private Socket socket;
	
	// Streams we communicate to server
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
		
	// Constructor
	public Client(String host, int port){
		//Set up screen
		setLayout(new BorderLayout());
		add("North", tf);
		add("Center", ta);
		
		tf.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent send){processMessage(send.getActionCommand());}		
		});
	
		// Connect to the server
		try{
			socket = new Socket(host, port);	// Initiate connection to server
			System.out.println("Connected to server" + socket);	// Inform user of connection
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			new Thread(this).start();	// Start thread for receiving messages	
		} catch(IOException ioe){System.out.println(ioe);}
	}

	// Called when the user types something
	private void processMessage(String message){
		try{
			dataOut.writeUTF(message);	// Send message to the server
			tf.setText("");	// Clear input field
		} catch(IOException ioe){System.out.println(ioe);}
	}

	// Background thread to display messages
	public void run(){
		try{
			// Continuously receive messages
			while(true){
				String message = dataIn.readUTF();	// Get the next message
				ta.append(message + "\n");	// Print message to window
			}
		} catch(IOException ioe){System.out.println(ioe);}
	}
}