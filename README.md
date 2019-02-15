# project-battleship

While in /ClientServer folder:
-To compile:
	make all
	
-To clear class files:
	make clean

While in /bin folder:
-To run in lobby mode:
	java Lobby.LobbySystem
	
-Then to connect with clients:
	java Lobby.GameClient
	(or)
	java Lobby.GameClientLobby

-To run in server mode:
	java Lobby.GameServer
	(or)
	java Lobby.GameServer 3
	(to run in three player mode)
-Then to connect with clients:
	java Lobby.GameClient
	
	
