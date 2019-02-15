package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;


public class Board{
	private int rows;
	private int cols;

	private Pos displayBoard[][];

	public class Pos{
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
	}

	public void setDefaultBoard(){
		for (int i = 0; i < this.rows; i++){
			for (int j = 0; j < this.cols; j++){
				this.displayBoard[i][j] = new Pos();
			}
		}
	}

	public String encodeHitBoard(){
        StringBuilder encodedString = new StringBuilder();
        encodedString.append("#");
        for (int i = this.rows - 1; i >= 0; i--){
            for (int j = 0; j < this.cols; j++){
                encodedString.append(displayBoard[i][j].hit);
				encodedString.append(".");
            }
            encodedString.append(",");
        }
        encodedString.append("#");
        return encodedString.toString();
    }
	
	public String encodeShipBoard(){
		StringBuilder encodedString = new StringBuilder();
		encodedString.append("#");
		for (int i = this.rows - 1; i >= 0; i--){
			for (int j = 0; j < this.cols; j++){
				encodedString.append(displayBoard[i][j].boatID);
				encodedString.append(".");
			}
			encodedString.append(",");
		}

		encodedString.append("#");


		return encodedString.toString();
	}

	public Messages initializeOneBoat(BattleCoordinates c){

		int rowAdd = c.getPoint().x;
		int colAdd = c.getPoint().y;

		int displace = c.getShip().getShipLength() - 1;
		switch(c.getDirection()){
			case N:
				colAdd += displace;
				break;
			case E:
				rowAdd += displace;
				break;
			case W:
				rowAdd -= displace;
				break;
			case S:
				colAdd -= displace;
				break;
		}

		if (checkBounds(c.getPoint().y, c.getPoint().x) && checkBounds(colAdd, rowAdd)){
			if (c.getDirection() == Compass.N || c.getDirection() == Compass.S ){

				if (checkVertical(c.getPoint().y, colAdd, rowAdd)){
					placeVertical(c.getPoint().y, colAdd, rowAdd, c.getShip().getShipId());		
					return Messages.VALID_PLACEMENT;	
				}
				else{
					return Messages.SHIP_IN_WAY;
				}

			}
			else if (c.getDirection() == Compass.E || c.getDirection() == Compass.W) {
				if(checkHorizontal(c.getPoint().x, rowAdd, colAdd)){
					placeHorizontal(c.getPoint().x, rowAdd, colAdd, c.getShip().getShipId());
					return Messages.VALID_PLACEMENT;	

				}
				else{
					return Messages.SHIP_IN_WAY;
				}
			}
			else{
				return Messages.DIRECTION;
			}
		}
		else{
			return Messages.OUT_OF_BOUNDS;
		}
	}

	public Messages[] initializeBoatPlacements(List<BattleCoordinates> coords){
		Messages[] responses = new Messages[coords.size()];
		for (int i = 0; i < coords.size(); i++){
			responses[i] = initializeOneBoat(coords.get(i));
		}
		return responses;
	}

	private boolean checkBounds(int newRow, int newCol){
		if (newRow < 0 || newRow >= this.rows){
			return false;
		}
		else if (newCol < 0 || newCol >= this.cols){
			return false;
		}
		else{
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

	public Messages attackPosition(Point p, Map<Integer,Ship> shipList){
		if ( (p.y < 0) || (p.x < 0) || (p.y >= this.rows) || (p.x >= this.cols)){
			return Messages.OUT_OF_BOUNDS;
		}

		Pos coord = this.displayBoard[p.y][p.x];

		if (coord.boatID ==0){
			if (this.displayBoard[p.y][p.x].hit == Status.MISS){
				return Messages.REPEAT_MISS;
			}
			else{
				
				setHitValue(p.x, p.y, Status.MISS);
				return Messages.MISS;
			}
		}
		else if (coord.boatID <= -1){
			return Messages.ROCK;
		}
		else{
			if (coord.hit == Status.HIT){
				return Messages.REPEAT_HIT;
			}
			else{
				
				setHitValue(p.x, p.y, Status.HIT);
				Ship target = shipList.get(coord.boatID);
				target.damageShip();
				if (target.isShipSunk()){
					return Messages.HIT_SINK;
				}
				else{
					return Messages.HIT;
				}
			}
		}
	}

	public void setHitValue(int x, int y, Status val){
		this.displayBoard[y][x].hit = val;
	}

}

