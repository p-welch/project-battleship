package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Ruleset{
	Map<Integer,Parameters> playerRules;
	List<Integer> turnOrder;
	int turnIndex;

	int turnCount;

	double timeStamp;

	//int repeats;

	boolean terrainOn;

	public class Parameters{
		final int numTurns;
		final double duration;

		public Parameters(){
			numTurns = 1;
			duration = 180;
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
		}

		@Override
		public String toString(){
			return String.format("Number of turns: " + numTurns + ", duration of turn: " + duration);
		}

	}

	public Ruleset(boolean t){
		playerRules = new HashMap<>();	
		terrainOn = t;	
		turnOrder = new ArrayList<>();
		turnIndex = 0;

		turnCount = 1;

	}

	public Ruleset(){
		playerRules = new HashMap<>();	
		terrainOn = false;	
		turnOrder = new ArrayList<>();
		turnIndex = 0;
	}

	public void setPlayerRules(int playerID, int n, int d){
		Parameters temp = new Parameters(n, d);
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

	//double check this, source of out of bounds excpetion if no players
	public int getPlayerTurn(){
		try{
			int temp = this.turnOrder.get(turnIndex);
			return temp;
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

	public void testInheritance(){
		System.out.println("I am a ruleset");
	}

	// @Override
	// public String toString(){
	// 	return String.format("P1-> time: " + this.p1Time + ", number of turns: " + this.p1 + "\nP2-> time: " + this.p2Time + ", number of turns: " + this.p2);
	// }

}//end solution

