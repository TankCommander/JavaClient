package ui;

import gameManagement.Consts;
import gameManagement.GameManager;
import gameManagement.gameObjects.implementations.PointImpl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sharedObjects.gameObjects.interfaces.FlightPath;
import sharedObjects.gameObjects.interfaces.Player;
import sharedObjects.gameObjects.interfaces.Point;
import sharedObjects.gameObjects.interfaces.TimePoint;



public class DrawPanel extends JPanel implements ImageObserver {
	//TODO: Benno Hier kannst du dich austoben
	
	private static final long serialVersionUID = 5482090458424997569L;


	public static enum PaintState {NONE, WAITFORPLAYER, DRAWMAP, DRAWFLIGHTPATH, GAMEFINISHED}
	private PaintState state = PaintState.NONE;
	
	Paint paint;
    String myDir;
    int textureWidth;
    File groundTexture;
    TexturePaint tp;
    
    public boolean lastShoot;
    private int counter;
    private ImageIcon explosionIcon = null;
    private Image explosionImage ;
	private JLabel labelExplosion;
	private Player explosionPlayer = null;
    
    
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
//		System.out.println(state);
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
					
					if (lastShoot)
						this.paintWinLostMessage(g);
					
				} catch (IOException e) {
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
		//Probezeichnen gegen schwarze Ränder
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		//Probezeichnen gegen schwarze Ränder
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		g.drawImage(
				explosionImage, 
				-200 , 
				-200, null);
		g.setColor(Color.black);
		g.drawString("Wait for other Player", 300, 200);
	}
	
	/**
	 * Function to draw the wait for player Message
	 * @param g
	 */
	private void paintWinLostMessage (Graphics g)
	{
		GameManager manager = GameManager.getInstance();
		
		
		if (manager.winner)
		{
			g.setColor(Color.GREEN);
			g.drawString("You Won", 300, 200);
		}
		else
		{
			g.setColor(Color.RED);
			g.drawString("You Lost", 300, 200);
		}
		
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
	    for(Player p : manager.localPlayerPositions){
	    	
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

	    if (explosionPlayer != null)
    		drawExplosion(gra);
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
	
	private void drawExplosion(final Graphics graphics) throws RemoteException{		
		
		if (explosionImage != null && explosionPlayer != null){

			graphics.drawImage(
				explosionImage, 
				explosionPlayer.getPosition().getXasInt() - explosionImage.getWidth(null) / 2 , 
				Consts.WORLD_HEIGHT - explosionPlayer.getPosition().getYasInt() - explosionImage.getHeight(null) / 2, null);
		}
	}
	
	/**
	 * Function to draw the flight path
	 * @param gra
	 * @throws RemoteException 
	 */
	
	private void initStuff() throws Exception{

        setLayout(null);

		myDir = System.getProperty("user.dir");
		groundTexture = new File("./img/berg.jpg");
		BufferedImage bi = ImageIO.read(groundTexture);
		textureWidth = bi.getWidth();
        paint = new TexturePaint(bi, new Rectangle2D.Double(0, 0, textureWidth, textureWidth));
        
        prepareExplosion();
	} 
    
	private void prepareExplosion(){
		
		explosionIcon = new ImageIcon("./img/explosion2.gif");
		explosionImage = explosionIcon.getImage();
		final int CROP_X = 18;
		final int CROP_Y = 20;
		explosionImage = createImage(new FilteredImageSource(explosionImage.getSource(),
		        new CropImageFilter(CROP_X, CROP_Y, explosionImage.getWidth(null) - 2*CROP_X, explosionImage.getHeight(null) - 2*CROP_Y)));
		 	 
	}
	
	
	
	public void setPaintState (PaintState state)
	{
		this.state = state;
		this.repaint();
	}

	public void setCounter(int i) {
		this.counter = 0;		
	}

	public int getCounter() {
		return counter;
	}

	public void IncCounter() {
		counter += Consts.POINTS_PER_REFRESH;		
	}

	public JLabel getLabelExplosion() {
		return labelExplosion;
	}

	public void setLabelExplosion(JLabel labelExplosion) {
		this.labelExplosion = labelExplosion;
	}

	public void showExplosion(Player player) throws RemoteException {
		
		final Timer timer = new Timer();
		
		explosionPlayer = player;
		
		timer.scheduleAtFixedRate(new TimerTask() {
			final int MAX_LOOPS = 10;
			int loopCount = 0;
			
			@Override
			public void run() {
				if (loopCount < MAX_LOOPS ){
					repaint();
					loopCount++;
				} else {
					explosionPlayer = null;
					repaint();
					timer.cancel();
				}
			}
		}, 0, 100);
	}
}