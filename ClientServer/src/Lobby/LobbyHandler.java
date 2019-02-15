package Lobby;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Point;
import GameObject.*;
import GameObject.Messages;


public class LobbyHandler implements Runnable {
	public Socket[] sockets;
	private BufferedReader playerInput;
	private DataOutputStream clientOutput;
	
	private static final double delayTime = 5000;
	
	public LobbyHandler(Socket[] s) {
		this.sockets = s;
	}
	
	public void run() {
	    try {
			if (this.sockets.length == 2){
				GameServer server = new GameServer(sockets);
				server.makeClassic();
				server.setConnections();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
			
	
	}
}