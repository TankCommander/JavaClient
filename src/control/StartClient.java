package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import sharedObjects.connectionObjects.interfaces.ServerEntryPoint;
import sharedObjects.gameObjects.interfaces.Match;
import ui.MainWindow;

public class StartClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
			    
	    //Create the UI
		MainWindow frame = new MainWindow();
		frame.setVisible(true);
	}

}
