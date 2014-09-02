package control;

import gameManagement.GameManager;
import gameManagement.LocalPointImpl;
import gameManagement.interfaceImplementations.PlayerImpl;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.gameObjects.interfaces.FlightPath;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.Point;
import ui.LocalGameMap;
import ui.DrawPanel.PaintState;

public class ClientInterfaceImplementation implements ClientInterface, Serializable{
	
	private static final long serialVersionUID = 8776665828836217049L;
	private PlayerImpl player;
	
	/**
	 * Constructor
	 * @param player
	 * @throws RemoteException
	 */
	public ClientInterfaceImplementation (PlayerImpl player) throws RemoteException
	{
		UnicastRemoteObject.exportObject(this, 0);
		this.player = player;
	}
	
	
	/**
	 * Function which is called when the game Object is transmitted by the server
	 * @param match
	 * @throws RemoteException
	 */
	@Override
	public void setGameObjects(Match match) throws RemoteException {
		GameManager manager = GameManager.getInstance();
		
		//Clone the map
		ArrayList<Point> horizonSkeleton = new ArrayList<Point>(match.getMap().getHorizonSkeleton().size());
		for (Point point : match.getMap().getHorizonSkeleton()) {
			horizonSkeleton.add(new LocalPointImpl(point.getX(), point.getY()));
		}
		
		ArrayList<Point> horizonLine = new ArrayList<Point>(match.getMap().getHorizonLine().size());
		for (Point point : match.getMap().getHorizonLine()) {
			horizonLine.add(new LocalPointImpl(point.getX(), point.getY()));
		}
		
		LocalGameMap localMap = new LocalGameMap(horizonSkeleton, horizonLine); //Add all needed values here
		
		manager.setMap(localMap);
		manager.setMatch(match);
		manager.environmentIsReady();
	}
	
	/**
	 * Function called when a game is finished
	 * @throws RemoteException
	 */
	public void gameEnded (boolean winner) throws RemoteException
	{
		GameManager manager = GameManager.getInstance();
		manager.gameEnded(winner);
	}
	
	
	@Override
	public void connectionLost(boolean won) throws RemoteException {
		gameEnded(won);
		GameManager.getInstance().getWindow().getDrawPanel().setPaintState(PaintState.GAMEFINISHED);
		GameManager.getInstance().getWindow().setFireButtonState(false);		
	}
	
	
	/**
	 * Function which will inform the client about a new flight path of the fired object
	 * @param flightPath
	 * @throws RemoteException
	 */
	@Override
	public void setNewFlightPath(FlightPath flightPath) throws RemoteException {
		GameManager manager = GameManager.getInstance();
		manager.receivedNewFlightPath(flightPath);
	}
	
	////////////////////
	//Getter + Setter//
	//////////////////
	@Override
	public Player getPlayer() throws RemoteException {
		return this.player;
	}

}
