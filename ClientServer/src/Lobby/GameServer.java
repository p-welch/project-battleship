package Lobby;
import java.net.*;
import java.io.*;
import java.util.*;

import BattleShipServer.*;
import GameObject.*;

public class GameServer {

    private Socket[] socketList;
	private int numPlayer;
	private BattleShipInterface game;
	private int[] playerIDs;
	
	public GameServer() {
		this.numPlayer = 2;
		socketList = new Socket[numPlayer];
		playerIDs = new int[numPlayer];
		game = new BattleShipInterface(this.numPlayer);
		initializeID();
	}

	public GameServer(Socket[] s) {
		this.numPlayer = s.length;
		this.socketList = s;
		playerIDs = new int[numPlayer];
		game = new BattleShipInterface(this.numPlayer);
		initializeID();
	}
	
	public GameServer(int num) {
		this.numPlayer = num;
		socketList = new Socket[numPlayer];
		playerIDs = new int[numPlayer];
		game = new BattleShipInterface(this.numPlayer);
		initializeID();
	}
	
	private void initializeID() {
		for (int i = 0; i < numPlayer; i++) {
			playerIDs[i] = i+1;
		}
	}

	public void setConnections(){

		try {
			// System.out.println("Waiting for player connections on port 7654.");
			// ServerSocket serverSock = new ServerSocket(7654);
			
			// for (int i = 0; i < numPlayer; i++) {
			// 	Socket connectionSock = serverSock.accept();
			// 	this.socketList[i] = connectionSock;
			// 	System.out.println("Player " + Integer.toString(i+1) + " connected successfully.");
				
			
			// }
				
			for (int i = 0; i < numPlayer; i++) {
				GameHandler handler = new GameHandler(this.socketList[i], game, playerIDs[i]);
				Thread theThread = new Thread(handler);
				theThread.start();
			}
			
			System.out.println("Game running...");
			
			//Socket connectionSock = serverSock.accept();

			while(!game.checkWin()){
				try{
					Thread.sleep(5000);	
				} catch (InterruptedException e){
					
				}

			}
			System.out.println("Closing connections...");
			
			for (int i = 0; i < this.socketList.length; i++) {
				socketList[i].close();
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	private void getConnection() {
		try {
			System.out.println("Waiting for player connections on port 7654.");
			ServerSocket serverSock = new ServerSocket(7654);
			
			for (int i = 0; i < numPlayer; i++) {
				Socket connectionSock = serverSock.accept();
				this.socketList[i] = connectionSock;
				System.out.println("Player " + Integer.toString(i+1) + " connected successfully.");
				
			
			}
			
			for (int i = 0; i < numPlayer; i++) {
				GameHandler handler = new GameHandler(this.socketList[i], game, playerIDs[i]);
				Thread theThread = new Thread(handler);
				theThread.start();
			}
			
			System.out.println("Game running...");
			
			//Socket connectionSock = serverSock.accept();

			while(!game.checkWin()){
				try{
					Thread.sleep(5000);	
				} catch (InterruptedException e){
					
				}

			}
			System.out.println("Closing connections...");
			
			for (int i = 0; i < this.socketList.length; i++) {
				socketList[i].close();
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void makeClassic() {
		this.game = BattleShipInterface.makeClassicGame(playerIDs[0], playerIDs[1]);
	}
	
	public void makeThree() {
		this.game = BattleShipInterface.makeThreePlayerGame(playerIDs[0], playerIDs[1], playerIDs[2]);
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			GameServer server = new GameServer();
			server.makeClassic();
			server.getConnection();
		}
		
		if (args.length > 0) {
			try {
				int n = Integer.parseInt(args[0]);
				if (n > 1) {
					if (n == 3) {
						GameServer server = new GameServer(n);
						server.makeThree();
						server.getConnection();
					}
				}
				else {
					System.out.println("Need at least two players. \n");
				}
			} catch (NumberFormatException e) {
				System.out.println("Must be a number greater than one. \n");
			}
		}	
	}
}