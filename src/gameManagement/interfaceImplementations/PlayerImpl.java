package gameManagement.interfaceImplementations;

import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.Point;

public class PlayerImpl extends UnicastRemoteObject implements Player  {

	private static final long serialVersionUID = -6042346884720363068L;
	private String name;
	private double damage = 0;
	private double angle = 0;
	private Point position;
	private Match match;
	private ClientInterface cInterface;
	private String playerID;
	private Color color;
	
	public PlayerImpl (String playerName) throws RemoteException
	{
//		UnicastRemoteObject.exportObject(this, 0); macht UnicastRemoteObject
		
		this.name = playerName;
		this.playerID = generatePlayerID();
	}
	

	/**
	 * Function which will create a Player ID
	 * @return String
	 */
	private String generatePlayerID ()
	{
		String id = String.valueOf(System.currentTimeMillis());
		id += String.valueOf(Math.random());
		
		return id;
	}
	
	
	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	@Override
	public double getDamage() throws RemoteException {
		return this.damage;
	}

	@Override
	public void addDamage(double damage) throws RemoteException {
		this.damage = this.damage + damage;
		
		if (this.damage > 1.0)
			this.damage = 1.0;
	}

	@Override
	public void setMatch(Match match) throws RemoteException {
		this.match = match;		
		if (match != null)
			this.position = this.match.getPlayerPosition(this);
		else
			this.position = null;				
	}

	@Override
	public Point getPosition() {		
		return this.position;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;		
	}

	@Override
	public Match getMatch() throws RemoteException {
		return this.match;
	}

	@Override
	public void setPosition(Point position) throws RemoteException {
		this.position = position;
	}

	@Override
	public double getAngele() throws RemoteException {
		return this.angle;
	}

	@Override
	public ClientInterface getClientInterface() throws RemoteException {
		return this.cInterface;
	}

	@Override
	public void setClientInterface(ClientInterface cInterface) throws RemoteException{
		this.cInterface = cInterface;
	}

	@Override
	public String getID() throws RemoteException {
		return this.playerID;
	}


	@Override
	public boolean equalsPlayer(Player otherPlayer) throws RemoteException {
		return (otherPlayer.getID().equals(this.getID()));
	}


	@Override
	public void setColor(Color color) throws RemoteException {
		this.color = color;		
	}


	@Override
	public Color getColor() throws RemoteException {
		return this.color;
	}

}
