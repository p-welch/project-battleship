package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

//import Parts.*;

import java.awt.Point;


public class BattleCoordinates{
	// public static enum compass{ N, E, S, W};

	private Ship ship;

	private Point point;
	private Compass direction;

	public BattleCoordinates(Ship s, Point p, Compass d){
		this.ship = s;
		//coord = new Point(p);
		this.point = p;
		this.direction = d;
	}

	public Point getPoint(){
		return this.point;
	}

	public Compass getDirection(){
		return this.direction;
	}

	public Ship getShip(){
		return this.ship;
	}

	@Override
	public String toString(){

		return String.format("Coord: " + point.toString() + ", direction: " + direction.toString() + ", SHIP: " + ship.toString() );

	}



	
}
