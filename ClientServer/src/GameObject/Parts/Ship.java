package GameObject;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Ship{
	private final String name;
	private final int length; 
	private final int shipID;
	private int shipHealth;


	private static int shipIDCounter = 1;

	public Ship(int size, String n){
		if (size >= 0){
			this.length = size;
			this.shipHealth = size;
			this.shipID = shipIDCounter;
			this.shipIDCounter++;	
		}
		else{
			this.length = 0;
			this.shipHealth = 0;
			shipID = 0;
		}
		name = n;
	}

	public Ship(){
		this.length = 0;
		this.shipID = 0;
		this.name = "";
	}

	public int getShipId(){
		return this.shipID;
	}

	public void damageShip(){
		this.shipHealth -= 1;
	}

	public boolean isShipSunk(){
		if (shipHealth <= 0){
			return true;
		}
		else{
			return false;
		}
	}

	public String getName(){
		return this.name;
	}

	public int getShipHealth(){
		return this.shipHealth;
	}

	public int getShipLength(){
		return this.length;
	}

	@Override
	public String toString(){
		return String.format("Size: " + this.length + ", Name: " + this.name + ", ID: " + shipID);
	}

}//end solution

