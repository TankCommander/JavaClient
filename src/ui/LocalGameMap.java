package ui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Point;

public class LocalGameMap implements GameMap{

	private static final long serialVersionUID = 5106517326997736002L;
	private ArrayList<Point> horizonSkeleton;
	private ArrayList<Point> horizonLine;
	
	public LocalGameMap(ArrayList<Point> horizonSkeleton, ArrayList<Point> horizonLine) throws RemoteException {
		this.horizonSkeleton = horizonSkeleton;
		this.horizonLine = horizonLine;
	}
       		
	
	@Override
	public ArrayList<Point> getHorizonLine() throws RemoteException {
		return this.horizonLine;
	}

	@Override
	public ArrayList<Point> getHorizonSkeleton() throws RemoteException {
		return this.horizonSkeleton;
	}

	@Override
	public double getHorizonY_Value(int x) throws RemoteException {
		return horizonLine.get(x).getY();
	}

}
