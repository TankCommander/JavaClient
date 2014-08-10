package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	//TODO: Benno Hier kannst du dich austoben
	
	private static final long serialVersionUID = 5482090458424997569L;


	public static enum PaintState {NONE, WAITFORPLAYER}
	private PaintState state = PaintState.NONE;
	
	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		
		switch (this.state) {
		case WAITFORPLAYER:
			this.paintWaitingMessage(g);
			break;

		default:
			break;
		}
	    
	}
	
	private void paintWaitingMessage (Graphics g)
	{
		g.setColor(Color.black);
		g.drawString("Wait for other Player", 300, 200);
	}
	
	
	public void setPaintState (PaintState state)
	{
		this.state = state;
	}
}
