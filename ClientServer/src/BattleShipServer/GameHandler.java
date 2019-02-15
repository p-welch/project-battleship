package BattleShipServer;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Point;
import GameObject.*;
import GameObject.Messages;

public class GameHandler implements Runnable {
	public Socket connectionSock;
	public BattleShipInterface game;
	public int playerID;
	private BufferedReader playerInput;
	private DataOutputStream clientOutput;
	
	private static final EnumSet<Messages> VALID = EnumSet.of(Messages.HIT, Messages.HIT_SINK, Messages.MISS, Messages.VALID_PLACEMENT, Messages.VALID);
	private static final EnumSet<Messages> INVALID = EnumSet.of(Messages.DIRECTION, Messages.OPTION, Messages.REPEAT_MISS, Messages.REPEAT_HIT, Messages.NUMBER, Messages.SHIP_IN_WAY, Messages.OUT_OF_BOUNDS, Messages.PLAYER, Messages.ENTRY);	
	private static final double delayTime = 5000;
	
	public GameHandler(Socket sock, BattleShipInterface game, int playerID) {
		this.connectionSock = sock;
		this.game = game;
		this.playerID = playerID;
		try {
			playerInput = new BufferedReader(new InputStreamReader(this.connectionSock.getInputStream()));
			clientOutput = new DataOutputStream(this.connectionSock.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void run() {
	    try {
			sendMessage("\n There are " + game.getNumPlayer() + " players in this game. You are player " + playerID + ".\r\n");
			sendMessage("\n You will have " + game.getPlayerTurnsNum(playerID) + " turn(s) every round. \r\n");
			setupBoard();
			double currTime = System.currentTimeMillis();
			boolean wishToWait = false;
			int response;
			
			sendMessage("\n Waiting for other players to set up boards. \r\n");
			while (!game.isAllReady()	) {
				Thread.sleep(500);
			}
			
			sendMessage("\n All players have finished setting up their boards. \r\n");
			
			while (!this.game.checkWin()) {
				if (System.currentTimeMillis() - currTime > delayTime) {
					if (game.getCurrentTurnID() != playerID && !wishToWait) {
						sendMessage("\n Enter (0) to wait for turn, (1) to see your own ship placements, or (2) to see current boardstate of a player \r\n");
						response = getNumber("\n" + game.getPlayerName(playerID) + ": Please enter a number", 0, 2);
						if (response == 0) {
							wishToWait = true;
						}
						else if (response == 1) {
							sendOwnShipBoard();
						}
						else {
							chooseHitBoard();
						}
						
						currTime = System.currentTimeMillis();
					}
					else if (game.getCurrentTurnID() == playerID) {
						sendMessage("\n \n It is now your turn \r\n");
						sendMessage("\n Enter (0) to attack a player, (1) to see your own ship placements, or (2) to see the current boardstate of your opponents \r\n");
						response = getNumber("\n" + game.getPlayerName(playerID) + ": Please enter a number", 0, 2);
						if (response == 0) {
							attackOpponent();
							game.advanceTurn();
						}
						else if (response == 1) {
							sendOwnShipBoard();
						}
						else {
							chooseHitBoard();
						}
					}	
				}
			}
			announceResult();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	private void announceResult() {
		HashSet<Integer> winners = game.getWinners();
		if (winners.contains(playerID)) {
			sendMessage("\n You have won the game! \r\n");
		}
		else {
			sendMessage("\n You have been defeated! \r\n");
		}
	}
	
	private void chooseHitBoard() {
		List<Integer> options = returnPossiblePlayerID(); 
		if (options.size() == 1) {
			sendHitBoard(options.get(0));
		}
		else {
			sendMessage(buildStringFromOptions("Select a player's board to view: ", options) + "\r\n");
			int response = getNumber("\n" + game.getPlayerName(playerID) + ": Please enter a number", 1, options.size());
			int targetPlayerID = options.get(response - 1);
			sendHitBoard(targetPlayerID);
		}
	}
	
	private String buildStringFromOptions(String prompt, List<Integer> options){
		StringBuilder sb = new StringBuilder();
		sb.append(prompt);
		for (int i = 0; i < options.size(); i++){
			sb.append(game.getPlayerName(playerID) + " (" + (i + 1) + ")");
			if (i == (options.size() - 1)){
				sb.append("? ");
			}
			else{
				sb.append(", ");
			}
		}

		return sb.toString();
	}
	
	private void attackOpponent() {
		List<Integer> opponents = returnPossiblePlayerID();
		int targetPlayerID;
		
		if (opponents.size() == 1) {
			targetPlayerID = opponents.get(0);
		}
		else {
			targetPlayerID = chooseOpponent(opponents);
		}
		
		sendHitBoard(targetPlayerID);
		attackTargetPlayer(targetPlayerID);
		sendMessage("--------------------------- \r\n");
		sendMessage("Updated \r\n");
		sendHitBoard(targetPlayerID);
	}
	
	private void attackTargetPlayer(int targetPlayerID) {
		Messages message;
		do {
			Point coordinate = getAttackCoordinateForTarget(targetPlayerID);
			message = game.attackPosition(coordinate, targetPlayerID);
			sendMessage("Message: " + message);
		} while (!isValidMessage(message));
	}
	
	private Point getAttackCoordinateForTarget(int targetPlayerID) {
		int x = getNumber("\n Please enter an x coordinate", 0, game.getPlayerBoardCols(playerID) - 1);
		int y = getNumber("\n Please enter an y coordinate", 0, game.getPlayerBoardRows(playerID) - 1);
		return new Point(x, y);
	}
	
	private int chooseOpponent(List<Integer> opponents) {
		sendMessage(buildStringFromOptions("Select an opponent to attack: ", opponents) + "\r\n");
		int response = getNumber("\n" + game.getPlayerName(playerID) + ": Please enter a number", 1, opponents.size());
		return opponents.get(response - 1);
	}
	
	private List<Integer> returnPossiblePlayerID() {
		boolean myTurn = (playerID == this.game.getCurrentTurnID());
		List<Integer> options = new ArrayList<>();
		for (int pID : this.game.getAllPlayerIDs()){
			if (!myTurn) {
				options.add(pID);
			}
			else if (myTurn && pID != playerID) {
				options.add(pID);
			}
		}
		return options;
	}
	
	private void setupBoard() {
		try {
			sendMessage("\n You have a board sized " + game.getPlayerBoardRows(playerID) + " x " + game.getPlayerBoardCols(playerID) + ".\r\n");
			sendMessage("\n You need to place the following list of ships on the board: \r\n\n");
			
			List<Ship> shipList = game.getShipList(playerID);
			
			for (Ship s : shipList) {
				sendMessage(s.toString() + "\r\n");
			}
			askForPlacements(shipList);
			sendMessage("\n " + game.getPlayerName(playerID) + ": Here is your final placements for your ships. \r\n");
			sendOwnShipBoard();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void askForPlacements(List<Ship> shipList){
		try {
			for (Ship s: shipList) {
				BattleCoordinates input;
				Messages response;
				do {
					sendOwnShipBoard();
					int x = getNumber("For " + s.getName() + ", please enter an x coordinate", 0, game.getPlayerBoardCols(playerID)-1);
					int y = getNumber("Please enter a y coordinate", 0, game.getPlayerBoardRows(playerID)-1);
					Compass direction = getDirection("Please enter a cardinal direction ('N', 'E', 'W', 'S')");
				
					input = new BattleCoordinates(s, new Point(x, y), direction);
					
					response = game.setOneShip(playerID, input);
					sendMessage(response.toString());
				} while (!isValidMessage(response)); 
			}
			game.setPlayerReady(this.playerID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private int getNumber(String prompt, int lowerLimit, int upperLimit) {
		try {
			Messages response = Messages.VALID;
			do {
				sendMessage(prompt + " between " + lowerLimit + " and "+ upperLimit + ": \r\n");
				String inputText = playerInput.readLine().trim();
				try {
					int n = Integer.parseInt(inputText);
					if ((n >= lowerLimit) && (n <=  upperLimit)){
						return n;
					}
					else{
						response = Messages.OUT_OF_BOUNDS;
						sendMessage(response.toString());
					}
				
				} catch (NumberFormatException e){
					sendMessage(Messages.NUMBER.toString());
				}
			} while (!isValidMessage(response));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	private Compass getDirection(String prompt) {
		try {
			Messages response = Messages.VALID;
			do {
				sendMessage(prompt + ": \r\n");
				String inputText = playerInput.readLine().trim();
				try {
					return Compass.valueOf(inputText.toUpperCase());
				} catch (IllegalArgumentException e){
					response = Messages.DIRECTION;
					sendMessage(response.toString());
				}
			} while (!isValidMessage(response));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private void sendOwnShipBoard() {
		try {
			sendMessage("\n");
			sendMessage(this.game.printPlayerShipBoard(playerID));
			sendMessage("\r\n\n");
	    } catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void sendHitBoard(int targetPlayerID) {
		try {
			sendMessage("\n");
			sendMessage(this.game.printPlayerHitBoard(targetPlayerID));
			sendMessage("\r\n\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean isValidMessage(Messages message) {
		return VALID.contains(message);
	}
	
	private void sendMessage(String message) {
		try {
			this.clientOutput.writeBytes(message);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}