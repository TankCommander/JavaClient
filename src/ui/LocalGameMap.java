package ui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Point;

public class LocalGameMap implements GameMap{

	private static final long serialVersionUID = 5106517326997736002L;

	@Override
	public ArrayList<Point> getHorizonLine() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHorizonY_Value(int x) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Point> getHorizonSkeleton() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
