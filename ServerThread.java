/**************************************************************************************************************
 *	CS-498 MP1
 *	Feb. 25 2014
 *	Team 05: english7, 
 *	
 *	ServerThread.java: Class allowing for multi-threading, enabling us to listen to all clients simultaneously 
 **************************************************************************************************************/

import java.io.*;
import java.net.*;

public class ServerThread extends Thread{
	private Server server;	// The server that created the thread
	private Socket socket;	// The socket connected to our client
	
	// Constructor
	public ServerThread(Server server, Socket socket){
		this.server = server;
		this.socket = socket;
		start();	// Start the thread
	}

	public void run(){
		try{
			// Create a DataINputStream to get communication from client
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());

			// Continuously check for new messages
			while(true){
				String message = dataIn.readUTF();	// Read the next message
				server.SendToAll(message);	// Have server send message to all clients		
			}
		} catch(EOFException eof){
		} catch(IOException ioe){
			ioe.printStackTrace();
		} finally{
			server.removeConnection(socket);	// Connection is closed (i.e. client's session is terminated)
		}
	}
}