

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Point;


import GameObject.*;
// import Parts.*;

//import GameObject.Parts.Ship;


public class Main{

	static final int player1ID = 101;
	static final int player2ID = 202;
	//List<BattleCoordinates>



	//static List<Ship> shipList;
	public static void main (String[] args) throws java.lang.Exception
	{

		Point test = new Point(2, 3);
		System.out.println(test);
		//BattleCoordinates test2 = new BattleCoordinates(new Point(1,1), BattleCoordinates.direction.S);
		//System.out.println(test2);


		//BattleShipGame.BattleCoordinates test2 = new BattleCoordinates(new Point(1,1), NORTH);

		List<Ship> shipList1;
		shipList1 = new ArrayList<>();
		shipList1.add(new Ship(5, "Aircraft Carrier"));
		shipList1.add(new Ship(4, "Battleship"));
		shipList1.add(new Ship(3, "Submarine"));
		shipList1.add(new Ship(3, "Cruiser"));
		shipList1.add(new Ship(2, "Destroyer"));

		List<Ship> ship = new ArrayList<>();
		ship.add(new Ship(5, "Five"));

		// List<Ship> shipList2;
		// shipList2 = new ArrayList<>();
		// shipList2.add(new Ship(5, "Aircraft Carrier"));
		// shipList2.add(new Ship(4, "Battleship"));
		// shipList2.add(new Ship(3, "Submarine"));
		// shipList2.add(new Ship(3, "Cruiser"));
		// shipList2.add(new Ship(2, "Destroyer"));


		// BattleCoordinates[] p1Choice = new BattleCoordinates[shipList1.size()];
		// p1Choice[0] = new BattleCoordinates(new Point(0,0), BattleCoordinates.direction.N);
		// p1Choice[1] = new BattleCoordinates(new Point(1,0), BattleCoordinates.direction.N);
		// p1Choice[2] = new BattleCoordinates(new Point(2,0), BattleCoordinates.direction.N);
		// p1Choice[3] = new BattleCoordinates(new Point(3,0), BattleCoordinates.direction.N);
		// p1Choice[4] = new BattleCoordinates(new Point(4,0), BattleCoordinates.direction.N);

		// BattleCoordinates[] p2Choice = new BattleCoordinates[shipList1.size()];
		// p2Choice[0] = new BattleCoordinates(new Point(0,0), BattleCoordinates.direction.N);
		// p2Choice[1] = new BattleCoordinates(new Point(1,0), BattleCoordinates.direction.N);
		// p2Choice[2] = new BattleCoordinates(new Point(2,0), BattleCoordinates.direction.N);
		// p2Choice[3] = new BattleCoordinates(new Point(3,0), BattleCoordinates.direction.N);
		// p2Choice[4] = new BattleCoordinates(new Point(4,0), BattleCoordinates.direction.N);



		//BattleShipGame myGame = new BattleShipGame(player1ID, 10, 10, shipList1, player2ID, 30, 30, shipList2);
		BattleShipGame myGame = new BattleShipGame(2);
		myGame.addPlayer(player1ID, "player one", 7, 7, ship);
		myGame.addPlayer(player2ID, "player two", 7, 7, ship);

		// myGame.addPlayer(player2ID, "player two", 15, 15, shipList2);

		List<BattleCoordinates> p1Choice = new ArrayList<>();
		p1Choice.add(new BattleCoordinates(shipList1.get(0), new Point(0,0), Compass.valueOf("E")));
		p1Choice.add(new BattleCoordinates(shipList1.get(1), new Point(9,1), Compass.N));
		p1Choice.add(new BattleCoordinates(shipList1.get(2), new Point(3,0), Compass.N));
		p1Choice.add(new BattleCoordinates(shipList1.get(3), new Point(7,8), Compass.W));
		p1Choice.add(new BattleCoordinates(shipList1.get(4), new Point(4,6), Compass.E));

		// // myGame.getPlayer(player1ID).get

		List<BattleCoordinates> p2Choice = new ArrayList<>();
		p2Choice.add(new BattleCoordinates(shipList1.get(0), new Point(0,0), Compass.N));
		p2Choice.add(new BattleCoordinates(shipList1.get(1), new Point(1,1), Compass.E));
		p2Choice.add(new BattleCoordinates(shipList1.get(2), new Point(2,2), Compass.N));
		p2Choice.add(new BattleCoordinates(shipList1.get(3), new Point(3,3), Compass.S));
		p2Choice.add(new BattleCoordinates(shipList1.get(4), new Point(4,4), Compass.N));

		// List<BattleCoordinates> p2Choice = new ArrayList<>();
		// p2Choice.add(new BattleCoordinates(shipList2.get(0), new Point(0,0), Compass.N));
		// p2Choice.add(new BattleCoordinates(shipList2.get(1), new Point(1,1), Compass.E));
		// p2Choice.add(new BattleCoordinates(shipList2.get(2), new Point(2,2), Compass.N));
		// p2Choice.add(new BattleCoordinates(shipList2.get(3), new Point(3,3), Compass.S));
		// p2Choice.add(new BattleCoordinates(shipList2.get(4), new Point(4,4), Compass.N));

		//Initialize rules
		myGame.rules = new Ruleset();
		myGame.rules.setPlayerRules(player1ID, 1, 1);
		myGame.rules.setPlayerRules(player2ID, 1, 1);

		// myGame.setBoard(player1ID, p1Choice);
		// myGame.setBoard(player2ID, p2Choice);

		//System.out.println(myGame.getShipList(player2ID));

		System.out.println("For: " + player1ID);
		myGame.askForPlacements(player1ID);
		System.out.println("For: " + player2ID);
		myGame.askForPlacements(player2ID);

		// myGame.setBoard(player1ID, p1Choice);


		// System.out.println(myGame.rules.getPlayerTurn());
		// myGame.rules.advanceTurn();
		// System.out.println(myGame.rules.getPlayerTurn());
		// System.out.println(myGame.rules.getPlayerTurn());
		// myGame.rules.advanceTurn();
		// System.out.println(myGame.rules.getPlayerTurn());

		// myGame.conductPlayerTurn();

		while (true){
			// System.out.println("=============================================");
			myGame.conductPlayerTurn();
		}

		//myGame.askForPlacements(player1ID);

		//myGame.setBoard
		// System.out.println("Hello world!");
		// System.out.println(my)
		//System.out.println(myGame.player1.shipList);

		//BattleShipGame.testInheritance();
	}


}//end solution

