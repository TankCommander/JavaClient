package gameManagement.interfaceImplementations;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import sharedObjects.gameObjects.interfaces.Player;

public class PlayerObject implements Player, Serializable {

	private static final long serialVersionUID = -6042346884720363068L;
	private String name;
	private double damage;
	
	public PlayerObject (String playerName) throws RemoteException
	{
		UnicastRemoteObject.exportObject(this, 0);
		
		this.name = playerName;
		this.damage = 0;
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

}
