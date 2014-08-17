package ui;

import gameManagement.GameManager;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	//TODO: Benno Hier kannst du dich austoben
	
	private static final long serialVersionUID = 5482090458424997569L;


	public static enum PaintState {NONE, WAITFORPLAYER, DRAWMAP}
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
			this.drawMap(g);
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
	 */
	private void drawMap (Graphics g)
	{
		GameManager manager = GameManager.getInstance();
		
		manager.getMap(); //TODO: Hier kannst du die Map zeichnen
		g.drawString("DrawMap", 300, 200);
	}
	
	public void setPaintState (PaintState state)
	{
		this.state = state;
		this.repaint();
	}
}