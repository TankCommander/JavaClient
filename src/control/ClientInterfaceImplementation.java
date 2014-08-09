package control;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import sharedObjects.connectionObjects.interfaces.ClientInterface;
import sharedObjects.gameObjects.interfaces.Match;
import sharedObjects.gameObjects.interfaces.Player;

public class ClientInterfaceImplementation implements ClientInterface, Serializable{
	
	public ClientInterfaceImplementation () throws RemoteException
	{
		UnicastRemoteObject.exportObject(this, 0);
	}
	
	@Override
	public void playerAvailable(Player opponent) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameObject(Match match) throws RemoteException {
		float a = 30;
		float b = 40;
		match.Fire(a, b);
		
	}

	@Override
	public void connectionLost() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getPlayer() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
