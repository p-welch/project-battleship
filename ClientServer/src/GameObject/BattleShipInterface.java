package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;


public class BattleShipInterface{

	private int numPlayers;
	private Map<Integer, Player> players;
	private Ruleset rules;

	private static final EnumSet<Messages> VALID = EnumSet.of(Messages.VALID, Messages.VALID_PLACEMENT, Messages.HIT, Messages.HIT_SINK, Messages.MISS);
	private static final EnumSet<Messages> INVALID = EnumSet.of(Messages.INVALID_PLACEMENT, Messages.DIRECTION, Messages.OPTION, Messages.REPEAT_MISS, Messages.REPEAT_HIT, Messages.NUMBER, Messages.SHIP_IN_WAY, Messages.OUT_OF_BOUNDS, Messages.PLAYER, Messages.ENTRY);


	public BattleShipInterface(int np){
		if (np > 1){
			this.numPlayers = np;
		}
		else{
			this.numPlayers = 2;
		}

		players = new HashMap<>();
		rules = new Ruleset();
	}

	public BattleShipInterface(){
		numPlayers = 2;

		players = new HashMap<>();
		rules = new Ruleset();
	}
	
	public static BattleShipInterface makeClassicGame(int player1ID, int player2ID){
        BattleShipInterface myGame = new BattleShipInterface();

        List<Ship> shipList1;
        shipList1 = new ArrayList<>();
        shipList1.add(new Ship(2, "Aircraft Carrier"));
        shipList1.add(new Ship(2, "Battleship"));
        shipList1.add(new Ship(3, "Submarine"));
        shipList1.add(new Ship(3, "Cruiser"));
        shipList1.add(new Ship(2, "Destroyer"));

        List<Ship> shipList2;
        shipList2 = new ArrayList<>();
        shipList2.add(new Ship(2, "Aircraft Carrier"));
        shipList2.add(new Ship(2, "Battleship"));
        shipList2.add(new Ship(3, "Submarine"));
        shipList2.add(new Ship(3, "Cruiser"));
        shipList2.add(new Ship(2, "Destroyer"));

        myGame.addPlayer(player1ID, "player one", 3, 3, shipList1);
        myGame.addPlayer(player2ID, "player two", 3, 3, shipList2);

        myGame.addPlayerRules(player1ID);
        myGame.addPlayerRules(player2ID);

        return myGame;
    }
	
	public static BattleShipInterface makeThreePlayerGame(int player1ID, int player2ID, int player3ID){
        BattleShipInterface newGame = new BattleShipInterface(3);

        List<Ship> shipList1;
        shipList1 = new ArrayList<>();
        //shipList1.add(new Ship(5, "Aircraft Carrier"));
        // shipList1.add(new Ship(4, "Battleship"));
        //shipList1.add(new Ship(3, "Submarine"));
        //shipList1.add(new Ship(3, "Cruiser"));
        shipList1.add(new Ship(2, "Destroyer"));

        List<Ship> shipList2;
        shipList2 = new ArrayList<>();
        // shipList2.add(new Ship(5, "Aircraft Carrier"));
        shipList2.add(new Ship(4, "Battleship"));
        // shipList2.add(new Ship(3, "Submarine"));
        //shipList2.add(new Ship(3, "Cruiser"));
        //shipList2.add(new Ship(2, "Destroyer"));

        List<Ship> shipList3;
        shipList3 = new ArrayList<>();
        // shipList3.add(new Ship(5, "Aircraft Carrier"));
        // shipList3.add(new Ship(4, "Battleship"));
        //shipList3.add(new Ship(3, "Submarine"));
        shipList3.add(new Ship(3, "Cruiser"));
        // shipList3.add(new Ship(2, "Destroyer"));



        newGame.addPlayer(player1ID, "player one", 10, 10, shipList1);
        newGame.addPlayer(player2ID, "player two", 7, 7, shipList2);
        newGame.addPlayer(player3ID, "player three", 5, 5, shipList3);

        newGame.addPlayerRules(player1ID);
        newGame.addPlayerRules(player2ID);
        newGame.addPlayerRules(player3ID, 2);

        return newGame;
    }

	public void addPlayer(int p1, String name, int x, int y, List<Ship> inputShips){
		players.put(p1, new Player(p1, name, x, y, inputShips));
	}

	public void addPlayerRules(int pID){
		this.rules.setPlayerRules(pID);
	}

	public void addPlayerRules(int pID, int numTurns){
		this.rules.setPlayerRules(pID, numTurns);
	}

	public void addPlayerRules(int pID, int numTurns, int duration){
		this.rules.setPlayerRules(pID, duration);
	}

	public int getNumPlayer() {
		return this.numPlayers;
	}
	
	public String getPlayerName(int playerID) {
		return players.get(playerID).getPlayerName();
	}
	
	public Set<Integer> getAllPlayerIDs(){
		return this.players.keySet();
	}
	
	public int getPlayerBoardRows(int playerID) {
		return this.players.get(playerID).getBoardRows();
	}
	
	public int getPlayerBoardCols(int playerID) {
		return this.players.get(playerID).getBoardCols();
	}
	
	public String printPlayerHitBoard(int playerCode){
		Player currPlayer = this.players.get(playerCode);
		if (currPlayer != null){
			return currPlayer.printPlayerHitBoard();
		}
		else{
			return Messages.PLAYER.toString();
		}
	}

	public String printPlayerShipBoard(int playerCode){
		Player currPlayer = this.players.get(playerCode);
		if (currPlayer != null){
			return currPlayer.printPlayerShipBoard();
		}
		else{
			return Messages.PLAYER.toString();
		}
	}

	public List<Ship> getShipList(int playerCode){
		Player p = this.players.get(playerCode);
		return p.getShipList();
	}
	
	public Messages setEntireBoard(int playerCode, List<BattleCoordinates> coords){
		Player temp = players.get(playerCode);
		if (temp == null){
			return Messages.PLAYER;
		}
		else{
			Messages[] responses = temp.setPlayerShips(coords);
			for (int i = 0; i < responses.length; i++) {
				if (!isValidMessage(responses[i])) {
					return Messages.INVALID_PLACEMENT;
				}
			}
			return Messages.VALID_PLACEMENT;
		}
	}

	public Messages setOneShip(int playerCode, BattleCoordinates c){
		Player temp = players.get(playerCode);

		if (temp == null){
			return Messages.PLAYER;
		}
		else{
			return temp.setOneShip(c);
		}
	}
	
	public Messages attackPosition(Point c, int targetPlayerID){
		return players.get(targetPlayerID).attackPosition(c);
	}
	
	public void setPlayerReady(int playerID) {
		this.rules.setPlayerReady(playerID);
	}
	
	public boolean isAllReady() {
		return this.rules.checkAllReady();
	}
	
	public int getPlayerTurnsNum(int playerID) {
		return this.rules.getPlayerTurnsNum(playerID);
	}
	
	public int getCurrentTurnID() {
		return this.rules.getPlayerTurn();
	}
	
	public void advanceTurn() {
		this.rules.advanceTurn();
	}
	
	public boolean checkWin(){
		return (this.rules.checkWin(players));
	}

	public HashSet<Integer> getWinners(){
		if (checkWin()){
			return this.rules.getWinners(players);
		}
		else{
			return new HashSet<Integer>();
		}
	}
	
	public boolean isValidMessage(Messages message) {
		return VALID.contains(message);
	}

}//end solution

