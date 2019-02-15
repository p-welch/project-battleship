package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Ruleset{
	private Map<Integer,Parameters> playerRules;
	private List<Integer> turnOrder;
	private int turnIndex;

	private int turnCount;

	private int lossLimit;
	private boolean terrainOn;
	private boolean gameOver;
	private boolean allReady;

	public class Parameters{
		final int numTurns;
		final double duration;
		
		boolean ready;

		public Parameters(){
			numTurns = 1;
			duration = 180;
			ready = false;
		}

		public Parameters(int n){
			if (n > 0){
				numTurns = n;
			}
			else{
				numTurns = 1;
			}
			duration = 180;
			ready = false;
		}

		public Parameters(int n, int d){
			if (n > 0 && d > 10){
				numTurns = n;
				duration = d;
			}
			else{
				numTurns = 1;
				duration = 180;
			}
			ready = false;
		}

		@Override
		public String toString(){
			return String.format("Number of turns: " + numTurns + ", duration of turn: " + duration);
		}
		
		public void setReady(){
			this.ready = true;
		}
	
		public boolean isReady(){
			return this.ready;
		}
	}

	public Ruleset(){
		playerRules = new HashMap<>();	
		terrainOn = false;	
		turnOrder = new ArrayList<>();
		turnIndex = 0;

		turnCount = 1;
		lossLimit = 1;
		gameOver = false;
		allReady = false;
	}

	public void setLossLimit(int n){
		if (n > 1 && n < playerRules.size()){
			this.lossLimit = n;
		}
		else{
			this.lossLimit = 1;
		}
	}

	public void setPlayerRules(int playerID, int n, int d){
		Parameters temp = new Parameters(n, d);
		this.playerRules.put(playerID, temp);
		for (int i = 0; i < temp.numTurns; i++){
			this.turnOrder.add(playerID);
		}
	}

	public void setPlayerRules(int playerID, int n){
		Parameters temp = new Parameters(n);
		this.playerRules.put(playerID, temp);
		for (int i = 0; i < temp.numTurns; i++){
			this.turnOrder.add(playerID);
		}
	}

	public void setPlayerRules(int playerID){
		Parameters temp = new Parameters();
		this.playerRules.put(playerID, temp);
		this.turnOrder.add(playerID);
	}

	public int getPlayerTurnsNum(int playerID){
		Parameters playerParam;
		playerParam = playerRules.get(playerID);
		if (playerParam == null){
			return 1;
		}
		else{
			return playerParam.numTurns;
		}
	}

	public double getPlayerDuration(int playerID){
		Parameters playerParam;
		playerParam = playerRules.get(playerID);
		if (playerParam == null){
			return 180;
		}
		else{
			return playerParam.duration;
		}
	}

	public int getPlayerTurn(){
		try{
			int playerID = this.turnOrder.get(turnIndex);
			return playerID;
		}
		catch(IndexOutOfBoundsException e){
			return -1;
		}
		
	}
	public void advanceTurn(){
		this.turnIndex++;
		if (this.turnIndex >= this.turnOrder.size()){
			this.turnIndex = 0;
		}
	}

	public boolean checkWin(Map<Integer, Player> players){
		if (gameOver){
			return true;
		}
		else{
			int count = 0;
			for (Player p : players.values()){
				if (p.checkLoss()){
					count++;
				}
			}
			if (count >= lossLimit){
				gameOver = true;
				return true;
			}
			else{
				return false;
			}
		}

	}

	public HashSet<Integer> getWinners(Map<Integer, Player> players){
		HashSet<Integer> winners = new HashSet<>();
		for (Player p : players.values()){
			if (!p.checkLoss()){
				winners.add(p.getPlayerID());
			}
			
		}
		return winners;
	}
	
	public void setPlayerReady(int playerID) {
		playerRules.get(playerID).setReady();
	}
	
	public boolean checkAllReady(){
		if (allReady){
			return true;
		}
		else{
			for(Parameters p : playerRules.values()){
				if (!p.isReady()) {
					return false;
				}
			}
			allReady = true;
			return true;
		}
	}
}

