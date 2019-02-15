package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;

public class Player{

	private int playerID;
	private String playerName;

	private Board playerBoard;

	private Map<Integer,Ship> shipList;

	private boolean hasLost;
	



	public Player(int id, String name, int boundsX, int boundsY, List<Ship> sList){
		playerID = id;
		playerName = name;
		shipList = new HashMap<>();
		for (Ship s : sList){
			shipList.put(s.getShipId(), s);
		}

		playerBoard = new Board(boundsX, boundsY);
		hasLost = false;
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

	public String printPlayerHitBoard(){
		return this.playerBoard.encodeHitBoard();
	}

	public String printPlayerShipBoard(){
		return this.playerBoard.encodeShipBoard();
	}

	public int getBoardRows(){
		return this.playerBoard.getRows();
	}

	public int getBoardCols(){
		return this.playerBoard.getCols();
	}

	public Messages attackPosition(Point p){
		return this.playerBoard.attackPosition(p, this.shipList);
	}

	public Messages[] setPlayerShips(List<BattleCoordinates> coords){
		return playerBoard.initializeBoatPlacements(coords);

	}

	public Messages setOneShip(BattleCoordinates c){
		return playerBoard.initializeOneBoat(c);
	}

	public boolean checkLoss(){
		if (hasLost){
			return true;
		}
		else{
			int count = 0;
			for (Ship s : shipList.values()){
				if (s.isShipSunk()){
					count++;
				}

			}
			if (count >= shipList.size()){
				this.hasLost = true;
				return true;
			}
			else{
				return false;
			}
		}
	}
}

