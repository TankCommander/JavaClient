package ui;

import gameManagement.Consts;
import gameManagement.GameManager;
import gameManagement.gameObjects.implementations.PointImpl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sharedObjects.gameObjects.interfaces.FlightPath;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.Point;
import sharedObjects.gameObjects.interfaces.TimePoint;

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
    public static int counter;
    
    
    public DrawPanel(){
    	super();
    	try {
			initStuff();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
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
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DRAWFLIGHTPATH:
				try {
					this.drawMap(g);

					GameManager manager = GameManager.getInstance();
					FlightPath fp = manager.getCurrentFlightPath();
					ArrayList<TimePoint> tps = fp.getTimePoints();
					
					if (counter < tps.size()){ //counter ist manchmal falsch!
						g.setColor(Color.DARK_GRAY);
						g.fillOval(tps.get(counter).getXasInt() - Consts.BULLET_RADIUS, Consts.WORLD_HEIGHT - tps.get(counter).getYasInt() - Consts.BULLET_RADIUS, 2*Consts.BULLET_RADIUS, 2*Consts.BULLET_RADIUS);
					}
					
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
		g.fillRect(0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
	    g.setPaint(paint);
	    g.fillPolygon(map.getPolygon());
	    
	    //Player
	    int d = 2* Consts.PLAYER_RADIUS;
	    g.setColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));
	    for(Player p : manager.getMatch().getPlayers()){
	    	
	    	int x = p.getPosition().getXasInt() - Consts.PLAYER_RADIUS;
	    	int y = Consts.WORLD_HEIGHT - p.getPosition().getYasInt();// - Consts.PLAYER_RADIUS;
	    	
	    	g.setColor(p.getColor());
//	    	g.fillRect(x, y, d, d);
	    	
	    	int r = 2 * Consts.PLAYER_RADIUS / 3;
	    	int x_o = p.getPosition().getXasInt() - r;
	    	int y_o = Consts.WORLD_HEIGHT - p.getPosition().getYasInt() - r;
	    	g.fillOval(x_o, y_o, 2*r, 2*r);
	    	g.fillRoundRect(x, y, d, Consts.PLAYER_RADIUS, 3, 3);
	    		    	
	    	// Kanonenrohr
	    	drawBarrel(gra, p);	    	
	    }		
	}
	
	private void drawBarrel(Graphics graphics, Player player) throws IOException{
		Point m = player.getPosition();
		double angle = player.getAngele();
		
		Point p = new PointImpl(m.getX() + Math.cos(angle) * Consts.PLAYER_RADIUS, m.getY() + Math.sin(angle) * Consts.PLAYER_RADIUS);
		
	
		graphics.setColor(player.getColor());
		graphics.drawLine(m.getXasInt(), Consts.WORLD_HEIGHT - m.getYasInt(), p.getXasInt(), Consts.WORLD_HEIGHT  - p.getYasInt());
	}
	
	@SuppressWarnings("unused")
	private void drawHorizonLine(Graphics gra, LocalGameMap map) throws RemoteException{
		Point pb = null;
		for(Point p : map.getHorizonLine()){
			if (pb != null){
				gra.drawLine(
					pb.getXasInt(),
					Consts.WORLD_HEIGHT - pb.getYasInt(),
					p.getXasInt(),
					Consts.WORLD_HEIGHT - p.getYasInt()
					);
			};
			pb = p;
		}
		
	}
	
	/**
	 * Function to draw the flight path
	 * @param gra
	 * @throws RemoteException 
	 */
	
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