package gameManagement;

import gameManagement.interfaceImplementations.PlayerImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import control.ClientInterfaceImplementation;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.connectionObjects.interfaces.ServerEntryPoint;
import sharedObjects.gameObjects.interfaces.FlightPath;
import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import ui.LocalGameMap;
import ui.MainWindow;
import ui.DrawPanel.PaintState;

/**
 * Class which manages the whole client game
 * @author markushinkelmann
 *
 */
public class GameManager {
	
	private static GameManager instance;
	private MainWindow window;
	private LocalGameMap map;
	private Match match;
	private ClientInterface cInterface;
	
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
	 * @return True if a player is available otherwise false
	 */
	public boolean startGame (String playerName, MainWindow window) throws RemoteException, NotBoundException 
	{
		this.window = window;
		
		//Create a player object
		PlayerImpl player = new PlayerImpl(playerName);
		
		//Register at the Server
		Registry registry = LocateRegistry.getRegistry();
	    ServerEntryPoint server = (ServerEntryPoint) registry.lookup( "ServerEntryPoint" );
	    ClientInterfaceImplementation c = new ClientInterfaceImplementation(player);
	    player.setClientInterface(c);
	    this.cInterface = c;
	    return server.registerClient(c);
	}
	
	/**
	 * Function called when the game environment is ready to play
	 * Updates the UI, send the needed data to the DrawPanel
	 * @throws RemoteException 
	 */
	public void environmentIsReady () throws RemoteException
	{
		window.setPlayerNames(this.match.getPlayers().get(0).getName(), this.match.getPlayers().get(1).getName());
		window.getDrawPanel().setPaintState(PaintState.DRAWMAP);
		
		//Enable oder disable the fire button
		if (this.match.getActivePlayer().comparePlayers(this.cInterface.getPlayer()))
			window.setFireButtonState(true);
		else
			window.setFireButtonState(false);
		
		window.getDrawPanel().repaint();
	}
	
	
	/**
	 * Function called, when the client received a new flight path
	 */
	public void receivedNewFlightPath (FlightPath path)
	{
		//TODO: Implement action
	}

	
	////////////////////
	//Getter + Setter//
	//////////////////
	public GameMap getMap() {
		return map;
	}

	public void setMap(LocalGameMap map) {
		this.map = map;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public ClientInterface getcInterface() {
		return cInterface;
	}

	public void setcInterface(ClientInterface cInterface) {
		this.cInterface = cInterface;
	}
	
	
	
}
