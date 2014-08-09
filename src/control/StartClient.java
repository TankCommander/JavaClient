package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import sharedObjects.connectionObjects.interfaces.ServerEntryPoint;
import sharedObjects.gameObjects.interfaces.Match;

public class StartClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
	    ServerEntryPoint server = (ServerEntryPoint) registry.lookup( "ServerEntryPoint" );
	    ClientInterfaceImplementation c = new ClientInterfaceImplementation();
	    server.registerClient(c);

	}

}
