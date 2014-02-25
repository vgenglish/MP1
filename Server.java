/****************************************************************************
 *	CS-498 MP1
 *	Feb. 25 2014
 *	Team 05: english7, 
 *	
 *	Server.java: Class responsible for managing clients
 ****************************************************************************/

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	private ServerSocket sSocket;	// ServerSocket used for accepting new connection
	private Hashtable outputStreams = new Hashtable();	// Mapping from sockets to DataOutputStreams

	// Constructor with while-accept loop
	public Server(int port) throws IOException{
		listen(port);	//Listen to InputStream
	}	

	private void listen(int port) throws IOException{
		sSocket = new ServerSocket(port);	// Create a Server Socket
		System.out.println("Listening on " + sSocket);	// Notify of new Server Socket
		
		// Continuously accept connections for duration of program
		while(true){
			Socket s = sSocket.accept();	// Get next incoming connection
			System.out.println("Connection from " + s);	// Notify of new connection
			DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());	// Create DataOutputStream
			outputStreams.put(s, dataOut);	// Store the stream
			new ServerThread(this, s);	// Create new thread for this connection
		}	
	}
	
	// Get an enumeration of all OutputStreams
	Enumeration getOutputStreams(){return outputStreams.elements();}
	
	// Send message to all clients
	void SendToAll(String message){
		// Synchronize in case another thread is calling removeConnection()
		synchronized(outputStreams){
			// For each client...
			for(Enumeration e = getOutputStreams(); e.hasMoreElements();){
				DataOutputStream dataOut = (DataOutputStream)e.nextElement();	// Get the output stream
				
				// Send the message
				try{
					dataOut.writeUTF(message);
				} catch(IOException ioe){System.out.println(ioe);}
			}
		}
	}
	
	void removeConnection(Socket s){
		// Synchronize so we don't interfere with sendToAll()
		synchronized(outputStreams){
			System.out.println("Removing connection to " + s);	// Inform that client left
			outputStreams.remove(s);	// Remove client (socket) from hash table
			
			// Make sure connection is closed
			/*try{
				s.close();
			} catch(IOException ioe){
				System.out.println("Error closing " + s);
				ioe.printStackTrace();			
			}*/
		}
	}
		
	public static void main(String args[]) throws IOException{
		new Server(8080);	// Create a server object on port 8080
	}	
}
