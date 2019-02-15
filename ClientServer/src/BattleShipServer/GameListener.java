package BattleShipServer;
import java.net.*;
import java.io.*;
import java.util.*;

public class GameListener implements Runnable {
	private Socket connectionSock;
	
	GameListener(Socket sock) {
		this.connectionSock = sock;
	}
	
	public void run() {
		try {
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true) {
				if (serverInput == null) {
					System.out.println("Closing connection for socket " + connectionSock);
					connectionSock.close();
					break;
				}
				
				String serverText = serverInput.readLine();
				
				if (serverText.startsWith("#")) {
					decodeBoardString(serverText.substring(1));
				}				
				else {
					System.out.println(serverText);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	private void decodeBoardString(String encodedString){
        int i = 0;
        while(encodedString.charAt(i) != '#' && i < encodedString.length()){
            if (encodedString.charAt(i) == ','){
				System.out.print("\n");
			}
			else if (encodedString.charAt(i) == '.'){
				System.out.print(" ");
			}

			else{
				System.out.print(encodedString.charAt(i)); 
 
			}
			i++;
        }
    }
	
}