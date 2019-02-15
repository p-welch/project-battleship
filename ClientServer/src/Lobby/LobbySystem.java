package Lobby;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import BattleShipServer.*;
import GameObject.*;

public class LobbySystem {

	final static int port = 7654;

	private Queue<Socket> quickPlay;
	private Set<Queue<Socket>> customPlay;


	public LobbySystem(){
		this.quickPlay = new LinkedList<>();
		this.customPlay = new HashSet<>();
	}



	private void listenForConnections(ServerSocket serverSock) {
		try {
			System.out.println("Waiting for player connections on port 7654.");
			Socket connectionSock = serverSock.accept();

			if (!quickPlay.isEmpty()){
				Socket[] sockets = new Socket[2];
				sockets[0] = connectionSock;
				sockets[1] = quickPlay.remove();
				LobbyHandler handler = new LobbyHandler(sockets);
				Thread theThread = new Thread(handler);
				theThread.start();
			}
			else{
				quickPlay.add(connectionSock);
			}
			
			
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		
		System.out.println("The lobby system is running...");
		LobbySystem lobby = new LobbySystem();
		
		try (ServerSocket serverSock = new ServerSocket(port)) {
			while(true){
				lobby.listenForConnections(serverSock);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}