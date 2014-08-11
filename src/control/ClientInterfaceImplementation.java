package control;

import gameManagement.GameManager;
import gameManagement.interfaceImplementations.PlayerObject;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.gameObjects.interfaces.FiredObject;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import ui.LocalGameMap;

public class ClientInterfaceImplementation implements ClientInterface, Serializable{
	
	private static final long serialVersionUID = 8776665828836217049L;
	private PlayerObject player;
	
	/**
	 * Constructor
	 * @param player
	 * @throws RemoteException
	 */
	public ClientInterfaceImplementation (PlayerObject player) throws RemoteException
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
	public void gameObject(Match match) throws RemoteException {
		GameManager manager = GameManager.getInstance();
		LocalGameMap localMap = new LocalGameMap(); //Add all needed values here
		manager.setMap(localMap);
		manager.setMatch(match);
		manager.environmentIsReady();
	}

	@Override
	public void connectionLost() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void opponentFired(FiredObject data) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////
	//Getter + Setter//
	//////////////////
	@Override
	public Player getPlayer() throws RemoteException {
		return this.player;
	}

}
