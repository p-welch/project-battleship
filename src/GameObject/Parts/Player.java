package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;

public class Player{

	int playerID;
	String playerName;
	double timeStamp;

	Board playerBoard;

	Map<Integer,Ship> shipList;
	
	//public List<Ship> shipList;



	public Player(int id, String name, int boundsX, int boundsY, List<Ship> sList){
		playerID = id;
		playerName = name;
		timeStamp = System.currentTimeMillis();
		// shipList = new ArrayList<>(sList);
		shipList = new HashMap<>();
		for (Ship s : sList){
			shipList.put(s.getShipId(), s);
		}

		playerBoard = new Board(boundsX, boundsY);
	}

	public void testInheritance(){
		System.out.println("I am a player");
	}

	public String getPlayerName(){
		return this.playerName;
	}

	public int getPlayerID(){
		return this.playerID;
	}

	public List<Ship> getShipList(){
		List<Ship> temp = new ArrayList<>(shipList.values());
		return temp;
	}

	public void printPlayerHitBoard(){
		this.playerBoard.showHitBoard();
	}

	public void printPlayerShipBoard(){
		this.playerBoard.showShipBoard();
	}

	public int getBoardRows(){
		return this.playerBoard.getRows();
	}

	public int getBoardCols(){
		return this.playerBoard.getCols();
	}

	public boolean attackPosition(Point p){
		return this.playerBoard.attackPosition(p, this.shipList);
	}

	public void setPlayerShips(List<BattleCoordinates> coords){
		System.out.println("DEBUG: playerID -> " + playerID);
		playerBoard.initializeBoatPlacements(coords);

	}

	public boolean setOneShip(BattleCoordinates c){
		System.out.println("Debug: playerID->" + playerID);
		return playerBoard.initializeOneBoat(c);
	}

	// public boolean initializeShipHealth(){
	// 	this.shipHealth.put()
	// }

	public void setTime(){
		this.timeStamp = System.currentTimeMillis();
	}

	

}//end solution

