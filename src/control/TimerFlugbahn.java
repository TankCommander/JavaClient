package control;

import java.util.ArrayList;

import sharedObjects.gameObjects.interfaces.TimePoint;
import ui.DrawPanel;
import ui.DrawPanel.PaintState;

public class TimerFlugbahn extends Thread{
	
	private DrawPanel drawPanel;
	private ArrayList<TimePoint> tps;
	
	public TimerFlugbahn(ArrayList<TimePoint> _tps, DrawPanel _drawPanel){
		tps = _tps;
		drawPanel = _drawPanel;
		DrawPanel.counter = 0;
	}
	
	@Override public void run(){
		while(isInterrupted() == false)	{
			if(DrawPanel.counter < tps.size() ){
				drawPanel.setPaintState(PaintState.DRAWFLIGHTPATH);
	//			drawPanel.repaint(); // wird in setPaintState mit erledigt
				DrawPanel.counter++;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				drawPanel.setPaintState(PaintState.DRAWMAP);
				break;
			}
		}
	 }

	 
	 
}