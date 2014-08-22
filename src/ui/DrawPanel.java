package ui;

import gameManagement.GameManager;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import sharedObjects.gameObjects.interfaces.GameMap;

public class DrawPanel extends JPanel {
	//TODO: Benno Hier kannst du dich austoben
	
	private static final long serialVersionUID = 5482090458424997569L;


	public static enum PaintState {NONE, WAITFORPLAYER, DRAWMAP, DRAWFLIGHTPATH}
	private PaintState state = PaintState.NONE;
	
	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		System.out.println(state);
		switch (this.state) {
		case WAITFORPLAYER:
			this.paintWaitingMessage(g);
			break;
		case DRAWMAP:
			try {
				this.drawMap(g);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	    
	}
	
	/**
	 * Function to draw the wait for player Message
	 * @param g
	 */
	private void paintWaitingMessage (Graphics g)
	{
		g.setColor(Color.black);
		g.drawString("Wait for other Player", 300, 200);
	}
	
	/**
	 * Function to draw the Map
	 * @param g
	 * @throws RemoteException 
	 */
	private void drawMap (Graphics g) throws RemoteException
	{
		GameManager manager = GameManager.getInstance();
		
		GameMap map = manager.getMap();
		g.drawString("DrawMap:" + map.getHorizonLine().toString(), 300, 200);
		
	}
	
	public void setPaintState (PaintState state)
	{
		this.state = state;
		this.repaint();
	}
}
