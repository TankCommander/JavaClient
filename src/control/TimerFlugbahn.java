package control;

import gameManagement.Consts;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.TimerTask;

import sharedObjects.gameObjects.interfaces.TimePoint;
import ui.DrawPanel;

public class TimerFlugbahn extends TimerTask{
	
	public TimerFlugbahn(ArrayList<TimePoint> _tps, Graphics _g){
		tps = _tps;
		g = _g;
		counter = 0;
	}
	
	@Override public void run(){
		 try {
			g.drawOval(tps.get(counter).getXasInt(), Consts.WORLD_HEIGHT - tps.get(counter).getYasInt(), 5, 5);
			System.out.println(tps.get(counter).getXasInt() + " " + tps.get(counter).getYasInt() + " " + tps.get(counter).getT());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		counter++;
	 }

	 private ArrayList<TimePoint> tps;
	 private Graphics g;
	 private int counter;
}
