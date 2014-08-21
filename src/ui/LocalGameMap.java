package ui;

import gameManagement.Consts;

import java.awt.Polygon;
import java.rmi.RemoteException;
import java.util.ArrayList;

import sharedObjects.gameObjects.interfaces.GameMap;
import sharedObjects.gameObjects.interfaces.Point;

public class LocalGameMap implements GameMap{

	private static final long serialVersionUID = 5106517326997736002L;
	private ArrayList<Point> horizonSkeleton;
	private ArrayList<Point> horizonLine;
	
	private int[] ax;
	private int[] ay;
	private Polygon polygon;
	
	public LocalGameMap(ArrayList<Point> horizonSkeleton, ArrayList<Point> horizonLine) throws RemoteException {
		this.horizonSkeleton = horizonSkeleton;
		this.horizonLine = horizonLine;
		ax = new int[horizonSkeleton.size()];
		ay = new int[horizonSkeleton.size()];
		int i = 0;
		for(Point p : horizonSkeleton){
			ax[i] = (int)p.getX();
			ay[i] = Consts.MAX_HORIZON_HEIGHT-(int)p.getY();
			i++;
		}		
		polygon = new Polygon(ax,ay,ax.length);
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
	
	public Polygon getPolygon(){
		return polygon;		
	}

	
}
