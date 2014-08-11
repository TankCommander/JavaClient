package ui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Point;

public class LocalGameMap implements GameMap{

	@Override
	public ArrayList<Point> getHorizonLine() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPlayer1Postion() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPlayer2Postion() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
