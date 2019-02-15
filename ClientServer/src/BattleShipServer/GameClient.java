package BattleShipServer;
import java.net.*;
import java.io.*;
import java.util.*;

public class GameClient {
	
	public static void main(String[] args) {
		try {
			String hostname = "localhost";
			int port = 7654;
			boolean myTurn = true;
			System.out.println("Connecting to game server on port " + port);
			Socket connectionSock = new Socket(hostname, port);
			
			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());
			
			System.out.println("Connection made.");
			
			GameListener listener = new GameListener(connectionSock);
			Thread theThread = new Thread(listener);
			theThread.start();
			
			Scanner keyboard = new Scanner(System.in);
			while (serverOutput != null) {
				String data = keyboard.nextLine();
				if (!myTurn) {
					System.out.println("Please wait for your turn.");
				}
				else if (data.equals("quit")) {
					serverOutput = null;
				}
				else if (!data.isEmpty()) {
					serverOutput.writeBytes(data + "\n");
				}
			}
			
			System.out.println("Connection lost.");
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}