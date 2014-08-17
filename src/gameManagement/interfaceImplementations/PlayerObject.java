package gameManagement.interfaceImplementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.Point;

public class PlayerObject implements Player {
	// ist das die shared PlayerImpl ?

	private static final long serialVersionUID = -6042346884720363068L;
	private String name;
	private double damage = 0;
	private double angle = 0;
	private Point position;
	private Match match;
	
	public PlayerObject (String playerName) throws RemoteException
	{
		UnicastRemoteObject.exportObject(this, 0);
		
		this.name = playerName;
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
	public void setMatch(Match match) {
		this.match = match;		
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

}
