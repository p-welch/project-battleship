package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;


public class Board{
	// public static enum status{ MISS, HIT, BLANK};
	static final String hitString = "Boat hit!";
	static final String missString = "Miss...";
	static final String repeatedHitString = "Boat already hit...";
	static final String repeatedMissString = "Already missed here...";

	int rows;
	int cols;

	Pos displayBoard[][];

	public class Pos{
		//public static enum status{ MISS, HIT, EMPTY};

		Status hit;
		int boatID;

		public Pos(){
			hit = Status.BLANK;
			boatID = 0;
		}

		public int boatID(){
			return this.boatID;
		}


	}

	public Board(int x, int y){
		this.rows = y;
		this.cols = x;

		displayBoard = new Pos[this.rows][this.cols];
		setDefaultBoard();
		//debugCheckBoard();
	}

	public void setDefaultBoard(){
		for (int i = 0; i < this.rows; i++){
			for (int j = 0; j < this.cols; j++){
				this.displayBoard[i][j] = new Pos();
				//this.displayBoard[i][j].hit = Status.BLANK; 
				//this.displayBoard[i][j].boatID = 0;
			}
		}
	}


	public void showShipBoard(){
		for (int i = this.rows - 1; i >= 0; i--){
			for (int j = 0; j < this.cols; j++){
				//System.out.println( i + ", " + j + ", " + displayBoard[i][j].hit + " sID: " + displayBoard[i][j].boatID);
				System.out.print(" " + displayBoard[i][j].boatID + " "); 
			}
			System.out.println(" ");
		}
	}

	public void showHitBoard(){
		for (int i = this.rows - 1; i >= 0; i--){
			for (int j = 0; j < this.cols; j++){
				//System.out.println( i + ", " + j + ", " + displayBoard[i][j].hit + " sID: " + displayBoard[i][j].boatID);
				System.out.print(" " + displayBoard[i][j].hit + " "); 
			}
			System.out.println();
		}
	}

	public boolean initializeOneBoat(BattleCoordinates c){
		// System.out.println(c.ship);
		// System.out.println("Point y: " + c.point.y);
		// System.out.println("Point x: " + c.point.x);
		// System.out.println("Direction: " + c.direction);


		int rowAdd = c.point.x;
		int colAdd = c.point.y;

		int displace = c.ship.length - 1;
		switch(c.direction){
			case N:
				colAdd += displace;
				break;
			case E:
				rowAdd += displace;
				break;
			case W:
				rowAdd -= displace;
				// rowAdd++;
				break;
			case S:
				colAdd -= displace;
				// colAdd++;
				break;
		default:
			System.out.println("Error in default switch case");
			return false;
			//break;

		}

		// System.out.println("Displace: " + displace);
		// System.out.println("rowAdd: " + rowAdd);
		// System.out.println("colAdd: " + colAdd);

		if (checkBounds(c.point.y, c.point.x) && checkBounds(colAdd, rowAdd)){
			//boolean checkPlace = false;
			if (c.direction == Compass.N || c.direction == Compass.S ){

				if (checkVertical(c.point.y, colAdd, rowAdd)){
					placeVertical(c.point.y, colAdd, rowAdd, c.ship.shipID);		
					// System.out.println("Added (placeCol)");	
					return true;	
				}
				else{
					System.out.println("Ship in way");
				}

			}
			else if (c.direction == Compass.E || c.direction == Compass.W) {
				if(checkHorizontal(c.point.x, rowAdd, colAdd)){
					placeHorizontal(c.point.x, rowAdd, colAdd, c.ship.shipID);
					// System.out.println("Added (placeRow)");
					return true;	

				}
				else{
					System.out.println("Ship in way");
				}
			}
			else{
				System.out.println("Invalid direciton");
			}
		}
		else{
			System.out.println("Out of bounds");
		}
		return false;

	}

	public void initializeBoatPlacements(List<BattleCoordinates> coords){
		for (BattleCoordinates c : coords){
			initializeOneBoat(c);
		}

		// debugCheckBoard();
		// showHitBoard();
	}

	// 	public void initializeBoatPlacements(List<BattleCoordinates> coords){
	// 	for (BattleCoordinates c : coords){
	// 		System.out.println(c.ship);
	// 		System.out.println("Point y: " + c.point.y);
	// 		System.out.println("Point x: " + c.point.x);
	// 		System.out.println("Direction: " + c.direction);


	// 		int rowAdd = c.point.x;
	// 		int colAdd = c.point.y;

	// 		int displace = c.ship.length;
	// 		switch(c.direction){
	// 			case N:
	// 				colAdd += displace;
	// 				break;
	// 			case E:
	// 				rowAdd += displace;
	// 				break;
	// 			case W:
	// 				rowAdd -= displace;
	// 				break;
	// 			case S:
	// 				colAdd -= displace;
	// 				break;
	// 		default:
	// 			System.out.println("Error in default switch case");
	// 			//return;
	// 			break;

	// 		}

	// 		System.out.println("Displace: " + displace);
	// 		System.out.println("rowAdd: " + rowAdd);
	// 		System.out.println("colAdd: " + colAdd);

	// 		if (checkBounds(c.point.x, c.point.y) && checkBounds(rowAdd, colAdd)){
	// 			//boolean checkPlace = false;
	// 			if (c.direction == Compass.N || c.direction == Compass.S ){

	// 				if (checkVertical(c.point.y, colAdd, rowAdd)){
	// 					placeVertical(c.point.y, colAdd, rowAdd, c.ship.shipID);		
	// 					System.out.println("Added (placeCol)");		
	// 				}
	// 				else{
	// 					System.out.println("Ship in way");
	// 				}

	// 			}
	// 			else if (c.direction == Compass.E || c.direction == Compass.W) {
	// 				if(checkHorizontal(c.point.x, rowAdd, colAdd)){
	// 					placeHorizontal(c.point.x, rowAdd, colAdd, c.ship.shipID);
	// 					System.out.println("Added (placeRow)");		

	// 				}
	// 				else{
	// 					System.out.println("Ship in way");
	// 				}
	// 			}
	// 			else{
	// 				System.out.println("Invalid direciton");
	// 			}
	// 		}
	// 		else{
	// 			System.out.println("Out of bounds");
	// 		}

			

	// 	}
	// 	debugCheckBoard();
	// 	showHitBoard();
	// }

	private boolean checkBounds(int newRow, int newCol){
		// System.out.println("inside checkBounds: " + newRow + " " + newCol );
		if (newRow < 0 || newRow >= this.rows){
			// System.out.println("row false");
			return false;
		}
		else if (newCol < 0 || newCol >= this.cols){
			// System.out.println("col false");
			return false;
		}
		else{
			// System.out.println("true");
			return true;
		}
	}

	public int getRows(){
		return this.rows;
	}

	public int getCols(){
		return this.cols;
	}

	private boolean checkHorizontal(int x, int y, int row){
		int start = (x < y) ? x : y;
		int end = (x > y) ? x : y;
		// System.out.println("DEBUG CHECK HORIZONTAL");

		if (x == y){
			return false;
		}

		for (int i = start; i <= end; i++){
			if (displayBoard[row][i].boatID != 0 ){
				return false;
			}
		}

		return true;
	}

	private boolean checkVertical(int x, int y, int col){
		int start = (x < y) ? x : y;
		int end = (x > y) ? x : y;

		if (x == y){
			return false;
		}

		for (int i = start; i <= end; i++){
			if (displayBoard[i][col].boatID != 0 ){
				return false;
			}
		}
		return true;
	}

	private void placeHorizontal(int x, int y, int row, int shipID){
		int start = (x < y) ? x : y;
		int end = (x > y) ? x : y;

		for (int i = start; i <= end; i++){
			displayBoard[row][i].boatID = shipID;
		}

	}

	private void placeVertical(int x, int y, int col, int shipID){
		int start = (x < y) ? x : y;
		int end = (x > y) ? x : y;

		for (int i = start; i <= end; i++){
			displayBoard[i][col].boatID = shipID;
		}
	}

	public boolean attackPosition(Point p, Map<Integer,Ship> shipList){
		if ( (p.y < 0) || (p.x < 0) || (p.y >= this.rows) || (p.x >= this.cols)){
			System.out.println("Out of bounds, attackPosition()");
			return false;
		}

		Pos coord = this.displayBoard[p.y][p.x];

		// if (this.displayBoard[p.y][p.x].boatID == 0){
		if (coord.boatID ==0){
			if (this.displayBoard[p.y][p.x].hit == Status.MISS){
				System.out.println(repeatedMissString);
				return false;
			}
			else{
				System.out.println(missString);
				setHitValue(p.x, p.y, Status.MISS);
				return true;
			}
		}
		else if (coord.boatID <= -1){
		// else if (this.displayBoard[p.y][p.x].boatID <= -1){
			//Future rock implementation
			return false;
		}
		else{
			if (coord.hit == Status.HIT){
			// if (this.displayBoard[p.y][p.x].hit == Status.HIT){
				System.out.println(repeatedHitString);
				return false;
			}
			else{
				System.out.println(hitString);
				setHitValue(p.x, p.y, Status.HIT);
				//damage ship
				Ship target = shipList.get(coord.boatID);
				target.damageShip();
				if (target.isShipSunk()){
					System.out.println("You sunk the " + target.getName());
				}
				else{
					System.out.println("No ship was sunk...");
				}


				return true;
			}
		}
	}


	// public void setHitBlank(int x, int y){
	// 	setHitValue(x, y, Status.BLANK);

	// }

	// public void setHitHit(int x, int y){
	// 	setHitValue(x, y, Status.HIT);
	// }

	// public void setBoxMiss(int x, int y){
	// 	setHitValue(x, y, Status.MISS);
	// }

	public void setHitValue(int x, int y, Status val){
		this.displayBoard[y][x].hit = val;
	}

	public void testInheritance(){
		System.out.println("I am a board");
	}

}//end solution

