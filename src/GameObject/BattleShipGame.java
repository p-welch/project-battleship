package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;



public class BattleShipGame{

	public int numPlayers;
	public Map<Integer, Player> players;

	//public double timeStamp;

	public Ruleset rules;
	//public Player currentPlayer;

	//Constructors
	public BattleShipGame(int np){
		if (np > 1){
			this.numPlayers = np;
		}
		else{
			this.numPlayers = 2;
		}

		players = new HashMap<>();
		//timeStamp = System.currentTimeMillis();
	}

	public BattleShipGame(){
		numPlayers = 2;

		players = new HashMap<>();
		//timeStamp = System.currentTimeMillis();
	}


	//Add Player to Map
	public void addPlayer(int p1, String name, int x, int y, List<Ship> inputShips){
		players.put(p1, new Player(p1, name, x, y, inputShips));
	}


	public void testInheritance(){
		System.out.println("I am a game");
	}

	private void initializeHitBoard(){ //necessary?

	}

	//Mass initialize based on list of coordinates, no player input requested
	public void setBoard(int playerCode, List<BattleCoordinates> coords){
		Player temp = players.get(playerCode);
		if (temp == null){
			System.out.println("PlayerID: " + playerCode + " not found.");
		}
		else{
			temp.setPlayerShips(coords);
		}

	}

	//Set one ship on board at a time
	private boolean setOneInput(int playerCode, BattleCoordinates c){
		Player temp = players.get(playerCode);

		if (temp == null){
			System.out.println("PlayerID: " + playerCode + " not found.");
		}
		else{
			return temp.setOneShip(c);
		}

		return false;
	}

	public void askForPlacements(int playerCode){
		Player tempPlayer = players.get(playerCode);

		if (tempPlayer == null){
			System.out.println("Error, player not found");
			return;
		}
		
		for (Ship s : tempPlayer.getShipList()){

			BattleCoordinates playerInput;
			//boolean loopFlag = true;

			while (true){
				int x = getNumber("For " + s + ", please enter an x coordinate", tempPlayer.getBoardCols());
				int y = getNumber("Please enter a y coordnate: ", tempPlayer.getBoardRows());
				
				Compass direction = getDirection("\nPlease enter a cardinal direction ('N', 'E', 'W', 'S')");

				//BattleCoordinates tempCoord = new BattleCoordinates()
				playerInput = new BattleCoordinates(s, new Point(x, y), direction);
				System.out.println(playerInput);
				if (setOneInput(playerCode, playerInput)){
					//Put to hashmap

					break;
				}
				System.out.println("Invalid entry, please try again");

			}

			tempPlayer.printPlayerShipBoard();

		}
//introduce for loop
	}


	// public List<Ship> getPlayer1List(){
	// 	return getShipList(this.player1);
	// }

	// public List<Ship> getPlayer2List(){
	// 	return getShipList(this.player2);
	// }

	public Player getPlayer(int playerCode){
		return this.players.get(playerCode);
	}

	public void printPlayerHitBoard(int playerCode){
		Player temp = this.players.get(playerCode);
		if (temp != null){
			temp.printPlayerHitBoard();
		}
		else{
			System.out.println("Player not found.");
		}
	}

	public void printPlayerShipBoard(int playerCode){
		Player temp = this.players.get(playerCode);
		if (temp != null){
			temp.printPlayerHitBoard();
		}
		else{
			System.out.println("Player not found.");
		}
	}

	public List<Ship> getShipList(int playerCode){
		Player p = this.players.get(playerCode);
		return p.getShipList();
	}

	private static int getNumber(String prompt, int upperLimit)
	{
		Scanner scanner = new Scanner(System.in);
		while (true){
			System.out.println(prompt + " less than " + upperLimit + ": ");
			String input = scanner.next();
			try {
				int temp = Integer.parseInt(input);
				if ((temp >= 0) && (temp < upperLimit)){
					return temp;
				}
				else{
					System.out.println("Out of bounds");
				}
				
			} catch (NumberFormatException e){
				System.out.println("Please enter an integer.");
			}
		}
	}

	private static Compass getDirection(String prompt){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println(prompt + ": ");
			String input = scanner.next();
			try {
				return Compass.valueOf(input.toUpperCase());
			} catch (IllegalArgumentException e){
				System.out.println("Please enter a valid direction ('N', 'E', 'W', 'S'): ");
			}
		}
	}

	public static int askForCoordinate(String prompt, int upperLimit){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println(prompt + upperLimit);
			String input = scanner.next();
			try {
				int temp = Integer.parseInt(input);
				if ((temp >= upperLimit) || (temp < 0)){
					throw new Exception("");
				}
				else{
					return Integer.parseInt(input);					
				}

			} catch (Exception e){
				System.out.println("Please enter a valid coordinate...");
			}
		}
	}

	public static int askForOpponent(String prompt, List<Player> opponents){
		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		sb.append("Options: ");
		for (int i = 0; i < opponents.size(); i++){
			sb.append(opponents.get(i).getPlayerName() + " (" + (i + 1) + ")");
			if (i != (opponents.size() - 1)){

			}
			else{
				sb.append(", ");
			}
		}

		while(true){
			System.out.println(prompt + " ");
			System.out.println(sb);
			String input = scanner.next();
			try{
				return Integer.parseInt(input);
			} catch (NumberFormatException e){
				System.out.println("Please choose a valid option number.");
			}

		}
	}

	public Point getAttackCoordinate(Player target){
		int x = askForCoordinate("Enter an x coordinate between 0 and ", target.getBoardCols());
		int y = askForCoordinate("Enter a y coordinate between 0 and ", target.getBoardRows());

		return (new Point(x, y));
	}


	public boolean conductPlayerTurn(){

		int playerID = rules.getPlayerTurn();
		System.out.println("\n================================================");
		System.out.println("Player " + players.get(playerID).getPlayerName() + " turn...");
		if (playerID < 0){
			System.out.println("Error in conductPlayerTurn()");
			return false;
		}
		else{
			Player currentPlayer;
			try{
				currentPlayer = players.get(playerID);
				List<Player> options = new ArrayList<>();
				// Set<Integer> attackable = players.keySet();
				int index = 0;
				for (Player p : players.values()){
					if (p.getPlayerID() != currentPlayer.getPlayerID()){
						options.add(p);
					}
				}
				//who to attack?
				Player target;
				int playerIndex = 0;
				if (numPlayers > 2){
					int temp = askForOpponent("Select an option. ", options);
				}

				target = options.get(playerIndex);
				target.printPlayerHitBoard();

				//now choose square
				while(true){
					Point coordinate = getAttackCoordinate(target);	
					if (attackPosition(coordinate, target)){
						break;
					}
					else{
						System.out.println("Please try again");
					}
				}
				System.out.println("--------------------------");
				System.out.println("Updated...");
				target.printPlayerHitBoard();


			}
			catch(IndexOutOfBoundsException e){
				System.out.println(e);
				return false;
			}

			this.rules.advanceTurn();
			// currentPlayer.printPlayerHitBoard();

			return true;


			//conduct player turn, ask for which player to attack

		}
	}

	public static boolean attackPosition(Point c, Player t){
		return t.attackPosition(c);
	}


}//end solution

