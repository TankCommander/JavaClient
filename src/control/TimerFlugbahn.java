package control;

import gameManagement.Consts;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import sharedObjects.gameObjects.interfaces.TimePoint;

public class TimerFlugbahn extends TimerTask{
	
	public TimerFlugbahn(ArrayList<TimePoint> _tps, Graphics _g){
		tps = _tps;
		g = _g;
		counter = 0;
	}
	
	@Override public void run(){
		 counter++;
	 }

	 private ArrayList<TimePoint> tps;
	 private Graphics g;
	 private int counter;
}
