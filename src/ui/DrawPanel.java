package ui;

import gameManagement.Consts;
import gameManagement.GameManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	//TODO: Benno Hier kannst du dich austoben
	
	private static final long serialVersionUID = 5482090458424997569L;


	public static enum PaintState {NONE, WAITFORPLAYER, DRAWMAP, DRAWFLIGHTPATH}
	private PaintState state = PaintState.NONE;
	
	Paint paint;
    String myDir;
    int textureWidth;
    File groundTexture;
    TexturePaint tp;
	
	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		System.out.println(state);
		switch (this.state) {
			case WAITFORPLAYER:
				
				try {
					initStuff();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.paintWaitingMessage(g);
				
				break;
			case DRAWMAP:
				try {
					this.drawMap(g);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DRAWFLIGHTPATH:
				try {
					this.drawMap(g);
					this.drawFlightPath(g);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
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
	 * @throws IOException 
	 */
	private void drawMap (Graphics gra) throws IOException
	{
		Graphics2D g = (Graphics2D) gra;
		GameManager manager = GameManager.getInstance();
		LocalGameMap map = (LocalGameMap)manager.getMap();
		g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.5f));
		g.fillRect(0, 0, Consts.WORLD_WIDTH, Consts.MAX_HORIZON_HEIGHT);
	    g.setPaint(paint);
	    g.fillPolygon(map.getPolygon());
		
	}
	
	/**
	 * Function to draw the flight path
	 * @param gra
	 */
	private void drawFlightPath (Graphics gra)
	{
		//TODO: Benno tob dich hier aus ;)
		GameManager manager = GameManager.getInstance();
		gra.drawString(manager.getCurrentFlightPath().toString(), 300, 200);
	}
	
	private void initStuff() throws Exception{

		myDir = System.getProperty("user.dir");
		groundTexture = new File("./img/berg.jpg");
		BufferedImage bi = ImageIO.read(groundTexture);
		textureWidth = bi.getWidth();
        paint = new TexturePaint(bi, new Rectangle2D.Double(0, 0, textureWidth, textureWidth));
	} 
    
	public void setPaintState (PaintState state)
	{
		this.state = state;
		this.repaint();
	}
	

}
