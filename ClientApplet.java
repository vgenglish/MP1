/****************************************************************************
 *	CS-498 MP1
 *	Feb. 25 2014
 *	Team 05: english7, 
 *	
 *	ClientApplet.java: Class responsible for creating the client applet (GUI)
 ****************************************************************************/
 
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientApplet extends Applet{
	public void init(){
		String host = getParameter("host");
		int port = 8080;
		setLayout(new BorderLayout());
		add("Center", new Client(host, port));	
	}
}