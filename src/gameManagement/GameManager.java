package gameManagement;

import gameManagement.interfaceImplementations.PlayerObject;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import control.ClientInterfaceImplementation;

import sharedObjects.connectionObjects.interfaces.ServerEntryPoint;
import ui.MainWindow;

/**
 * Class which manages the whole client game
 * @author markushinkelmann
 *
 */
public class GameManager {
	
	private static GameManager instance;
	private MainWindow window;
	
	private GameManager () {}

	public static synchronized GameManager getInstance () {
		if (GameManager.instance == null) {
			GameManager.instance = new GameManager ();
		}
		return GameManager.instance;
	}
	
	/**
	 * Function called, when it is necessary to start a game
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public void startGame (String playerName, MainWindow window) throws RemoteException, NotBoundException 
	{
		this.window = window;
		
		//Create a player object
		PlayerObject player = new PlayerObject(playerName);
		
		//Register at the Server
		Registry registry = LocateRegistry.getRegistry();
	    ServerEntryPoint server = (ServerEntryPoint) registry.lookup( "ServerEntryPoint" );
	    ClientInterfaceImplementation c = new ClientInterfaceImplementation(player);
	    server.registerClient(c);
	}

}
